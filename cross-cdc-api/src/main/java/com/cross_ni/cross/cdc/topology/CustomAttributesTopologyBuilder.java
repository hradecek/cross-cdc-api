package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.source.CaDefinition;
import com.cross_ni.cross.cdc.model.source.CustomAttribute;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Repartitioned;

import static com.cross_ni.cross.cdc.topology.LinkTopologyBuilder.TOPIC_NAME_SINK_LINK;
import static com.cross_ni.cross.cdc.topology.NodeTopologyBuilder.TOPIC_NAME_SINK_NODE;

class CustomAttributesTopologyBuilder {

    private static final String TOPIC_NAME_SOURCE_CA_DEF = "crossdb.public.ca_def";
    private static final String TOPIC_NAME_SOURCE_CA_VAL = "crossdb.public.ca_val";

    private final StreamsBuilder builder;

    private KTable<String, CaSetIdEntityId> nodeMap;
    private KTable<String, CaSetIdEntityId> linkMap;

    public CustomAttributesTopologyBuilder(StreamsBuilder builder, KTable<String, CaSetIdEntityId> nodeMap, KTable<String, CaSetIdEntityId> linkMap) {
        this.builder = builder;
        this.nodeMap = nodeMap;
        this.linkMap = linkMap;
    }

    public void build() {
        final KStream<String, CustomAttribute> caValues = caValues();
        KTable<String, CustomAttributes> customAttributes = aggregate(joinCaDefinitions(caValues));
        repartitionByEntityId(customAttributes, nodeMap).to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private KStream<String, CustomAttribute> caValues() {
        return builder.stream(TOPIC_NAME_SOURCE_CA_VAL, JsonConsumed.of("cu", CustomAttribute.class));
    }

    private GlobalKTable<String, CaDefinition> caDefinitions() {
        return builder.globalTable(TOPIC_NAME_SOURCE_CA_DEF, JsonConsumed.of("cuu", CaDefinition.class));
    }

    private KStream<String, CustomAttribute> joinCaDefinitions(KStream<String, CustomAttribute> caValues) {
        return caValues.join(caDefinitions(), (caSetId, customAttribute) -> customAttribute.getCaDefId(), CustomAttribute::join);
    }

    private KTable<String, CustomAttributes> aggregate(KStream<String, CustomAttribute> caValues) {
        return caValues
            .groupByKey()
            .aggregate(CustomAttributes::new, CustomAttributes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private KStream<String, CustomAttributes> repartitionByEntityId(KTable<String, CustomAttributes> caValues, KTable<String, CaSetIdEntityId> caSetIdEntityIdMap) {
        return caValues
            .join(caSetIdEntityIdMap, (customAttributes, node) -> customAttributes.entityId(node.getEntityId()), Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)))
            .toStream()
            .selectKey((caSetId, custom) -> custom.getEntityId())
            .repartition(Repartitioned.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }
}
