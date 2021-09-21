package com.cross_ni.cross.cdc.topology;

import org.apache.kafka.streams.Topology;

public class CdcTopology {

    private static final NodeTopologyBuilder nodeTopologyBuilder = new NodeTopologyBuilder();

    public static Topology build() {
        return nodeTopologyBuilder.build();
    }
}
