package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.aggregate.NodeSnapshot;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.CaDefinition;
import com.cross_ni.cross.cdc.model.source.CustomAttribute;
import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_CA_DEF = "crossdb.public.ca_def";
    private static final String TOPIC_NAME_SOURCE_CA_VAL = "crossdb.public.ca_val";
    private static final String TOPIC_NAME_SOURCE_EXTERNAL_ID = "crossdb.public.external_id";
    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private final StreamsBuilder builder = new StreamsBuilder();

    public Topology build() {
        final GlobalKTable<String, CaDefinition> caDefinitions = caDefinitions();
        repartitionByCaSetId(nodes().join(nodeNodeTypes(), NodeSnapshot::join).join(externalIds(), NodeSnapshot::join))
            .leftJoin(customAttributes(caDefinitions), NodeSnapshot::join)
            .toStream()
            .to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(NodeSnapshot.class)));

        return builder.build();
    }

    private KTable<String, NodeSnapshot> nodes() {
        return builder
            .table(TOPIC_NAME_SOURCE_NODE, Consumed.with(Serdes.String(), JsonSerdes.serde(Node.class)))
            .mapValues(NodeSnapshot::new);
    }

    private KTable<String, NodeTypes> nodeNodeTypes() {
        return
            builder
            .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
            .groupByKey()
            .aggregate(NodeTypes::new, NodeTypes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)));
    }

    private static final String EXTERNAL_ID_NODE_ENTITY = "com.cross_ni.cross.db.pojo.core.Node";

    private KTable<String, ExternalIds> externalIds() {
        return builder
            .stream(TOPIC_NAME_SOURCE_EXTERNAL_ID, Consumed.with(Serdes.String(), JsonSerdes.serde(ExternalId.class)))
            .filter((k, v) -> v.getEntity().equals(EXTERNAL_ID_NODE_ENTITY))
            .groupByKey()
            .aggregate(ExternalIds::new, ExternalIds::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
    }

    private KTable<String, CustomAttributes> customAttributes(GlobalKTable<String, CaDefinition> caDefinitions) {
        return
            builder
                .stream(TOPIC_NAME_SOURCE_CA_VAL, Consumed.with(Serdes.String(), JsonSerdes.serde(CustomAttribute.class)))
                .join(caDefinitions, (caSetId, customAttribute) -> customAttribute.getCaDefId(), CustomAttribute::join)
                .groupByKey()
                .aggregate(CustomAttributes::new, CustomAttributes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private KTable<String, NodeSnapshot> repartitionByCaSetId(KTable<String, NodeSnapshot> nodes) {
        return
            nodes
            .groupBy((nodeId, node) -> KeyValue.pair(node.getCaSetId(), node), Grouped.with(Serdes.String(), JsonSerdes.serde(NodeSnapshot.class)))
            .aggregate(() -> null, (key, n, aggregate) -> n, (key, n, aggregate) -> null, Materialized.with(Serdes.String(), JsonSerdes.serde(NodeSnapshot.class)));
    }

    private GlobalKTable<String, CaDefinition> caDefinitions() {
        return builder.globalTable(TOPIC_NAME_SOURCE_CA_DEF, Consumed.with(Serdes.String(), JsonSerdes.serde(CaDefinition.class)));
    }
}
