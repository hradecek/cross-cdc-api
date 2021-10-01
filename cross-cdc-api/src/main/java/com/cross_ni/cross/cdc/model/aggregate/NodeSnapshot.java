package com.cross_ni.cross.cdc.model.aggregate;

import com.cross_ni.cross.cdc.model.source.CustomAttribute;
import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.model.source.Node;

import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
public class NodeSnapshot {

    private final Node node;
    private Set<String> discriminators;
    private Set<ExternalId> externalIds = new HashSet<>();
    private Set<CustomAttribute> customAttributes;

    @Getter
    private String operation;
    @Getter
    private String nodeId;
    @Getter
    private double sourceTsMs;
    @Getter
    private String caSetId;

    public NodeSnapshot(Node node) {
        this.operation = node.getOp();
        this.nodeId = node.getNodeId();
        this.sourceTsMs = node.getSourceTsMs();
        this.caSetId = node.getCaSetId();
        this.node = node;
    }

    public NodeSnapshot join(CustomAttributes sourceCustomAttributes) {
        if (sourceCustomAttributes == null) {
            return this;
        }
        if (operation.equals("r") && sourceCustomAttributes.getOperation().equals("r")) {
            operation = "r";
            customAttributes = sourceCustomAttributes.getCustomAttributes();
        } else {
            if (node.getSourceTsMs() < sourceCustomAttributes.getSourceTsMs()) {
                customAttributes = sourceCustomAttributes.getCustomAttributes();
                operation = sourceCustomAttributes.getOperation();
            }
        }
        return this;
    }

    public NodeSnapshot join(NodeTypes sourceNodeTypes) {
        if (operation.equals("r") && sourceNodeTypes.getOperation().equals("r")) {
            operation = "r";
            discriminators = sourceNodeTypes.getDiscriminators();
        } else {
            if (node.getSourceTsMs() < sourceNodeTypes.getSourceTsMs()) {
                discriminators = sourceNodeTypes.getDiscriminators();
                operation = sourceNodeTypes.getOperation();
            }
        }
        return this;
    }

    public NodeSnapshot join(ExternalIds externalIds) {
        if (operation.equals("r") && externalIds.getOperation().equals("r")) {
            operation = "r";
            this.externalIds = externalIds.getExternalIds();
        } else {
            if (node.getSourceTsMs() > externalIds.getSourceTsMs()) {
                this.externalIds = externalIds.getExternalIds();
                operation = externalIds.getOperation();
            }
        }
        return this;
    }
}
