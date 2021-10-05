package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;

public class JsonConsumed {

    private JsonConsumed() {
        throw new AssertionError("Cannot instantiate static utility class: " + JsonConsumed.class.getName());
    }

    public static <T> Consumed<String, T> of(String processorName, Class<T> consumedType) {
        return Consumed.with(Serdes.String(), JsonSerdes.serde(consumedType)).withName(processorName);
    }
}
