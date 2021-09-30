package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.NodeCdcSinkValueJoinerFacade;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private final StreamsBuilder builder = new StreamsBuilder();

    public Topology build() {
        final KStream<String, NodeTypes> nodeNodeTypesSnapshot = nodeNodeTypes().toStream();
        final KStream<String, com.cross_ni.cross.cdc.model.sink.Node> nodes = nodes();

        nodes.to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class)));

        nodeNodeTypesSnapshot.to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)));

        return builder.build();
    }

    private KStream<String, com.cross_ni.cross.cdc.model.sink.Node> nodes() {
        return builder
            .stream(TOPIC_NAME_SOURCE_NODE, Consumed.with(Serdes.String(), JsonSerdes.serde(Node.class)))
            .mapValues(NodeCdcSinkValueJoinerFacade::nodeMapper);
    }

    private KTable<String, NodeTypes> nodeNodeTypes() {
        return
            builder
            .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
            .groupBy((k, v) -> k, Grouped.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
            .aggregate(NodeTypes::new, NodeTypes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)));
    }
}
