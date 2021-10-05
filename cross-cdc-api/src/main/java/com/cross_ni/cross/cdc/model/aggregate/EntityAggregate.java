package com.cross_ni.cross.cdc.model.aggregate;

import lombok.Getter;

@Getter
public abstract class EntityAggregate<K, V, A> {

    protected String operation;
    protected String entityId;
    protected double sourceTsMs = 0.0;

    public abstract A aggregate(K nodeId, V sourceNodeNodeType);
}
