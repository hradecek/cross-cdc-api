package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;

public class JsonProduced {

    private JsonProduced() {
        throw new AssertionError("Cannot instantiate static utility class: " + JsonProduced.class.getName());
    }

    public static <T> Produced<String, T> of(String processorName, Class<T> producedType) {
        return Produced.with(Serdes.String(), JsonSerdes.serde(producedType)).withName(processorName);
    }
}
