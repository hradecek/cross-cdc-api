package com.cross_ni.cross.cdc.serialization.json;

import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.aggregate.NodeSnapshot;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.model.source.CustomAttribute;
import com.cross_ni.cross.cdc.model.source.ExternalId;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.model.source.NodeType;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonSerdes {

    private static final Set<Class<?>> SOURCE_MODELS = new HashSet<>();
    private static final Set<Class<?>> SINK_MODELS = new HashSet<>();
    private static final Map<Class<?>, Serde<?>> SERDES = new HashMap<>();

    static {
        SOURCE_MODELS.add(Node.class);
        SOURCE_MODELS.add(NodeType.class);
        SOURCE_MODELS.add(NodeNodeType.class);
        SOURCE_MODELS.add(ExternalId.class);
        SOURCE_MODELS.add(CustomAttribute.class);

        SOURCE_MODELS.add(NodeTypes.class);
        SOURCE_MODELS.add(NodeSnapshot.class);
        SOURCE_MODELS.add(ExternalIds.class);
        SOURCE_MODELS.add(CustomAttributes.class);

        SINK_MODELS.add(com.cross_ni.cross.cdc.model.sink.Node.class);

        for (Class<?> modelClass : findAllSourceModels()) {
            SERDES.put(modelClass, createSerde(modelClass));
        }
        for (Class<?> modelClass : findAllSinkModels()) {
            SERDES.put(modelClass, createSerde(modelClass));
        }
    }

    private JsonSerdes() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    @SuppressWarnings("unchecked")
    public static <T> Serde<T> serde(Class<T> modelClass) {
        return (Serde<T>) SERDES.get(modelClass);
    }

    private static Set<Class<?>> findAllSourceModels() {
        return SOURCE_MODELS;
    }

    private static Set<Class<?>> findAllSinkModels() {
        return SINK_MODELS;
    }

    private static <T> Serde<T> createSerde(Class<T> clazz) {
        final JsonSerializer<T> serializer = new JsonSerializer<>();
        final JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
