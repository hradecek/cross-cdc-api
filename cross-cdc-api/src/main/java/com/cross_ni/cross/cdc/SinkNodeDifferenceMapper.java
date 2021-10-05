package com.cross_ni.cross.cdc;

import com.cross_ni.cross.cdc.model.source.Node;

import java.util.function.Function;

public class SinkNodeDifferenceMapper {

    public com.cross_ni.cross.cdc.model.sink.Node apply(Node currentNode, Node newNode) {
        boolean updated = false;
        final com.cross_ni.cross.cdc.model.sink.Node sinkNode = createSinkNode();
        if (isDifferent(Node::getNodeName, currentNode, newNode)) {
            sinkNode.setName(currentNode.getNodeName());
            updated = true;
        }
        if (isDifferent(Node::getNodeStatusId, currentNode, newNode)) {
            sinkNode.setStatus(currentNode.getNodeStatusId());
            updated = true;
        }

        if (updated) {
            sinkNode.setOperation("u");
            return sinkNode;
        } else {
            return null;
        }
    }

    private static <T> boolean isDifferent(Function<Node, T> propertyGetter, Node currentNode, Node newNode) {
        System.out.println(propertyGetter.apply(currentNode));
        System.out.println(propertyGetter.apply(newNode));
        return !propertyGetter.apply(currentNode).equals(propertyGetter.apply(newNode));
    }

    // TODO: check whether JSON2POJO can use nulls instead of empty collections
    private static com.cross_ni.cross.cdc.model.sink.Node createSinkNode() {
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
