
package com.cross_ni.cross.cdc.model.source;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class NodeNodeType extends CdcDebeziumModel {

    private String nodeId;

    private String discriminator;
}
