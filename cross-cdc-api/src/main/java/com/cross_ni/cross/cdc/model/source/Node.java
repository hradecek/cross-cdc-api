
package com.cross_ni.cross.cdc.model.source;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Node extends CdcDebeziumModel implements CdcEntityWithCaSetModel<String, String> {

    private String nodeId;
    private String capacityFree;
    private String capacityFull;
    private String description;
    private String inheritGeometry;
    private String nodeName;
    private String caSetId;
    private String materialId;
    private String nodeGeomId;
    private String nodeStatusId;

    @Override
    public String getPrimaryId() {
        return nodeId;
    }
}
