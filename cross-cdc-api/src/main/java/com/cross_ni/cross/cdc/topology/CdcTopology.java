package com.cross_ni.cross.cdc.topology;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Facade class composing all sub-topologies into whole CROSS CDC {@link Topology} in kafka streams.
 * <p>
 * Resulting topology represents CROSS CDC API.
 */
public class CdcTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(CdcTopology.class);

    private static final List<Function<StreamsBuilder, CdcTopologyBuilder>> BUILDERS =
        Arrays.asList(
            NodeTopologyBuilder::create,
            LinkTopologyBuilder::create
        );

    private CdcTopology() {
        throw new AssertionError("Cannot instantiate " + CdcTopology.class.getName());
    }

    /**
     * Build whole CROSS CDC {@link Topology kafka streams topology}.
     *
     * @return resulting {@link Topology}
     */
    // This is the only entry point for com.cross_ni.cross.cdc.topology package
    public static Topology create() {
        final StreamsBuilder streamsBuilder = new StreamsBuilder();
        BUILDERS.stream().map(builder -> builder.apply(streamsBuilder)).forEach(CdcTopologyBuilder::build);

        return buildAndLogTopology(streamsBuilder);
    }

    private static Topology buildAndLogTopology(final StreamsBuilder builder) {
        final Topology topology = builder.build();
        LOGGER.info(topology.describe().toString());

        return topology;
    }
}
