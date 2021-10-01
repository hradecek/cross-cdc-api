package com.cross_ni.cross.cdc.model.source;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@RequiredArgsConstructor
public class CustomAttribute {

    @SerializedName("__op")
    private String op;

    @SerializedName("__source_ts_ms")
    private Double sourceTsMs;

    @SerializedName("ca_def_id")
    private String caDefId;

    // TODO: Can the "value names" be inferred from cross_db entities somehow?
    // TODO: Values' names can be unified by custom Kafka SMT
    @SerializedName(
        value = "value",
        alternate = {
            "address_id",
            "bool_value",
            "country_id",
            "date_value",
            "double_value",
            "enum_value",
            "int_value",
            "ip_pool_id",
            "long_value",
            "material_id",
            "node_id",
            "large_string",
            "radio_band_id",
            "radio_band_width_id",
            "radio_channel_id",
            "radio_kmb_id",
            "short_value",
            "string_value",
            "timestamp_value",
            "time_value",
            "url_value",
        }
    )
    private Object value;

    private CaDefinition caDefinition;

    public CustomAttribute join(CaDefinition caDefinition) {
        this.caDefinition = caDefinition;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomAttribute that = (CustomAttribute) o;
        return Objects.equals(caDefId, that.caDefId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caDefId);
    }
}
