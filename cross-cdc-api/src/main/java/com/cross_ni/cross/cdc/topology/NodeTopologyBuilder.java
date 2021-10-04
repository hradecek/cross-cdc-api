package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.*;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;

// TODO: Use String as default key Serde
class NodeTopologyBuilder implements CdcTopologyBuilder {

    private static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_EXTERNAL_ID = "crossdb.public.external_id";
    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private final StreamsBuilder builder;
//    private final CustomAttributesTopologyBuilder<Node> customAttributesTopologyBuilder;

    public static NodeTopologyBuilder create(final StreamsBuilder builder) {
        return new NodeTopologyBuilder(builder);
    }

    private NodeTopologyBuilder(final StreamsBuilder builder) {
        this.builder = builder;
//        this.customAttributesTopologyBuilder = new CustomAttributesTopologyBuilder<>(builder);
    }

    @Override
    public void build() {
        final KTable<String, Node> nodes = nodes();
//        final KTable<String, NodeTypes> nodeTypes = nodeNodeTypes();
//        final KTable<String, ExternalIds> externalIds = externalIds();

//        nodes.toStream().to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(Node.class)));
//        nodeTypes.toStream().to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)));
//        externalIds.toStream().to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
        customAttributes(nodes).toStream().to(TOPIC_NAME_SINK_NODE, Produced.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private KTable<String, Node> nodes() {
        return builder.table(TOPIC_NAME_SOURCE_NODE, Consumed.with(Serdes.String(), JsonSerdes.serde(Node.class)));
    }

    private KTable<String, NodeTypes> nodeNodeTypes() {
        return
            builder
            .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)))
            .groupByKey()
            .aggregate(NodeTypes::new, NodeTypes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)));
    }

    private static final String EXTERNAL_ID_NODE_ENTITY = "com.cross_ni.cross.db.pojo.core.Node";

    private KTable<String, ExternalIds> externalIds() {
        return builder
            .stream(TOPIC_NAME_SOURCE_EXTERNAL_ID, Consumed.with(Serdes.String(), JsonSerdes.serde(ExternalId.class)))
            .filter((k, v) -> v.getEntity().equals(EXTERNAL_ID_NODE_ENTITY))
            .groupByKey()
            .aggregate(ExternalIds::new, ExternalIds::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(ExternalIds.class)));
    }

    public KTable<String, CustomAttributes> customAttributes(KTable<String, Node> entityTable) {
        final KStream<String, CustomAttribute> caValues = caValues();

        return repartitionByNodeId(aggregate(joinCaDefinitions(caValues)), entityTable);
    }

    private static final String TOPIC_NAME_SOURCE_CA_DEF = "crossdb.public.ca_def";
    private static final String TOPIC_NAME_SOURCE_CA_VAL = "crossdb.public.ca_val";
    private KStream<String, CustomAttribute> caValues() {
        return builder.stream(TOPIC_NAME_SOURCE_CA_VAL, Consumed.with(Serdes.String(), JsonSerdes.serde(CustomAttribute.class)));
    }

    private KStream<String, CustomAttribute> joinCaDefinitions(KStream<String, CustomAttribute> caValues) {
        final GlobalKTable<String, CaDefinition> caDefinitions = caDefinitions();

        return caValues.join(caDefinitions, (caSetId, customAttribute) -> customAttribute.getCaDefId(), CustomAttribute::join);
    }

    private KTable<String, CustomAttributes> aggregate(KStream<String, CustomAttribute> caValues) {
        return caValues
            .groupByKey()
            .aggregate(CustomAttributes::new, CustomAttributes::aggregator, Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private KTable<String, CustomAttributes> repartitionByNodeId(KTable<String, CustomAttributes> caValues, KTable<String, Node> entityTable) {
        final KTable<String, CaSetIdEntityId> caSetIdEntityIdMap = caSetIdEntityIdMap(entityTable);

        return caValues.join(caSetIdEntityIdMap, (customAttributes, node) -> customAttributes.nodeId(node.getNodeId()), Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private GlobalKTable<String, CaDefinition> caDefinitions() {
        return builder.globalTable(TOPIC_NAME_SOURCE_CA_DEF, Consumed.with(Serdes.String(), JsonSerdes.serde(CaDefinition.class)));
    }

    private KTable<String, CaSetIdEntityId> caSetIdEntityIdMap(KTable<String, Node> entityTable) {
        return entityTable
            .toStream()
            .filterNot((id, entity) -> entity.getOp().equals("u"))
            .map((entityId, entity) -> KeyValue.pair(entity.getCaSetId(), new CaSetIdEntityId(entity.getCaSetId(), entityId)))
            .toTable(Materialized.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)));
    }
}
