package com.cross_ni.cross.cdc.model.mapper;

import com.cross_ni.cross.cdc.model.source.Node;

import static com.cross_ni.cross.cdc.model.utils.SinkModelUtils.createEmptySinkNode;

public class SinkNodeMapper {

    public com.cross_ni.cross.cdc.model.sink.Node apply(Node sourceNode) {
        final com.cross_ni.cross.cdc.model.sink.Node sinkNode = createEmptySinkNode();
        sinkNode.setOperation(sourceNode.getOp());
        sinkNode.setNodeId(sourceNode.getNodeId());
        sinkNode.setCapacityFree(Double.valueOf(sourceNode.getCapacityFree()));
        sinkNode.setCapacityFull(Double.valueOf(sourceNode.getCapacityFull()));
        sinkNode.setDescription(sourceNode.getDescription());
        sinkNode.setInheritGeometry(Boolean.valueOf(sourceNode.getInheritGeometry()));
        sinkNode.setName(sourceNode.getNodeName());
        sinkNode.setStatus(sourceNode.getNodeStatusId());

        return sinkNode;
    }
}
