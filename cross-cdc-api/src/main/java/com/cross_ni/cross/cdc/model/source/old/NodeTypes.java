package com.cross_ni.cross.cdc.model.source.old;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import static com.cross_ni.cross.cdc.model.source.old.CdcOperation.CREATE;
import static com.cross_ni.cross.cdc.model.source.old.CdcOperation.DELETE;
import static com.cross_ni.cross.cdc.model.source.old.CdcOperation.SNAPSHOT;
import static com.cross_ni.cross.cdc.model.source.old.CdcOperation.UPDATE;

@Getter
@ToString
@GeneratedSerde
public class NodeTypes {

    private Set<NodeType> nodeTypes = new HashSet<>();

    public NodeTypes aggregate(final NodeType nodeType) {
        if (nodeType.getOperation() == UPDATE || nodeType.getOperation() == CREATE || nodeType.getOperation() == SNAPSHOT) {
            nodeTypes.add(nodeType);
        } else if (nodeType.getOperation() == DELETE) {
            nodeTypes.remove(nodeType);
        }
        return this;
    }
}
