package com.cross_ni.cross.cdc.model.source;

public interface CdcEntityWithCaSetModel<ID, CID> extends CdcEntityModel<ID> {

    CID getCaSetId();
}
