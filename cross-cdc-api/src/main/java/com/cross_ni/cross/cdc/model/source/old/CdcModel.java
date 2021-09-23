package com.cross_ni.cross.cdc.model.source.old;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class CdcModel {

    @Setter
    @SerializedName("__op")
    private CdcOperation operation;
}
