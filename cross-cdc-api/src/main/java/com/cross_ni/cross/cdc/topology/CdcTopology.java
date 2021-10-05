package com.cross_ni.cross.cdc.topology;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Composing all sub-topologies into whole CROSS CDC {@link Topology} in kafka streams.
 * <p>
 * Resulting topology represents CROSS CDC API.
 */
public class CdcTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(CdcTopology.class);

    private final StreamsBuilder streamsBuilder = new StreamsBuilder();

    /**
     * Build whole CROSS CDC {@link Topology kafka streams topology}.
     *
     * @return resulting {@link Topology}
     */
    // This is the only entry point for com.cross_ni.cross.cdc.topology package
    public Topology create() {
        new ExternalIdTopologyBuilder().build(streamsBuilder);
        final LinkTopologyBuilder linkTopologyBuilder = new LinkTopologyBuilder();
        linkTopologyBuilder.build(streamsBuilder);

        final NodeTopologyBuilder nodeTopologyBuilder = new NodeTopologyBuilder();
        nodeTopologyBuilder.build(streamsBuilder);

        new CustomAttributesTopologyBuilder(streamsBuilder, nodeTopologyBuilder.getCaSetMap(), linkTopologyBuilder.getCaSetMap()).build();

        return buildAndLogTopology(streamsBuilder);
    }

    private static Topology buildAndLogTopology(final StreamsBuilder builder) {
        final Topology topology = builder.build();
        LOGGER.info(topology.describe().toString());

        return topology;
    }
}
