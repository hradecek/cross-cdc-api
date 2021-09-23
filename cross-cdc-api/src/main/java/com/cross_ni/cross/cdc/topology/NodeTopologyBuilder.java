package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.source.CaDefinition;
import com.cross_ni.cross.cdc.model.source.CustomAttribute;
import com.cross_ni.cross.cdc.model.source.CustomAttributes;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.model.source.NodeType;
import com.cross_ni.cross.cdc.model.source.NodeTypes;
import com.cross_ni.cross.cdc.serialization.NodeSinkValueMapper;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeTopologyBuilder.class);

    // TODO: Externalize
    private static final String TOPIC_NAME_CDC_NODES = "cross.cdc.node";
    private static final String TOPIC_NAME_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_NODE_TYPE = "crossdb.public.node_type";
    private static final String TOPIC_NAME_NODE_NODE_TYPE = "crossdb.public.node_node_type";
    private static final String TOPIC_NAME_CA_DEF = "crossdb.public.ca_def";
    private static final String TOPIC_NAME_CA_VALS = "crossdb.public.ca_vals";

    private final StreamsBuilder builder = new StreamsBuilder();
    private final NodeSinkValueMapper nodeSinkValueMapper = new NodeSinkValueMapper();

    public Topology build() {
        final GlobalKTable<String, CaDefinition> customAttributeDefinitions = customAttributeDefinitions();
        final KTable<String, CustomAttributes> customAttributes = customAttributes(customAttributeDefinitions);

        final GlobalKTable<String, NodeType> nodeTypes = nodeTypes();
        final KTable<String, NodeTypes> nodeNodeTypes = nodeNodeTypes(nodeTypes);

        final KTable<String, Node> nodes = nodes(nodeNodeTypes, customAttributes);

        nodes.mapValues(nodeSinkValueMapper).toStream().to(TOPIC_NAME_CDC_NODES, Produced.with(Serdes.String(), JsonSerdes.SinkNode()));

        return builder.build();
    }

    private GlobalKTable<String, CaDefinition> customAttributeDefinitions() {
        return builder.globalTable(TOPIC_NAME_CA_DEF, Consumed.with(Serdes.String(), JsonSerdes.serde(CaDefinition.class)));
    }

    private KTable<String, CustomAttributes> customAttributes(GlobalKTable<String, CaDefinition> customAttributeDefinitions) {
        return builder
            .stream(TOPIC_NAME_CA_VALS, Consumed.with(Serdes.String(), JsonSerdes.serde(CustomAttribute.class)))
            .join(customAttributeDefinitions, (k, v) -> v.getCaDefId(), CustomAttribute::aggregate)
            .groupByKey()
            .aggregate(
                CustomAttributes::new,
                NodeTopologyBuilder::customAttributesAggregator,
                Materialized.with(Serdes.String(), JsonSerdes.serde(CustomAttributes.class)));
    }

    private static CustomAttributes customAttributesAggregator(String key, CustomAttribute value, CustomAttributes aggregate) {
        LOGGER.debug("Custom attributes aggregation: {}", key);
        return aggregate.aggregate(value);
    }

    private GlobalKTable<String, NodeType> nodeTypes() {
        return builder.globalTable(TOPIC_NAME_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.serde(NodeType.class)));
    }

    // TODO: Do selectKey directly in SMT in order to avoid repartitioning
    private KTable<String, NodeTypes> nodeNodeTypes(GlobalKTable<String, NodeType> nodeTypes) {
        return builder
            .stream(TOPIC_NAME_NODE_NODE_TYPE, Consumed.with(JsonSerdes.serde(NodeNodeType.class), JsonSerdes.serde(NodeNodeType.class)))
            .selectKey((key, value) -> String.valueOf(key.getNodeId()))
            .join(nodeTypes, (key, value) -> value.getDiscriminator(), (left, right) -> {
                right.setOperation(left.getOperation());
                return right;
            })
            .groupByKey(Grouped.with(Serdes.String(), JsonSerdes.serde(NodeType.class)))
            .aggregate(
                NodeTypes::new,
                NodeTopologyBuilder::nodeTypesAggregator,
                Materialized.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)));
    }

    private static NodeTypes nodeTypesAggregator(String key, NodeType value, NodeTypes aggregate) {
        LOGGER.debug("Node types aggregation: {}", key);
        return aggregate.aggregate(value);
    }

    private KTable<String, Node> nodes(KTable<String, NodeTypes> nodeTypes, KTable<String, CustomAttributes> customAttributes) {
        final KTable<String, Node> nodes = builder.table(TOPIC_NAME_NODE, Consumed.with(Serdes.String(), JsonSerdes.serde(Node.class)));
        return joinCustomAttributes(joinNodeTypes(nodes, nodeTypes), customAttributes);
    }

    private KTable<String, Node> joinNodeTypes(KTable<String, Node> nodes, KTable<String, NodeTypes> nodeTypes) {
        return nodeTypes.join(nodes, (left, right) -> right.aggregate(left), Materialized.with(Serdes.String(), JsonSerdes.serde(Node.class)));
    }

    private KTable<String, Node> joinCustomAttributes(KTable<String, Node> nodes, KTable<String, CustomAttributes> customAttributes) {
        return repartitionByCaDefId(nodes)
            .outerJoin(
                customAttributes,
                NodeTopologyBuilder::nodeCustomAttributeJoiner,
                Materialized.with(Serdes.String(), JsonSerdes.serde(Node.class)));
    }

    private KTable<String, Node> repartitionByCaDefId(KTable<String, Node> nodes) {
        return nodes
            .toStream()
            .selectKey((k, v) -> String.valueOf(v.getCaSetId()))
            .toTable(Materialized.with(Serdes.String(), JsonSerdes.serde(Node.class)));
    }

    // Node null check: custom attributes might be attached another object type, not node, hence outer join will be null
    // CustomAttributes null check: it is not required for node to have custom attributes
    private static Node nodeCustomAttributeJoiner(Node node, CustomAttributes customAttributes) {
        if (node != null && customAttributes != null) {
            return node.aggregate(customAttributes);
        } else {
            return null;
        }
    }
}
