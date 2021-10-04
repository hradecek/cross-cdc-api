package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.source.CaDefinition;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Composing all sub-topologies into whole CROSS CDC {@link Topology} in kafka streams.
 * <p>
 * Resulting topology represents CROSS CDC API.
 */
public class CdcTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(CdcTopology.class);

    private static final String TOPIC_NAME_SOURCE_CA_DEF = "crossdb.public.ca_def";

    private final StreamsBuilder streamsBuilder = new StreamsBuilder();

    /**
     * Build whole CROSS CDC {@link Topology kafka streams topology}.
     *
     * @return resulting {@link Topology}
     */
    // This is the only entry point for com.cross_ni.cross.cdc.topology package
    public Topology create() {
//        final KTable<String, CustomAttributes> customAttributes = new CustomAttributesTopologyBuilder(streamsBuilder).customAttributes();
        new ExternalIdTopology(streamsBuilder).build();
        new LinkTopologyBuilder(streamsBuilder).build();
        final NodeTopologyBuilder nodeTopologyBuilder = new NodeTopologyBuilder(streamsBuilder);
        nodeTopologyBuilder.build();
        new CustomAttributesTopologyBuilder(streamsBuilder, nodeTopologyBuilder.getCaSetMap()).build();

        return buildAndLogTopology(streamsBuilder);
    }

    private static Topology buildAndLogTopology(final StreamsBuilder builder) {
        final Topology topology = builder.build();
        LOGGER.info(topology.describe().toString());

        return topology;
    }
}
