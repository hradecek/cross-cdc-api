package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Repartitioned;
import org.apache.kafka.streams.state.KeyValueStore;

import lombok.Getter;

// TODO: Use String as default key Serde
class NodeTopologyBuilder implements CdcTopologyBuilder {

    public static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private final StreamsBuilder builder;

    public NodeTopologyBuilder(StreamsBuilder builder) {
        this.builder = builder;
    }

    @Getter
    private KTable<String, CaSetIdEntityId> caSetMap;

    @Override
    public void build() {
        final KTable<String, Node> nodes = nodes();
        final KTable<String, NodeTypes> nodeTypes = nodeNodeTypes();

        nodes.toStream(Named.as("stream-nodes")).to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(Node.class)).withName("sink-nodes"));
        caSetMap = nodes.toStream().selectKey((nodeId, node) -> node.getCaSetId()).mapValues(node -> new CaSetIdEntityId(node.getCaSetId(), node.getNodeId())).repartition(Repartitioned.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class))).toTable();
        nodeTypes.toStream(Named.as("stream-node-types")).to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)).withName("sink-node-types"));
    }

    private KTable<String, Node> nodes() {
        return builder.table(TOPIC_NAME_SOURCE_NODE, Consumed.with(Serdes.String(), JsonSerdes.serde(Node.class)).withName("source-node"));
    }

    private KTable<String, NodeTypes> nodeNodeTypes() {
        return
            builder
                .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)).withName("source-node-type"))
                .groupByKey()
                .aggregate(NodeTypes::new, NodeTypes::aggregator, Named.as("aggregate-node-types"),
                    Materialized.<String, NodeTypes, KeyValueStore<Bytes, byte[]>>as("aggregate-node-types-store").withKeySerde(Serdes.String()).withValueSerde(JsonSerdes.serde(NodeTypes.class)));
    }
//
//    private KTable<String, CaSetIdEntityId> caSetIdEntityIdMap(KTable<String, Node> entityTable) {
//        return entityTable
//            .toStream()
//            .filterNot((id, entity) -> entity.getOp().equals("u"))
//            .map((entityId, entity) -> KeyValue.pair(entity.getCaSetId(), new CaSetIdEntityId(entity.getCaSetId(), entityId)))
//            .toTable(Materialized.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)));
//    }
//
//    private KTable<String, CustomAttributes> repartitionByNodeId(KTable<String, CustomAttributes> caValues, KTable<String, Node> entityTable) {
//        final KTable<String, CaSetIdEntityId> caSetIdEntityIdMap = caSetIdEntityIdMap(entityTable);
//
//        return caValues.join(caSetIdEntityIdMap, (customAttributes, node) -> customAttributes.entityId(node.getEntityId()), Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
//    }
//
//    public KTable<String, CustomAttributes> customAttributes(KTable<String, Node> entityTable) {
//        final KStream<String, CustomAttribute> caValues = caValues();
//
//        return repartitionByNodeId(aggregate(joinCaDefinitions(caValues)), entityTable);
//    }
//
//    private static final String TOPIC_NAME_SOURCE_CA_VAL = "crossdb.public.ca_val";
//    private KStream<String, CustomAttribute> caValues() {
//        return builder.stream(TOPIC_NAME_SOURCE_CA_VAL, Consumed.with(Serdes.String(), JsonSerdes.serde(CustomAttribute.class)));
//    }
//
//    private KStream<String, CustomAttribute> joinCaDefinitions(KStream<String, CustomAttribute> caValues) {
//        return caValues.join(customAttributes, (caSetId, customAttribute) -> customAttribute.getCaDefId(), CustomAttribute::join);
//    }
//
//    private KTable<String, CustomAttributes> aggregate(KStream<String, CustomAttribute> caValues) {
//        return caValues
//            .groupByKey()
//            .aggregate(CustomAttributes::new, CustomAttributes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
//    }
//
//    private KTable<String, CustomAttributes> repartitionByNodeId(KTable<String, CustomAttributes> caValues, KTable<String, Node> entityTable) {
//        final KTable<String, CaSetIdEntityId> caSetIdEntityIdMap = caSetIdEntityIdMap(entityTable);
//
//        return caValues.join(caSetIdEntityIdMap, (customAttributes, node) -> customAttributes.nodeId(node.getNodeId()), Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
//    }
//
//    private KTable<String, CaSetIdEntityId> caSetIdEntityIdMap(KTable<String, Node> entityTable) {
//        return entityTable
//            .toStream()
//            .filterNot((id, entity) -> entity.getOp().equals("u"))
//            .map((entityId, entity) -> KeyValue.pair(entity.getCaSetId(), new CaSetIdEntityId(entity.getCaSetId(), entityId)))
//            .toTable(Materialized.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)));
//    }
}
