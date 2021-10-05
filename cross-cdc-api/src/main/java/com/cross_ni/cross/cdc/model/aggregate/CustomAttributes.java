package com.cross_ni.cross.cdc.model.aggregate;

import com.cross_ni.cross.cdc.model.source.CustomAttribute;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

// TODO: Will be re-worked
@Getter
public class CustomAttributes extends EntityAggregate<String, CustomAttribute, CustomAttributes> {

    private final Set<CustomAttribute> customAttributes = new HashSet<>();

    private String caSetId;

    public static CustomAttributes aggregator(String nodeId, CustomAttribute sourceCustomAttribute, CustomAttributes aggregatedCustomAttributes) {
        return aggregatedCustomAttributes.aggregate(nodeId, sourceCustomAttribute);
    }

    @Override
    public CustomAttributes aggregate(String caSetId, CustomAttribute sourceCustomAttribute) {
        if (sourceCustomAttribute.getOp().equals("d")) {
            customAttributes.remove(sourceCustomAttribute);
        } else {
            customAttributes.remove(sourceCustomAttribute);
            customAttributes.add(sourceCustomAttribute);
        }

        if (sourceCustomAttribute.getOp().equals("r")) {
            operation = "r";
        } else {
            operation = "u";
        }
        sourceTsMs = Math.max(sourceTsMs, sourceCustomAttribute.getSourceTsMs());

        this.caSetId = caSetId;

        return this;
    }

    public CustomAttributes entityId(String entityId) {
        this.entityId = entityId;
        return this;
    }
}
