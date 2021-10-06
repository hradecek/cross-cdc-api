package com.cross_ni.cross.cdc.model.source;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Link extends CdcDebeziumModel implements CdcEntityWithCaSetModel<String> {

    private String linkId;
    private Double capacityFull;
    private Double capacityFree;
    @SerializedName("link_type_id")
    private String linkType;
    @SerializedName("link_status_id")
    private String linkStatus;
    private String routingPolicy;
    private String name;
    private String description;
    private Double channelNumber;
    private Double mainFibrilCount;
    private Double protectFibrilCount;
    private String caSetId;
}
