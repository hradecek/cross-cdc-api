package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.mapper.SinkLinkExternalIdsMapper;
import com.cross_ni.cross.cdc.model.mapper.SinkNodeExternalIdsMapper;
import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.utils.JsonConsumed;
import com.cross_ni.cross.cdc.utils.JsonMaterialized;
import com.cross_ni.cross.cdc.utils.JsonProduced;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import static com.cross_ni.cross.cdc.topology.CdcTopology.TOPIC_NAME_SINK_NODE;

class ExternalIdTopologyBuilder implements CdcTopologyBuilder {

    private static final String TOPIC_NAME_SOURCE_EXTERNAL_ID = "crossdb.public.external_id";

    // TODO: Must be tested? - if package changes -> fail, should be part of some schema tests
    private static final String EXTERNAL_ID_LINK_ENTITY = "com.cross_ni.cross.db.pojo.core.Link";
    private static final String EXTERNAL_ID_NODE_ENTITY = "com.cross_ni.cross.db.pojo.core.Node";

    @Override
    public void build(final StreamsBuilder builder) {
        final ExternalIdsKTable externalIds = new ExternalIdsKTable(builder, TOPIC_NAME_SOURCE_EXTERNAL_ID);
        sinkNodeExternalIdsFor(externalIds);
        sinkLinkExternalIdsFor(externalIds);
    }

    // TODO: Duplication - e.g. create a specialized class "Sinker" with configuration?
    private static void sinkNodeExternalIdsFor(ExternalIdsKTable externalIdsKtable) {
        externalIdsKtable
            .createFor(EXTERNAL_ID_NODE_ENTITY)
            .toStream()
            .mapValues(new SinkNodeExternalIdsMapper())
            .to(TOPIC_NAME_SINK_NODE, JsonProduced.of("sink-node-external-ids", com.cross_ni.cross.cdc.model.sink.Node.class));
    }

    private static void sinkLinkExternalIdsFor(ExternalIdsKTable externalIdsKtable) {
        externalIdsKtable
            .createFor(EXTERNAL_ID_LINK_ENTITY)
            .toStream()
            .mapValues(new SinkLinkExternalIdsMapper())
            .to(TOPIC_NAME_SINK_NODE, JsonProduced.of("sink-link-external-ids", com.cross_ni.cross.cdc.model.sink.Link.class));
    }

    private static class ExternalIdsKTable {

        private final KStream<String, ExternalId> externalIds;

        ExternalIdsKTable(StreamsBuilder builder, String topic) {
            externalIds = builder.stream(topic, JsonConsumed.of("source-external-ids", ExternalId.class));
        }

        KTable<String, ExternalIds> createFor(String entityDiscriminator) {
            return externalIds
                    .filter((k, v) -> v.getEntity().equals(entityDiscriminator))
                    .groupByKey(Grouped.as("group-external-ids-by-entity-id"))
                    .aggregate(ExternalIds::new, ExternalIds::aggregator, JsonMaterialized.of("aggregate-external-ids", ExternalIds.class));
        }
    }
}
