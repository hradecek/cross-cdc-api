package com.cross_ni.cross.cdc.model.source;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CaDefinition {

    private final String attributeName;

    // DB column is mapped to "class", which is a JAVA keyword and must be remapped
    @SerializedName(value = "class")
    private final String attributeClass;
}
