package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.model.source.NodeType;
import com.cross_ni.cross.cdc.serialization.NodeCdcSinkValueJoinerFacade;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final String KTABLE_NODES = "cross.cdc.ktable.node";
    private static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";
    private static final String TOPIC_NAME_NODE_TYPE = "crossdb.public.node_type";
    private static final String TOPIC_NAME_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private final StreamsBuilder builder = new StreamsBuilder();
    private final NodeCdcSinkValueJoinerFacade mapper = new NodeCdcSinkValueJoinerFacade();

    public Topology build() {
        final KStream<String, com.cross_ni.cross.cdc.model.sink.NodeType> nodeTypes = nodeTypes();
        nodeTypes.to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.NodeType.class)));

        return builder.build();
    }

    private KStream<String, com.cross_ni.cross.cdc.model.sink.NodeType> nodeTypes() {
        final GlobalKTable<String, NodeType> nodeTypes = builder.globalTable(TOPIC_NAME_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeType.class))).;
        final KStream<String, NodeNodeType> nodeNodeTypes = builder.stream(TOPIC_NAME_NODE_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)));

        return
            nodeNodeTypes
                .join(nodeTypes, (key, value) -> value.getDiscriminator(), NodeCdcSinkValueJoinerFacade.apply(NodeNodeType.class, NodeType.class));
    }

}
