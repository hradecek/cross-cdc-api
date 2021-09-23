package com.cross_ni.cross.cdc.model.source.old;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@GeneratedSerde
public class NodeNodeType extends CdcModel {

    private long nodeId;
    private String discriminator;
}
