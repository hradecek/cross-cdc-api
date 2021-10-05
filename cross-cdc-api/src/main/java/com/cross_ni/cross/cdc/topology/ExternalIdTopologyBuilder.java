package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;
import com.cross_ni.cross.cdc.utils.JsonConsumed;
import com.cross_ni.cross.cdc.utils.JsonProduced;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;

import static com.cross_ni.cross.cdc.topology.CdcTopology.TOPIC_NAME_SINK_LINK;
import static com.cross_ni.cross.cdc.topology.CdcTopology.TOPIC_NAME_SINK_NODE;

class ExternalIdTopologyBuilder implements CdcTopologyBuilder {

    private static final String TOPIC_NAME_SOURCE_EXTERNAL_ID = "crossdb.public.external_id";

    // TODO: Must be tested? - if package changes -> fail, should be part of some schema tests
    private static final String EXTERNAL_ID_LINK_ENTITY = "com.cross_ni.cross.db.pojo.core.Link";
    private static final String EXTERNAL_ID_NODE_ENTITY = "com.cross_ni.cross.db.pojo.core.Node";

    @Override
    public void build(final StreamsBuilder builder) {
        final ExternalIdsKTable externalIds = new ExternalIdsKTable(builder, TOPIC_NAME_SOURCE_EXTERNAL_ID);
        sinkExternalIdsFor(externalIds, EXTERNAL_ID_LINK_ENTITY, TOPIC_NAME_SINK_LINK, "sink-link-external-ids");
        sinkExternalIdsFor(externalIds, EXTERNAL_ID_NODE_ENTITY, TOPIC_NAME_SINK_NODE, "sink-node-external-ids");
    }

    private static void sinkExternalIdsFor(ExternalIdsKTable externalIdsKtable, String entity, String sinkTopicName, String processorName) {
        externalIdsKtable.createFor(entity).toStream().to(sinkTopicName, JsonProduced.of(processorName, ExternalIds.class));
    }

    private static class ExternalIdsKTable {

        private final KStream<String, ExternalId> externalIds;

        ExternalIdsKTable(StreamsBuilder builder, String topic) {
            externalIds = builder.stream(topic, JsonConsumed.of("source-external-ids", ExternalId.class));
        }

        KTable<String, ExternalIds> createFor(String entityDiscriminator) {
            return externalIds
                    .filter((k, v) -> v.getEntity().equals(entityDiscriminator))
                    .groupByKey()
                    .aggregate(ExternalIds::new, ExternalIds::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
        }
    }
}
