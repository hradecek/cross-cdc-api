package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.source.Link;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Repartitioned;

import lombok.Getter;

// TODO: Use String as default key Serde
class LinkTopologyBuilder implements CdcTopologyBuilder {

    public static final String TOPIC_NAME_SINK_LINK = "cross.cdc.link";

    private static final String TOPIC_NAME_SOURCE_LINK = "crossdb.public.link";

    private final StreamsBuilder builder;

    @Getter
    private KTable<String, CaSetIdEntityId> caSetMap;

    public LinkTopologyBuilder(StreamsBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void build() {
        final KTable<String, Link> links = links();
        links.toStream().to(TOPIC_NAME_SINK_LINK, Produced.with(Serdes.String(), JsonSerdes.serde(Link.class)));

        caSetMap = caSetIdMap(links);
    }

    private KTable<String, Link> links() {
        return builder.table(TOPIC_NAME_SOURCE_LINK, Consumed.with(Serdes.String(), JsonSerdes.serde(Link.class)));
    }

    private KTable<String, CaSetIdEntityId> caSetIdMap(KTable<String, Link> links) {
        return links
            .toStream()
            .selectKey((linkId, link) -> link.getCaSetId())
            .mapValues(link -> new CaSetIdEntityId(link.getCaSetId(), link.getLinkId()))
            .repartition(Repartitioned.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)))
            .toTable();
    }
}
