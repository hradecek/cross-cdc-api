package com.cross_ni.cross.cdc.model.aggregate;

import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ExternalIds {

    private final Set<ExternalId> externalIds = new HashSet<>();

    private String operation;
    private String nodeId;
    private double sourceTsMs = 0.0;

    public ExternalIds aggregate(String nodeId, ExternalId sourceExternalId) {
        if (sourceExternalId.getOp().equals("d")) {
            externalIds.remove(sourceExternalId);
        } else {
            externalIds.add(sourceExternalId);
        }
        if (sourceExternalId.getOp().equals("r")) {
            operation = "r";
        } else {
            operation = "u";
        }
        sourceTsMs = Math.max(sourceTsMs, sourceExternalId.getSourceTsMs());

        this.nodeId = nodeId;

        return this;
    }

    public static ExternalIds aggregator(String key, ExternalId sourceExternalId, ExternalIds aggregatedExternalIds) {
        return aggregatedExternalIds.aggregate(key, sourceExternalId);
    }
}
