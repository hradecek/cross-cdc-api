package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.source.CaDefinition;
import com.cross_ni.cross.cdc.model.source.CdcEntityWithCaSetModel;
import com.cross_ni.cross.cdc.model.source.CustomAttribute;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;

class CustomAttributesTopologyBuilder<T extends CdcEntityWithCaSetModel<String, String>> {

    private static final String TOPIC_NAME_SOURCE_CA_DEF = "crossdb.public.ca_def";
    private static final String TOPIC_NAME_SOURCE_CA_VAL = "crossdb.public.ca_val";

    private final StreamsBuilder builder;

    public CustomAttributesTopologyBuilder(StreamsBuilder builder) {
        this.builder = builder;
    }

    public KTable<String, CustomAttributes> customAttributes(KTable<String, T> entityTable) {
        final KStream<String, CustomAttribute> caValues = caValues();

        return repartitionByNodeId(aggregate(joinCaDefinitions(caValues)), entityTable);
    }

    private KStream<String, CustomAttribute> caValues() {
        return builder.stream(TOPIC_NAME_SOURCE_CA_VAL, Consumed.with(Serdes.String(), JsonSerdes.serde(CustomAttribute.class)));
    }

    private KStream<String, CustomAttribute> joinCaDefinitions(KStream<String, CustomAttribute> caValues) {
        final GlobalKTable<String, CaDefinition> caDefinitions = caDefinitions();

        return caValues.join(caDefinitions, (caSetId, customAttribute) -> customAttribute.getCaDefId(), CustomAttribute::join);
    }

    private KTable<String, CustomAttributes> aggregate(KStream<String, CustomAttribute> caValues) {
        return caValues
            .groupByKey()
            .aggregate(CustomAttributes::new, CustomAttributes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private KTable<String, CustomAttributes> repartitionByNodeId(KTable<String, CustomAttributes> caValues, KTable<String, T> entityTable) {
        final KTable<String, CaSetIdEntityId> caSetIdEntityIdMap = caSetIdEntityIdMap(entityTable);

        return caValues.join(caSetIdEntityIdMap, (customAttributes, node) -> customAttributes.nodeId(node.getNodeId()), Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private GlobalKTable<String, CaDefinition> caDefinitions() {
        return builder.globalTable(TOPIC_NAME_SOURCE_CA_DEF, Consumed.with(Serdes.String(), JsonSerdes.serde(CaDefinition.class)));
    }

    private KTable<String, CaSetIdEntityId> caSetIdEntityIdMap(KTable<String, T> entityTable) {
        return entityTable
            .toStream()
            .filterNot((id, entity) -> entity.getOp().equals("u"))
            .map((entityId, entity) -> KeyValue.pair(entity.getCaSetId(), new CaSetIdEntityId(entity.getCaSetId(), entityId)))
            .toTable(Materialized.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)));
    }
}
