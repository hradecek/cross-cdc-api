package com.cross_ni.cross.cdc.serialization;

import com.cross_ni.cross.cdc.model.aggregate.NodeSnapshot;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.sink.Node;

import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.ArrayList;

public class NodeSinkValueMapper implements ValueMapper<com.cross_ni.cross.cdc.model.aggregate.NodeSnapshot, Node> {

    public static Node nodeMapper(com.cross_ni.cross.cdc.model.source.Node sourceNode) {
        final Node sinkNode = new Node();
        sinkNode.setNodeId(sourceNode.getNodeId());
        sinkNode.setName(sourceNode.getNodeName());
        sinkNode.setDescription(sourceNode.getDescription());
        sinkNode.setInheritGeometry(Boolean.valueOf(sourceNode.getInheritGeometry()));
        sinkNode.setCapacityFree(Double.valueOf(sourceNode.getCapacityFree()));
        sinkNode.setCapacityFull(Double.valueOf(sourceNode.getCapacityFull()));
        sinkNode.setOperation(sourceNode.getOp());
        if (!sourceNode.getOp().equals("r")) {
            sinkNode.setNodeTypes(null);
        }
        return sinkNode;
    }

    public static Node nodeTypesJoiner(Node node, NodeTypes nodeTypes) {
        node.setOperation(nodeTypes.getOperation());
        node.setNodeTypes(new ArrayList<>(nodeTypes.getDiscriminators()));
        return node;
    }

    @Override
    public Node apply(NodeSnapshot nodeSnapshot) {
        return null;
    }
}
