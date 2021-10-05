package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.aggregate.CustomAttributes;
import com.cross_ni.cross.cdc.model.aggregate.NodeTypes;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;

public class JsonMaterialized {

    private JsonMaterialized() {
        throw new AssertionError("Cannot instantiate static utility class: " + JsonMaterialized.class.getName());
    }

    public static <T> Materialized<String, T, KeyValueStore<Bytes, byte[]>> of(String storeName, Class<T> materializedType) {
        return Materialized
            .<String, T, KeyValueStore<Bytes, byte[]>>as(storeName)
            .withKeySerde(Serdes.String())
            .withValueSerde(JsonSerdes.serde(materializedType));
    }
}
