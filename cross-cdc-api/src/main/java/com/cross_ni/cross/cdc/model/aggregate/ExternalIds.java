package com.cross_ni.cross.cdc.model.aggregate;

import com.cross_ni.cross.cdc.model.source.ExternalId;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ExternalIds extends EntityAggregate<String, ExternalId, ExternalIds> {

    private final Set<ExternalId> externalIds = new HashSet<>();

    public static ExternalIds aggregator(String key, ExternalId sourceExternalId, ExternalIds aggregatedExternalIds) {
        return aggregatedExternalIds.aggregate(key, sourceExternalId);
    }

    @Override
    public ExternalIds aggregate(String entityId, ExternalId sourceExternalId) {
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
        this.entityId = entityId;

        return this;
    }
}
