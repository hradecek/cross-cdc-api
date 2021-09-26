package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import org.apache.kafka.streams.Topology;

import javax.annotation.Generated;

@GeneratedSerde
public class CdcTopology {

    private static final NodeTopologyBuilder nodeTopologyBuilder = new NodeTopologyBuilder();

    public static Topology build() {
        return nodeTopologyBuilder.build();
    }
}
