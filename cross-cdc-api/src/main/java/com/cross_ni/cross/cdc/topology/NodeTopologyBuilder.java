package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.NodeCdcSinkValueJoinerFacade;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";
    private static final String TOPIC_NAME_SOURCE_EXTERNAL_ID = "crossdb.public.external_id";

    private final StreamsBuilder builder = new StreamsBuilder();

    public Topology build() {
        final KTable<String, NodeTypes> nodeTypes = nodeNodeTypes();
        final KTable<String, ExternalIds> externalIds = externalIds();
        final KTable<String, Node> nodes = nodes();

        nodes
            .join(nodeTypes, (node, nts) -> {
                final com.cross_ni.cross.cdc.model.sink.Node sinkNode;
                if (node.getOp().equals("r") && nts.getOperation().equals("r")) {
                    sinkNode = NodeCdcSinkValueJoinerFacade.nodeMapper(node);
                    sinkNode.setOperation("r");
                    sinkNode.setNodeTypes(new ArrayList<>(nts.getDiscriminators()));
                } else {
                    if (node.getSourceTsMs() > nts.getSourceTsMs()) {
                        sinkNode = NodeCdcSinkValueJoinerFacade.nodeMapper(node);
                        sinkNode.setOperation(node.getOp());
                    } else {
                        sinkNode = new com.cross_ni.cross.cdc.model.sink.Node();
                        sinkNode.setNodeId(node.getNodeId());
                        sinkNode.setNodeTypes(new ArrayList<>(nts.getDiscriminators()));
                        sinkNode.setOperation(nts.getOperation());
                    }
                }
                return sinkNode;
            })
            .join(externalIds, (node, eids) -> {
                if (node.getOperation().equals("r") && eids.getOperation().equals("r")) {
                    List<com.cross_ni.cross.cdc.model.sink.ExternalId> esss = eids.getExternalIds().stream().map(e -> {
                        final com.cross_ni.cross.cdc.model.sink.ExternalId sinkEid = new com.cross_ni.cross.cdc.model.sink.ExternalId();
                        sinkEid.setOperation(e.getOp());
                        sinkEid.setExternalId(e.getExternalId());
                        sinkEid.setSystemId(e.getSystemId());
                        return sinkEid;
                    }).collect(Collectors.toList());
                    node.setExternalIds(esss);
                } else {
                    if (node.getSourceTsMs() > eids.getSourceTsMs()) {
                        sinkNode = NodeCdcSinkValueJoinerFacade.nodeMapper(node);
                        sinkNode.setOperation(node.getOp());
                    } else {
                        sinkNode = new com.cross_ni.cross.cdc.model.sink.Node();
                        sinkNode.setNodeId(node.getNodeId());
                        sinkNode.setNodeTypes(new ArrayList<>(nts.getDiscriminators()));
                        sinkNode.setOperation(nts.getOperation());
                    }
                }
                return node;
            })
            .mapValues(n -> {
                n.setSourceTsMs(null);
                return n;
            })
            .toStream()
            .to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class)));

        return builder.build();
    }

    private KTable<String, Node> nodes() {
        return builder.table(TOPIC_NAME_SOURCE_NODE, Consumed.with(Serdes.String(), JsonSerdes.serde(Node.class)));
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
}
