package com.cross_ni.cross.cdc.model.mapper;

import com.cross_ni.cross.cdc.model.aggregate.ExternalIds;
import com.cross_ni.cross.cdc.model.sink.Link;

import static com.cross_ni.cross.cdc.model.utils.SinkModelUtils.createEmptySinkNode;

public class SinkLinkExternalIdsMapper extends SinkExternalIdsMapper<Link> {
    @Override
    public Link apply(ExternalIds value) {
//        final com.cross_ni.cross.cdc.model.sink.Link sinkLink = createEmptySinkNode();
//        sinkNode.setExternalIds(mapExternalIds(sourceExternalIds.getExternalIds()));
//
        return null;
    }
}
