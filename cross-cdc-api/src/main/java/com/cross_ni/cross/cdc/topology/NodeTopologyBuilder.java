package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.SinkNodeDifferenceMapper;
import com.cross_ni.cross.cdc.SinkNodeMapper;
import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Repartitioned;
import org.apache.kafka.streams.kstream.ValueTransformerWithKey;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import lombok.Getter;

import java.util.Optional;

// TODO: Use String as default key Serde
class NodeTopologyBuilder implements CdcTopologyBuilder {

    public static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    @Getter
    private KTable<String, CaSetIdEntityId> caSetMap;

    @Override
    public void build(final StreamsBuilder builder) {
        final KTable<String, Node> nodes = nodes(builder);
        sinkNodeChanges(nodes);
        sinkNodeTypeChanges(builder);
        caSetMap = caSetIdMap(nodes);
    }

    private void sinkNodeChanges(KTable<String, Node> nodes) {
        nodes.transformValues(SinkNodeValueTransformer::new, Named.as("transform-to-sink-node"), "source-nodes-state-store")
            .toStream(Named.as("stream-nodes"))
            .filter((nodeId, node) -> node.getOperation() != null)
            .to(TOPIC_NAME_SINK_NODE, producedSinkNode("sink-nodes"));
    }

    private void sinkNodeTypeChanges(final StreamsBuilder builder) {
//        nodeNodeTypes(builder).toStream(Named.as("stream-node-types")).to(TOPIC_NAME_SINK_NODE, producedSinkNode("sink-node-types"));
    }

    private static Produced<String, com.cross_ni.cross.cdc.model.sink.Node> producedSinkNode(String processorName) {
        return JsonProduced.of(processorName, com.cross_ni.cross.cdc.model.sink.Node.class);
    }

    private static class SinkNodeValueTransformer implements ValueTransformerWithKey<String, Node, com.cross_ni.cross.cdc.model.sink.Node> {

        private static final String STORE_NAME_SOURCE_NODE = "source-nodes-state-store";

        private SinkNodeMapper mapper;
        private SinkNodeDifferenceMapper differenceMapper;

        private KeyValueStore<String, Node> store;

        @Override
        public void init(final ProcessorContext context) {
            differenceMapper = new SinkNodeDifferenceMapper();
            mapper = new SinkNodeMapper();
            store = context.getStateStore(STORE_NAME_SOURCE_NODE);
        }

        @Override
        public com.cross_ni.cross.cdc.model.sink.Node transform(final String key, final Node nodeNewChange) {
            return updateStore(key, nodeNewChange)
                .map(nodeBeforeChange -> differenceMapper.apply(nodeBeforeChange, nodeNewChange))
                .orElse(mapper.apply(nodeNewChange));
        }

        private Optional<Node> updateStore(final String key, final Node value) {
            final Node nodeBeforeChange = store.get(key);
            if (value.getOp().equals("d")) {
                store.delete(key);
            } else {
                store.put(key, value);
            }
            return Optional.ofNullable(nodeBeforeChange);
        }

        @Override
        public void close() { }
    }

    private KTable<String, Node> nodes(final StreamsBuilder builder) {
        builder.addStateStore(sourceNodeStore());
        return builder.table(TOPIC_NAME_SOURCE_NODE, JsonConsumed.of("source-node", Node.class));
    }

    private StoreBuilder<KeyValueStore<String, Node>> sourceNodeStore() {
        return Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("source-nodes-state-store"), Serdes.String(), JsonSerdes.serde(Node.class));
    }

    private KTable<String, CaSetIdEntityId> caSetIdMap(KTable<String, Node> nodes) {
        return nodes
            .toStream()
            .selectKey((nodeId, node) -> node.getCaSetId())
            .mapValues(node -> new CaSetIdEntityId(node.getCaSetId(), node.getNodeId()))
            .repartition(Repartitioned.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)))
            .toTable();
    }

    private KTable<String, NodeTypes> nodeNodeTypes(final StreamsBuilder builder){
        final Named processorName = Named.as("aggregate-node-types");

        return builder
            .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, JsonConsumed.of("source-node-type", NodeNodeType.class))
            .groupByKey()
            .aggregate(NodeTypes::new, NodeTypes::aggregator, processorName, JsonMaterialized.of("aggregate-node-types-store", NodeTypes.class));
    }
}
