package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.NodeCdcSinkValueJoinerFacade;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;

import java.time.Duration;
import java.util.Map;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private final StreamsBuilder builder = new StreamsBuilder();

    public Topology build() {
        final Map<String, KStream<String, Node>> nodesSplit = nodesSplit();
        final KTable<String, com.cross_ni.cross.cdc.model.sink.Node> snapshots = snapshot(nodesSplit.get("NODE-SNAPSHOTS"));
        final KStream<String, com.cross_ni.cross.cdc.model.sink.Node> updates = nodeUpdatesStream(nodesSplit.get("NODE-UPDATES"));
        snapshots.toStream()
            .outerJoin(updates, (snap, update) -> {
                    if (update == null) {
                        return snap;
                    }
                    return update;
                }, JoinWindows.of(Duration.ZERO),
                StreamJoined.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class)))
        .to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class)));
        //                ,(update, snap) -> {
//                    if (snap != null) {
//                        System.out.println("UPDATE");
//                        update.setOperation("u");
//                        return update;
//                    }
//                    System.out.println("READ");
//                    return snap;
//                },
//                Joined.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class)))
//            .to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class)));
//        nodeNodeTypesUpdates().to(TOPIC_NAME_SINK_NODE);

        return builder.build();
    }

    private KStream<String, com.cross_ni.cross.cdc.model.sink.Node> nodeUpdatesStream(KStream<String, Node> nodesSplit) {
        return nodesSplit.mapValues(NodeCdcSinkValueJoinerFacade::nodeMapper);
    }

    private Map<String, KStream<String, Node>> nodesSplit() {
        return builder
            .stream(TOPIC_NAME_SOURCE_NODE, Consumed.with(Serdes.String(), JsonSerdes.serde(Node.class)))
            .split(Named.as("NODE-"))
            .branch((nodeId, node) -> node.getOp().equals("r"), Branched.as("SNAPSHOTS"))
            .defaultBranch(Branched.as("UPDATES"));
    }

    private KTable<String, com.cross_ni.cross.cdc.model.sink.Node> snapshot(KStream<String, Node> nodesSplit) {
        return joinNodeTypesSnapshot(nodesSplit.toTable().mapValues(NodeCdcSinkValueJoinerFacade::nodeMapper));
    }

    private KTable<String, com.cross_ni.cross.cdc.model.sink.Node> joinNodeTypesSnapshot(KTable<String, com.cross_ni.cross.cdc.model.sink.Node> nodesTable) {
        final KTable<String, NodeTypes> nodeNodeTypesTable = nodeNodeTypesSnapshot();
        return nodesTable.join(nodeNodeTypesTable, NodeCdcSinkValueJoinerFacade::nodeTypesJoiner, Materialized.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class)));
    }

    private KTable<String, NodeTypes> nodeNodeTypesSnapshot() {
        return builder
            .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
            .filter((k, v) -> v.getOp().equals("r"))
            .groupBy((k, v) -> k, Grouped.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
            .aggregate(NodeTypes::new, NodeTypes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)));
    }

    private KStream<String, NodeTypes> nodeNodeTypesUpdates() {
        return builder
            .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
            .groupBy((k, v) -> k, Grouped.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
            .aggregate(NodeTypes::new, NodeTypes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)))
            .filterNot((k, v) -> v.getOperation().equals("r"))
            .toStream();
    }
}
