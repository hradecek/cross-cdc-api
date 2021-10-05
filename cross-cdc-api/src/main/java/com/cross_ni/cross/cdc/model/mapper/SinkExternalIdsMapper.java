package com.cross_ni.cross.cdc.model.mapper;

import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.sink.ExternalId;

import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SinkExternalIdsMapper<T> implements ValueMapper<ExternalIds, T> {

    protected List<ExternalId> mapExternalIds(Set<com.cross_ni.cross.cdc.model.source.ExternalId> externalIds) {
        return externalIds.stream().map(this::mapExternalId).collect(Collectors.toList());
    }

    protected com.cross_ni.cross.cdc.model.sink.ExternalId mapExternalId(com.cross_ni.cross.cdc.model.source.ExternalId externalId) {
        final com.cross_ni.cross.cdc.model.sink.ExternalId sinkExternalId = new com.cross_ni.cross.cdc.model.sink.ExternalId();
        sinkExternalId.setExternalId(externalId.getExternalId());
        sinkExternalId.setSystemId(externalId.getSystemId());

        return sinkExternalId;
    }
}
