package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.model.source.Link;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

// TODO: Use String as default key Serde
class LinkTopologyBuilder implements CdcTopologyBuilder {

    private static final String TOPIC_NAME_SINK_LINK = "cross.cdc.link";
    private static final String TOPIC_NAME_SOURCE_EXTERNAL_ID = "crossdb.public.external_id";

    private static final String TOPIC_NAME_SOURCE_LINK = "crossdb.public.link";

    private final StreamsBuilder builder;
//    private final CustomAttributesTopologyBuilder<Link> customAttributesTopologyBuilder;

    public static LinkTopologyBuilder create(final StreamsBuilder builder) {
        return new LinkTopologyBuilder(builder);
    }

    private LinkTopologyBuilder(final StreamsBuilder builder) {
        this.builder = builder;
//        this.customAttributesTopologyBuilder = new CustomAttributesTopologyBuilder<>(builder);
    }

    @Override
    public void build() {
        final KTable<String, Link> links = links();
        final KTable<String, ExternalIds> externalIds = externalIds();

        links.toStream().to(TOPIC_NAME_SINK_LINK, Produced.with(Serdes.String(), JsonSerdes.serde(Link.class)));
        externalIds.toStream().to(TOPIC_NAME_SINK_LINK, Produced.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
//        customAttributesTopologyBuilder.customAttributes(links).toStream().to(TOPIC_NAME_SINK_LINK, Produced.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private static final String EXTERNAL_ID_LINK_ENTITY = "com.cross_ni.cross.db.pojo.core.Link";

    private KTable<String, Link> links() {
        return builder.table(TOPIC_NAME_SOURCE_LINK, Consumed.with(Serdes.String(), JsonSerdes.serde(Link.class)));
    }

    private KTable<String, ExternalIds> externalIds() {
        return builder
            .stream(TOPIC_NAME_SOURCE_EXTERNAL_ID, Consumed.with(Serdes.String(), JsonSerdes.serde(ExternalId.class)))
            .filter((k, v) -> v.getEntity().equals(EXTERNAL_ID_LINK_ENTITY))
            .groupByKey()
            .aggregate(ExternalIds::new, ExternalIds::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
    }
}
