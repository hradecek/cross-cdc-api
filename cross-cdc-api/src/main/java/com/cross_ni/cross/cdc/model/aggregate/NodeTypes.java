package com.cross_ni.cross.cdc.model.aggregate;

import com.cross_ni.cross.cdc.model.source.NodeNodeType;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class NodeTypes {

    private final Set<String> discriminators = new HashSet<>();

    private String operation;
    private String nodeId;

    public NodeTypes aggregate(String nodeId, NodeNodeType sourceNodeNodeType) {
        if (sourceNodeNodeType.getOp().equals("d")) {
            discriminators.remove(sourceNodeNodeType.getDiscriminator());
        } else {
            discriminators.add(sourceNodeNodeType.getDiscriminator());
        }
        if (sourceNodeNodeType.getOp().equals("r")) {
            operation = "r";
        } else {
            operation = "u";
        }
        this.nodeId = nodeId;

        return this;
    }

    public static NodeTypes aggregator(String key, NodeNodeType sourceNodeNodeType, NodeTypes aggregatedNodeTypes) {
        return aggregatedNodeTypes.aggregate(key, sourceNodeNodeType);
    }
}
