package com.cross_ni.cross.cdc;

import com.cross_ni.cross.cdc.model.source.Node;

import java.util.function.Function;

public class SinkNodeMapper {

    public com.cross_ni.cross.cdc.model.sink.Node apply(Node sourceNode) {
        final com.cross_ni.cross.cdc.model.sink.Node sinkNode = createSinkNode();
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

    // TODO: check whether JSON2POJO can use nulls instead of empty collections
    private static com.cross_ni.cross.cdc.model.sink.Node createSinkNode() {
        final com.cross_ni.cross.cdc.model.sink.Node sinkNode = new com.cross_ni.cross.cdc.model.sink.Node();
        sinkNode.setAliases(null);
        sinkNode.setCustomAttributes(null);
        sinkNode.setExternalIds(null);
        sinkNode.setNodeTypes(null);

        return sinkNode;
    }
}
