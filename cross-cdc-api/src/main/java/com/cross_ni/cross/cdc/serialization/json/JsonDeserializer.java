package com.cross_ni.cross.cdc.serialization.json;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

// TODO: IntelliJ has a problem when class is not public. Why?
public class JsonDeserializer<T> implements Deserializer<T> {

    private final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private Class<T> destinationClass;
    private Type reflectionTypeToken;

    /**
     * Default constructor needed by Kafka.
     *
     * @param destinationClass class which data will be deserialized to
     */
    public JsonDeserializer(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    /**
     * Constructor.
     *
     * @param reflectionTypeToken type of deserialized class
     */
    public JsonDeserializer(Type reflectionTypeToken) {
        this.reflectionTypeToken = reflectionTypeToken;
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {}

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        final Type type = destinationClass != null ? destinationClass : reflectionTypeToken;

        return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), type);
    }

    @Override
    public void close() { }
}
