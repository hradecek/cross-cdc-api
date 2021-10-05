package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.SinkNodeDifferenceMapper;
import com.cross_ni.cross.cdc.SinkNodeMapper;
import com.cross_ni.cross.cdc.model.CaSetIdEntityId;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;
import lombok.Getter;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

// TODO: Use String as default key Serde
class NodeTopologyBuilder implements CdcTopologyBuilder {

    public static final String TOPIC_NAME_SINK_NODE = "cross.cdc.node";

    private static final String TOPIC_NAME_SOURCE_NODE = "crossdb.public.node";
    private static final String TOPIC_NAME_SOURCE_NODE_NODE_TYPE = "crossdb.public.node_node_type";

    private static final Consumed<String, Node> CONSUMED_NODE_SOURCE =
        Consumed.with(Serdes.String(), JsonSerdes.serde(Node.class)).withName("source-node");
    private static final Consumed<String, NodeNodeType> CONSUMED_NODE_NODE_TYPE_SOURCE =
        Consumed.with(Serdes.String(), JsonSerdes.serde(NodeNodeType.class)).withName("source-node-type");

    private static final Materialized<String, NodeTypes, KeyValueStore<Bytes, byte[]>> MATERIALIZED_NODE_TYPES_AGGREGATE =
        Materialized.<String, NodeTypes, KeyValueStore<Bytes, byte[]>>as("aggregate-node-types-store")
            .withKeySerde(Serdes.String())
            .withValueSerde(JsonSerdes.serde(NodeTypes.class));

    private static final Produced<String, com.cross_ni.cross.cdc.model.sink.Node> PRODUCED_NODE_SINK =
        Produced.with(Serdes.String(), JsonSerdes.serde(com.cross_ni.cross.cdc.model.sink.Node.class)).withName("sink-nodes");
    private static final Produced<String, NodeTypes> PRODUCED_NODE_TYPES_SINK =
        Produced.with(Serdes.String(), JsonSerdes.serde(NodeTypes.class)).withName("sink-node-types");

    private final StreamsBuilder builder;

    public NodeTopologyBuilder(StreamsBuilder builder) {
        this.builder = builder;
    }

    @Getter
    private KTable<String, CaSetIdEntityId> caSetMap;

    @Override
    public void build() {
        final StoreBuilder<KeyValueStore<String, Node>> sourceNodesStateStore =
            Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("source-nodes-state-store"), Serdes.String(), JsonSerdes.serde(Node.class));
        builder.addStateStore(sourceNodesStateStore);

        final KTable<String, Node> nodes = nodes();
        nodes.transformValues(() ->
                new ValueTransformerWithKey<String, Node, com.cross_ni.cross.cdc.model.sink.Node>() {

                    private KeyValueStore<String, Node> state;
                    private SinkNodeDifferenceMapper differenceMapper;
                    private SinkNodeMapper mapper = new SinkNodeMapper();

                    @Override
                    public void init(final ProcessorContext context) {
                        differenceMapper = new SinkNodeDifferenceMapper();
                        mapper = new SinkNodeMapper();
                        state = context.getStateStore("source-nodes-state-store");
                    }

                    @Override
                    public com.cross_ni.cross.cdc.model.sink.Node transform(final String key, final Node value) {
                        final Node prevValue = state.get(key);
                        if (value.getOp().equals("d")) {
                            state.delete(key);
                        } else {
                            state.put(key, value);
                        }

                        if (prevValue != null) {
                            return differenceMapper.apply(prevValue, value);
                        }
                        return mapper.apply(value);
                    }

                    @Override
                    public void close() {}
                }
            , Named.as("transform-to-sink-node"), new String[]{"source-nodes-state-store"})
            .filter((nodeId, node) -> {
                System.out.println("Filter " + node);
                return node != null;
            })
            .toStream(Named.as("stream-nodes")).to(TOPIC_NAME_SINK_NODE, PRODUCED_NODE_SINK);
//        nodeNodeTypes().toStream(Named.as("stream-node-types")).to(TOPIC_NAME_SINK_NODE, PRODUCED_NODE_TYPES_SINK);

        caSetMap = caSetIdMap(nodes);
    }

    private KTable<String, Node> nodes() {
        return builder.table(TOPIC_NAME_SOURCE_NODE, CONSUMED_NODE_SOURCE);
    }

    private KTable<String, CaSetIdEntityId> caSetIdMap(KTable<String, Node> nodes) {
        return nodes
            .toStream()
            .selectKey((nodeId, node) -> node.getCaSetId())
            .mapValues(node -> new CaSetIdEntityId(node.getCaSetId(), node.getNodeId()))
            .repartition(Repartitioned.with(Serdes.String(), JsonSerdes.serde(CaSetIdEntityId.class)))
            .toTable();
    }

    private KTable<String, NodeTypes> nodeNodeTypes() {
        return builder
            .stream(TOPIC_NAME_SOURCE_NODE_NODE_TYPE, CONSUMED_NODE_NODE_TYPE_SOURCE)
            .groupByKey()
            .aggregate(NodeTypes::new, NodeTypes::aggregator, Named.as("aggregate-node-types"), MATERIALIZED_NODE_TYPES_AGGREGATE);
    }
}
