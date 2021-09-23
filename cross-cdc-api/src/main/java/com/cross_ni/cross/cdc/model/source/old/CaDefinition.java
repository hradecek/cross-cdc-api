package com.cross_ni.cross.cdc.model.source.old;

import com.cross_ni.cross.cdc.serialization.GeneratedSerde;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@GeneratedSerde
public class CaDefinition extends CdcModel {

    private String attributeName;

    // DB column is mapped to "class", which is JAVA keyword and must be remapped via annotation
    @SerializedName(value = "class")
    private String attributeClass;
}
