package com.cross_ni.cross.cdc.model.source;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
@GeneratedSerde
public class Node {

    private final long nodeId;
    private final long caSetId;
    private final String nodeName;
    private final String description;
    private final Set<NodeType> nodeTypes = new HashSet<>();
    private final Set<CustomAttribute> customAttributes = new HashSet<>();

    public Node aggregate(final CustomAttributes customAttributes) {
        this.customAttributes.addAll(customAttributes.getCustomAttributes());
        return this;
    }

    public Node aggregate(final NodeTypes nodeTypes) {
        this.nodeTypes.addAll(nodeTypes.getNodeTypes());
        return this;
    }
}
