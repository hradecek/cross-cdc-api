package com.cross_ni.cross.cdc.model.source;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public abstract class CdcDebeziumModel {

    @SerializedName("__op")
    private DebeziumOperation op;

    @SerializedName("__source_ts_ms")
    private Double sourceTsMs;

    public boolean isDelete() {
        return op.equals(DebeziumOperation.D);
    }

    public boolean isSnapshot() {
        return op.equals(DebeziumOperation.R);
    }
}
