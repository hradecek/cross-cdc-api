package com.cross_ni.cross.cdc.topology;

//import com.cross_ni.cross.cdc.model.source.NodeNodeType;
//import com.cross_ni.cross.cdc.model.source.NodeType;
//import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final String KTABLE_NODES = "cross.cdc.ktable.node";
    private static final String TOPIC_NAME_NODE_TYPE = "crossdb.public.node";
    private static final String TOPIC_NAME_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private final StreamsBuilder builder = new StreamsBuilder();

    public Topology build() {
//        final GlobalKTable<String, NodeType> nodeTypes = nodeTypes();
//        final KTable<String, NodeTypes> nodeNodeTypes = nodeNodeTypes(nodeTypes);
//        builder
//            .stream(TOPIC_NAME_NODE_NODE_TYPE, Consumed.with(JsonSerdes.serde(NodeNodeType.class), JsonSerdes.serde(NodeNodeType.class)))
//            .selectKey((key, value) -> String.valueOf(key.getNodeId()))
//            .foreach((k, v) -> System.out.println(k + " " + v));
        return builder.build();
    }

//    private GlobalKTable<String, NodeType> nodeTypes() {
//        return builder.globalTable(TOPIC_NAME_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeType.class)));
//    }

    // TODO: Do selectKey directly in SMT in order to avoid repartitioning
//    private KTable<String, NodeTypes> nodeNodeTypes(GlobalKTable<String, NodeType> nodeTypes) {
//        return builder
//            .stream(TOPIC_NAME_NODE_NODE_TYPE, Consumed.with(JsonSerdes.serde(NodeNodeType.class), JsonSerdes.serde(NodeNodeType.class)))
//            .selectKey((key, value) -> String.valueOf(key.getNodeId()));
//            .join(nodeTypes, (key, value) -> value.getDiscriminator(), (left, right) -> {
//                right.setOperation(left.getOperation());
//                return right;
//            })
//            .groupByKey(Grouped.with(Serdes.String(), JsonSerdes.serde(NodeType.class)))
//            .aggregate(
//                NodeTypes::new,
//                NodeTopologyBuilder::nodeTypesAggregator,
//                Materialized.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)));
//    }
}
