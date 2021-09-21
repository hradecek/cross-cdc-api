package com.cross_ni.cross.cdc.model.source;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@GeneratedSerde
public class CustomAttribute {

    private final String caDefId;
    private final long caSetId;
    // TODO: Can the "value names" be inferred from cross_db entities somehow?
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
    private final Object value;

    private CaDefinition caDefinition;

    public CustomAttribute aggregate(CaDefinition caDefinition) {
        this.caDefinition = caDefinition;
        return this;
    }
}
