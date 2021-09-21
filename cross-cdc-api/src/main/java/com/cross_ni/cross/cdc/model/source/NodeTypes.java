package com.cross_ni.cross.cdc.model.source;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@GeneratedSerde
public class NodeTypes {

    private final Set<NodeType> nodeTypes = new HashSet<>();

    public NodeTypes aggregate(final NodeType nodeType) {
        nodeTypes.add(nodeType);
        return this;
    }
}
