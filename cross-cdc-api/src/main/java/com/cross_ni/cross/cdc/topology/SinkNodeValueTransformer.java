package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.mapper.SinkNodeDifferenceMapper;
import com.cross_ni.cross.cdc.model.mapper.SinkNodeMapper;
import com.cross_ni.cross.cdc.model.source.Node;

import org.apache.kafka.streams.kstream.ValueTransformerWithKey;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

import static com.cross_ni.cross.cdc.topology.NodeTopologyBuilder.STORE_NAME_SOURCE_NODES;

class SinkNodeValueTransformer implements ValueTransformerWithKey<String, Node, com.cross_ni.cross.cdc.model.sink.Node> {

    private SinkNodeMapper mapper;
    private SinkNodeDifferenceMapper differenceMapper;

    private KeyValueStore<String, Node> store;

    @Override
    public void init(final ProcessorContext context) {
        differenceMapper = new SinkNodeDifferenceMapper();
        mapper = new SinkNodeMapper();
        store = context.getStateStore(STORE_NAME_SOURCE_NODES);
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
