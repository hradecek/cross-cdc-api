package com.cross_ni.cross.cdc.model.source.old;

import com.google.gson.annotations.SerializedName;

public enum CdcOperation {

    @SerializedName("c") CREATE,
    @SerializedName("r") SNAPSHOT,
    @SerializedName("u") UPDATE,
    @SerializedName("d") DELETE,
}
