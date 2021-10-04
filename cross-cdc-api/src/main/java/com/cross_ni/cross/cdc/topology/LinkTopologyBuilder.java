package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.source.Link;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

// TODO: Use String as default key Serde
class LinkTopologyBuilder implements CdcTopologyBuilder {

    public static final String TOPIC_NAME_SINK_LINK = "cross.cdc.link";

    private static final String TOPIC_NAME_SOURCE_LINK = "crossdb.public.link";

    private final StreamsBuilder builder;

    public LinkTopologyBuilder(StreamsBuilder builder) {
        this.builder = builder;
//        this.customAttributes = customAttributes;
    }

    @Override
    public void build() {
        links().toStream().to(TOPIC_NAME_SINK_LINK, Produced.with(Serdes.String(), JsonSerdes.serde(Link.class)));
//        repartitionByNodeId(customAttributes, links).toStream().to(TOPIC_NAME_SINK_LINK, Produced.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private KTable<String, Link> links() {
        return builder.table(TOPIC_NAME_SOURCE_LINK, Consumed.with(Serdes.String(), JsonSerdes.serde(Link.class)));
    }

//    private KTable<String, CaSetIdEntityId> caSetIdEntityIdMap(KTable<String, Link> entityTable) {
//        return entityTable
//            .toStream()
//            .filterNot((id, entity) -> entity.getOp().equals("u"))
//            .map((entityId, entity) -> KeyValue.pair(entity.getCaSetId(), new CaSetIdEntityId(entity.getCaSetId(), entityId)))
//            .toTable(Materialized.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)));
//    }
//
//    private KTable<String, CustomAttributes> repartitionByNodeId(KTable<String, CustomAttributes> caValues, KTable<String, Link> entityTable) {
//        final KTable<String, CaSetIdEntityId> caSetIdEntityIdMap = caSetIdEntityIdMap(entityTable);
//
//        return caValues.join(caSetIdEntityIdMap, (customAttributes, entity) -> customAttributes.entityId(entity.getEntityId()), Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
//    }
}
