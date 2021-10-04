package com.cross_ni.cross.cdc.model.source;

public interface CdcEntityModel<ID> extends CdcModel {

    ID getPrimaryId();
}
