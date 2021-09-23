package com.cross_ni.cross.cdc.model.source.old;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@GeneratedSerde
public class NodeType extends CdcModel {

    private String discriminator;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeType nodeType = (NodeType) o;
        return Objects.equals(discriminator, nodeType.discriminator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discriminator);
    }
}
