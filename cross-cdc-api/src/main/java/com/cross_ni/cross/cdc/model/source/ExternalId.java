
package com.cross_ni.cross.cdc.model.source;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ExternalId extends CdcDebeziumModel {

    private String entity;
    private String externalId;
    private String systemId;
    private String internalId;
}
