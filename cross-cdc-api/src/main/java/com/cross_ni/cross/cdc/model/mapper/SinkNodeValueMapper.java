package com.cross_ni.cross.cdc.model.mapper;

import com.cross_ni.cross.cdc.model.sink.Node;

import org.apache.kafka.streams.kstream.ValueMapper;

public interface SinkNodeValueMapper<T> extends ValueMapper<T, Node> {
}
