package com.cross_ni.cross.cdc.model.source;

import com.google.gson.annotations.SerializedName;

public enum DebeziumOperation {

    @SerializedName("c") C,
    @SerializedName("r") R,
    @SerializedName("u") U,
    @SerializedName("d") D;
}
