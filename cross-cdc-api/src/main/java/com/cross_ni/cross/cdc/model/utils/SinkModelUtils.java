package com.cross_ni.cross.cdc.model.utils;

public class SinkModelUtils {

    public static com.cross_ni.cross.cdc.model.sink.Node createEmptySinkNode() {
        final com.cross_ni.cross.cdc.model.sink.Node sinkNode = new com.cross_ni.cross.cdc.model.sink.Node();
        sinkNode.setOperation(null);
        sinkNode.setNodeId(null);
        sinkNode.setCapacityFull(null);
        sinkNode.setCapacityFree(null);
        sinkNode.setDescription(null);
        sinkNode.setInheritGeometry(null);
        sinkNode.setName(null);
        sinkNode.setStatus(null);

        sinkNode.setAliases(null);
        sinkNode.setCustomAttributes(null);
        sinkNode.setExternalIds(null);
        sinkNode.setNodeTypes(null);

        return sinkNode;
    }
}
