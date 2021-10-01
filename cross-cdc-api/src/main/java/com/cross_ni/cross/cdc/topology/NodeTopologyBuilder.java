package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.aggregate.NodeSnapshot;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
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
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_CA_VAL = "crossdb.public.ca_val";
    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";
    private static final String TOPIC_NAME_SOURCE_EXTERNAL_ID = "crossdb.public.external_id";

    private final StreamsBuilder builder = new StreamsBuilder();

    public Topology build() {
        repartitionByCaSetId(nodes())
            .toStream()
//            .join(customAttributes(), NodeSnapshot::valueJoiner)
            .foreach((k, v) -> System.out.println(k + " " + v));
//            .join(nodeNodeTypes(), NodeSnapshot::valueJoiner)
//            .join(externalIds(), NodeSnapshot::valueJoiner)
//            .toStream()
//            .to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(NodeSnapshot.class)));

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
            .groupBy((k, v) -> k, Grouped.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
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

    private KTable<String, CustomAttributes> customAttributes() {
        return
            builder
                .stream(TOPIC_NAME_SOURCE_CA_VAL, Consumed.with(Serdes.String(), JsonSerdes.serde(CustomAttribute.class)))
                .groupByKey()
                .aggregate(CustomAttributes::new, CustomAttributes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private KTable<String, NodeSnapshot> repartitionByCaSetId(KTable<String, NodeSnapshot> nodes) {
        return
            nodes
            .groupBy((nodeId, node) -> KeyValue.pair(node.getCaSetId(), node), Grouped.with(Serdes.String(), JsonSerdes.serde(NodeSnapshot.class)))
            .aggregate(() -> null, (key, n, aggregate) -> n, (key, n, aggregate) -> null, Materialized.with(Serdes.String(), JsonSerdes.serde(NodeSnapshot.class)));
    }
//    private static <KO, KN, V> KTable<KN, V> repartition(KTable<KO, V> tableToRepartition) {
//        final KTable<String, User> usersKeyedByApplicationIDKTable = usersKTable.groupBy(
//            // First, going to set the new key to the user's application id
//            (userId, user) -> KeyValue.pair(user.getApplicationID().toString(), user)
//        ).aggregate(
//            // Initiate the aggregate value
//            () -> null,
//            // adder (doing nothing, just passing the user through as the value)
//            (applicationId, user, aggValue) -> user,
//            // subtractor (doing nothing, just passing the user through as the value)
//            (applicationId, user, aggValue) -> user
//        );
//    }
}
