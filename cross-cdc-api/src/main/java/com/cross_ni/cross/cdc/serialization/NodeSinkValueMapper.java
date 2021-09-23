package com.cross_ni.cross.cdc.serialization;

import com.cross_ni.cross.cdc.model.sink.CustomAttributeDefinition;
import com.cross_ni.cross.cdc.model.sink.Node;
import com.cross_ni.cross.cdc.model.sink.NodeType;
import com.cross_ni.cross.cdc.model.sink.CustomAttribute;

import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NodeSinkValueMapper implements ValueMapper<com.cross_ni.cross.cdc.model.source.Node, Node> {

    @Override
    public Node apply(com.cross_ni.cross.cdc.model.source.Node sourceNode) {
        final Node node = new Node();
        node.setName(sourceNode.getNodeName());
        node.setNodeTypes(mapNodeTypes(sourceNode.getNodeTypes()));
        node.setCustomAttributes(mapCustomAttributes(sourceNode.getCustomAttributes()));

        return node;
    }

    private static List<NodeType> mapNodeTypes(Set<com.cross_ni.cross.cdc.model.source.NodeType> sourceNodeTypes) {
        return sourceNodeTypes.stream().map(NodeSinkValueMapper::mapNodeType).collect(Collectors.toList());
    }

    private static NodeType mapNodeType(com.cross_ni.cross.cdc.model.source.NodeType sourceNodeType) {
        final NodeType nodeType = new NodeType();
        nodeType.setName(sourceNodeType.getName());
        nodeType.setDiscriminator(sourceNodeType.getDiscriminator());

        return nodeType;
    }

    private static List<CustomAttribute> mapCustomAttributes(Set<com.cross_ni.cross.cdc.model.source.CustomAttribute> sourceCustomAttributes) {
        return sourceCustomAttributes.stream().map(NodeSinkValueMapper::mapCustomAttribute).collect(Collectors.toList());
    }

    private static CustomAttribute mapCustomAttribute(com.cross_ni.cross.cdc.model.source.CustomAttribute sourceCustomAttribute) {
        final CustomAttribute customAttribute = new CustomAttribute();
        customAttribute.setValue(sourceCustomAttribute.getValue());
        customAttribute.setDefinition(mapCustomAttributeDefinition(sourceCustomAttribute.getCaDefinition()));

        return customAttribute;
    }

    private static CustomAttributeDefinition mapCustomAttributeDefinition(
        com.cross_ni.cross.cdc.model.source.CaDefinition sourceCustomAttributeDefinition) {

        final CustomAttributeDefinition customAttributeDefinition = new CustomAttributeDefinition();
        customAttributeDefinition.setAttributeName(sourceCustomAttributeDefinition.getAttributeName());
        customAttributeDefinition.setAttributeClass(sourceCustomAttributeDefinition.getAttributeClass());

        return customAttributeDefinition;
    }
}
