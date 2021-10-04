package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import static com.cross_ni.cross.cdc.topology.LinkTopologyBuilder.TOPIC_NAME_SINK_LINK;
import static com.cross_ni.cross.cdc.topology.NodeTopologyBuilder.TOPIC_NAME_SINK_NODE;

public class ExternalIdTopology implements CdcTopologyBuilder {

    private static final String TOPIC_NAME_SOURCE_EXTERNAL_ID = "crossdb.public.external_id";

    // TODO: Must be tested - if package changes -> fail
    private static final String EXTERNAL_ID_LINK_ENTITY = "com.cross_ni.cross.db.pojo.core.Link";
    private static final String EXTERNAL_ID_NODE_ENTITY = "com.cross_ni.cross.db.pojo.core.Node";

    private final StreamsBuilder builder;

    public ExternalIdTopology(StreamsBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void build() {
        final ExternalIdsCreator externalIdsCreator = new ExternalIdsCreator(builder, TOPIC_NAME_SOURCE_EXTERNAL_ID);
        externalIdsCreator.createKTable(EXTERNAL_ID_NODE_ENTITY).toStream().to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
        externalIdsCreator.createKTable(EXTERNAL_ID_LINK_ENTITY).toStream().to(TOPIC_NAME_SINK_LINK, Produced.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
    }

    private static class ExternalIdsCreator {

        private final KStream<String, ExternalId> externalIds;

        public ExternalIdsCreator(StreamsBuilder builder, String topic) {
            this.externalIds = builder.stream(topic, Consumed.with(Serdes.String(), JsonSerdes.serde(ExternalId.class)));
        }

        public KTable<String, ExternalIds> createKTable(String entityDiscriminator) {
            return externalIds
                    .filter((k, v) -> v.getEntity().equals(entityDiscriminator))
                    .groupByKey()
                    .aggregate(ExternalIds::new, ExternalIds::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
        }
    }
}
