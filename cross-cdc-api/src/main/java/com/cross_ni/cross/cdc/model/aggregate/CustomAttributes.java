package com.cross_ni.cross.cdc.model.aggregate;

import com.cross_ni.cross.cdc.model.source.CustomAttribute;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class CustomAttributes {

    private final Set<CustomAttribute> customAttributes = new HashSet<>();

    private String operation;
    private String caSetIt;
    private double sourceTsMs = 0.0;
    private String nodeId;

    public CustomAttributes nodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

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

        this.caSetIt = caSetId;

        return this;
    }


    public static CustomAttributes aggregator(String nodeId, CustomAttribute sourceCustomAttribute, CustomAttributes aggregatedCustomAttributes) {
        return aggregatedCustomAttributes.aggregate(nodeId, sourceCustomAttribute);
    }
}
