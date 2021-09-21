package com.cross_ni.cross.cdc.serialization.json;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonSerdes {

    private static final String JAVA_PACKAGE_SOURCE_MODEL = "com.cross_ni.cross.cdc.model.source";
    private static final Map<Class<?>, Serde<?>> SERDES = new HashMap<>();

    static {
        for (Class<?> modelClass : findAllModelsWithGeneratedSerde()) {
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

    private static Set<Class<?>> findAllModelsWithGeneratedSerde() {
        final Reflections reflections = new Reflections(JAVA_PACKAGE_SOURCE_MODEL);
        return reflections.getTypesAnnotatedWith(GeneratedSerde.class);
    }

    private static <T> Serde<T> createSerde(Class<T> clazz) {
        final JsonSerializer<T> serializer = new JsonSerializer<>();
        final JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
