package com.cross_ni.cross.cdc.model.mapper;

import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;

import static com.cross_ni.cross.cdc.model.utils.SinkModelUtils.createEmptySinkNode;

public class SinkNodeExternalIdsMapper
    extends SinkExternalIdsMapper<com.cross_ni.cross.cdc.model.sink.Node> {

    @Override
    public com.cross_ni.cross.cdc.model.sink.Node apply(ExternalIds sourceExternalIds) {
        final com.cross_ni.cross.cdc.model.sink.Node sinkNode = createEmptySinkNode();
        sinkNode.setExternalIds(mapExternalIds(sourceExternalIds.getExternalIds()));

        return sinkNode;
    }
}
