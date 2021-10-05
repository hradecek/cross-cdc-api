package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.mapper.SinkNodeTypeMapper;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;
import com.cross_ni.cross.cdc.utils.JsonConsumed;
import com.cross_ni.cross.cdc.utils.JsonMaterialized;
import com.cross_ni.cross.cdc.utils.JsonProduced;
import lombok.Getter;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Repartitioned;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import static com.cross_ni.cross.cdc.topology.CdcTopology.TOPIC_NAME_SINK_NODE;

class NodeTopologyBuilder implements CdcTopologyBuilder {

    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private static final String STORE_NAME_SOURCE_NODES = "source-nodes-store";

    // TODO: Use state store instead
    @Getter
    private KTable<String, CaSetIdEntityId> caSetMap;

    @Override
    public void build(final StreamsBuilder builder) {
        final KTable<String, Node> nodes = nodes(builder);
        sinkNodeChanges(nodes);
        sinkNodeTypeChanges(builder);

        caSetMap = caSetIdMap(nodes);
    }

    private KTable<String, Node> nodes(final StreamsBuilder builder) {
        builder.addStateStore(sourceNodeStore());
        return builder.table(TOPIC_NAME_SOURCE_NODE, JsonConsumed.of("source-node", Node.class));
    }

    private static StoreBuilder<KeyValueStore<String, Node>> sourceNodeStore() {
        return Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore(STORE_NAME_SOURCE_NODES), Serdes.String(), JsonSerdes.serde(Node.class));
    }

    private void sinkNodeChanges(KTable<String, Node> nodes) {
        nodes.transformValues(SinkNodeValueTransformer::new, Named.as("transform-to-sink-node"), STORE_NAME_SOURCE_NODES)
            .toStream(Named.as("stream-nodes"))
            .filter((nodeId, sinkNode) -> operationNotNull(sinkNode), Named.as("filter-sink-node-changes"))
            .to(TOPIC_NAME_SINK_NODE, producedSinkNode("sink-nodes"));
    }

    private static boolean operationNotNull(com.cross_ni.cross.cdc.model.sink.Node sinkNode) {
        return sinkNode.getOperation() != null;
    }

    private KTable<String, NodeTypes> nodeNodeTypes(final StreamsBuilder builder){
        final Named processorName = Named.as("aggregate-node-types");

        return builder
            .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, JsonConsumed.of("source-node-type", NodeNodeType.class))
            .groupByKey()
            .aggregate(NodeTypes::new, NodeTypes::aggregator, processorName, JsonMaterialized.of("aggregate-node-types-store", NodeTypes.class));
    }

    private void sinkNodeTypeChanges(final StreamsBuilder builder) {
        nodeNodeTypes(builder)
            .toStream(Named.as("stream-node-types"))
            .mapValues(new SinkNodeTypeMapper(), Named.as("map-node-types-to-sink-node"))
            .to(TOPIC_NAME_SINK_NODE, producedSinkNode("sink-node-types"));
    }

    private static Produced<String, com.cross_ni.cross.cdc.model.sink.Node> producedSinkNode(String processorName) {
        return JsonProduced.of(processorName, com.cross_ni.cross.cdc.model.sink.Node.class);
    }

    private KTable<String, CaSetIdEntityId> caSetIdMap(KTable<String, Node> nodes) {
        return nodes
            .toStream()
            .selectKey((nodeId, node) -> node.getCaSetId())
            .mapValues(node -> new CaSetIdEntityId(node.getCaSetId(), node.getNodeId()))
            .repartition(Repartitioned.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)))
            .toTable();
    }
}
