package com.cross_ni.cross.cdc.serialization;

import com.cross_ni.cross.cdc.model.aggregate.NodeSnapshot;
import com.cross_ni.cross.cdc.model.sink.Node;

import org.apache.kafka.streams.kstream.ValueMapper;

public class NodeSinkValueMapper implements ValueMapper<com.cross_ni.cross.cdc.model.aggregate.NodeSnapshot, Node> {

    @Override
    public Node apply(NodeSnapshot node) {
        final Node sinkNode = new Node();
        sinkNode.setNodeId(node.getNodeId());
        sinkNode.setName(node.getNode().getNodeName());
        sinkNode.setCapacityFree(Double.valueOf(node.getNode().getCapacityFree()));
        sinkNode.setCapacityFull(Double.valueOf(node.getNode().getCapacityFull()));
        sinkNode.setDescription(node.getNode().getDescription());
        sinkNode.setInheritGeometry(Boolean.valueOf(node.getNode().getInheritGeometry()));
        return sinkNode;
    }
}
