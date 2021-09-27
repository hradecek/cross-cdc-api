package com.cross_ni.cross.cdc.serialization;

import com.cross_ni.cross.cdc.model.sink.NodeType;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.kstream.ValueJoiner;

import java.util.HashMap;
import java.util.Map;

public class NodeCdcSinkValueJoinerFacade {

    private static final Map<TypePair<?, ?>, ValueJoiner<?, ?, ?>> VALUE_JOINERS = new HashMap<>();
    static {
        VALUE_JOINERS.put(TypePair.of(com.cross_ni.cross.cdc.model.source.NodeNodeType.class, com.cross_ni.cross.cdc.model.source.NodeType.class), new NodeTypeValueJoiner());
    }

    public static <V1, V2, VR> ValueJoiner<V1, V2, VR> apply(Class<V1> v1, Class<V2> v2) {
        return (ValueJoiner<V1, V2, VR>) VALUE_JOINERS.get(TypePair.of(v1, v2));
    }

    private static class NodeTypeValueJoiner implements ValueJoiner<com.cross_ni.cross.cdc.model.source.NodeNodeType, com.cross_ni.cross.cdc.model.source.NodeType, NodeType> {
        @Override
        public NodeType apply(com.cross_ni.cross.cdc.model.source.NodeNodeType sourceNodeNodeType, com.cross_ni.cross.cdc.model.source.NodeType sourceNodeType) {
            final NodeType sinkNodeType = new NodeType();
            sinkNodeType.setOp(sourceNodeNodeType.getOp());
            sinkNodeType.setNodeId(sourceNodeNodeType.getNodeId());
            sinkNodeType.setDiscriminator(sourceNodeType.getDiscriminator());
            sinkNodeType.setMultiparent(sourceNodeType.getMultiparent());
            sinkNodeType.setName(sourceNodeType.getName());
            sinkNodeType.setRootType(sourceNodeType.getRootType());

            return sinkNodeType;
        }
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor(staticName = "of")
    private static class TypePair<V1, V2> {

        private final Class<V1> v1Class;
        private final Class<V2> v2Class;
    }
}
