package com.cross_ni.cross.cdc.topology;

import org.apache.kafka.streams.StreamsBuilder;

interface CdcTopologyBuilder {

    void build(final StreamsBuilder builder);
}
