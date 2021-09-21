package com.cross_ni.cross.cdc.model.source;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@GeneratedSerde
public class NodeType {

    private final String discriminator;
    private final String name;
}
