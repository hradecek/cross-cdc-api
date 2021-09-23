package com.cross_ni.cross.cdc.model.source.old;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@GeneratedSerde
public class Node extends CdcModel {

    private long nodeId;
    private long caSetId;
    private String nodeName;
    private String description;

    private Set<NodeType> nodeTypes = new HashSet<>();
    private Set<CustomAttribute> customAttributes = new HashSet<>();

    public Node aggregate(final CustomAttributes customAttributes) {
        this.customAttributes.addAll(customAttributes.getCustomAttributes());
        return this;
    }

    public Node aggregate(final NodeTypes nodeTypes) {
        this.nodeTypes.addAll(nodeTypes.getNodeTypes());
        return this;
    }
}
