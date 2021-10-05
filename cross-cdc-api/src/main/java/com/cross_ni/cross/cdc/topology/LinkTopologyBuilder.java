package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.source.Link;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import static com.cross_ni.cross.cdc.topology.CdcTopology.TOPIC_NAME_SINK_LINK;

class LinkTopologyBuilder implements CdcTopologyBuilder {

    private static final String TOPIC_NAME_SOURCE_LINK = "crossdb.public.link";

    @Override
    public void build(final StreamsBuilder builder) {
        final KTable<String, Link> links = links(builder);
        links.toStream().to(TOPIC_NAME_SINK_LINK, Produced.with(Serdes.String(), JsonSerdes.serde(Link.class)));
    }

    private KTable<String, Link> links(final StreamsBuilder builder) {
        return builder.table(TOPIC_NAME_SOURCE_LINK, Consumed.with(Serdes.String(), JsonSerdes.serde(Link.class)));
    }
}
