package com.cross_ni.cross.cdc.model.mapper;

import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;

import java.util.ArrayList;

import static com.cross_ni.cross.cdc.model.utils.SinkModelUtils.createEmptySinkNode;

public class SinkNodeTypesMapper implements SinkNodeValueMapper<NodeTypes> {

    @Override
    public com.cross_ni.cross.cdc.model.sink.Node apply(NodeTypes nodeTypes) {
        final com.cross_ni.cross.cdc.model.sink.Node sinkNode = createEmptySinkNode();
        sinkNode.setOperation(nodeTypes.getOperation());
        sinkNode.setNodeId(nodeTypes.getEntityId());
        sinkNode.setNodeTypes(new ArrayList<>(nodeTypes.getDiscriminators()));

        return sinkNode;
    }
}
