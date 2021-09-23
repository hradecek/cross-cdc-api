package com.cross_ni.cross.cdc.model.source.old;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@GeneratedSerde
public class CustomAttributes {

    private Set<CustomAttribute> customAttributes = new HashSet<>();

    public CustomAttributes aggregate(CustomAttribute customAttribute) {
        customAttributes.add(customAttribute);
        return this;
    }
}
