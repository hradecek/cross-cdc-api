package com.cross_ni.cross.cdc.model.aggregate;

import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;

import java.util.HashSet;
import java.util.Set;

public class NodeSnapshot {

    private final Set<String> discriminators = new HashSet<>();
    private final Set<ExternalId> externalIds = new HashSet<>();

    private String operation;
    private String nodeId;
    private double sourceTsMs = 0.0;

    public NodeTypes aggregate(String nodeId, NodeNodeType sourceNodeNodeType) {
//        if (sourceNodeNodeType.getOp().equals("d")) {
//            discriminators.remove(sourceNodeNodeType.getDiscriminator());
//        } else {
//            discriminators.add(sourceNodeNodeType.getDiscriminator());
//        }
//        if (sourceNodeNodeType.getOp().equals("r")) {
//            operation = "r";
//        } else {
//            operation = "u";
//        }
//        sourceTsMs = Math.max(sourceTsMs, sourceNodeNodeType.getSourceTsMs());
//
//        this.nodeId = nodeId;
//
//        return this;
    }

    public static NodeTypes aggregator(String key, NodeNodeType sourceNodeNodeType, NodeTypes aggregatedNodeTypes) {
        return aggregatedNodeTypes.aggregate(key, sourceNodeNodeType);
    }
}
