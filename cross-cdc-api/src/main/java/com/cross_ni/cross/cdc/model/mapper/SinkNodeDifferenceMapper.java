package com.cross_ni.cross.cdc.model.mapper;

import com.cross_ni.cross.cdc.model.source.Node;

import java.util.function.Function;

import static com.cross_ni.cross.cdc.model.utils.SinkModelUtils.createEmptySinkNode;

public class SinkNodeDifferenceMapper {

    public com.cross_ni.cross.cdc.model.sink.Node apply(Node currentNode, Node newNode) {
        boolean updated = false;
        final com.cross_ni.cross.cdc.model.sink.Node sinkNode = createEmptySinkNode();
        if (isDifferent(Node::getNodeName, currentNode, newNode)) {
            sinkNode.setName(newNode.getNodeName());
            updated = true;
        }
        if (isDifferent(Node::getNodeStatusId, currentNode, newNode)) {
            sinkNode.setStatus(newNode.getNodeStatusId());
            updated = true;
        }

        if (updated) {
            sinkNode.setOperation("u");
        }
        return sinkNode;
    }

    private static <T> boolean isDifferent(Function<Node, T> propertyGetter, Node currentNode, Node newNode) {
        return !propertyGetter.apply(currentNode).equals(propertyGetter.apply(newNode));
    }
}
