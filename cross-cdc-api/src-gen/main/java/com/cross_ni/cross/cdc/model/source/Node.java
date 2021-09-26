
package com.cross_ni.cross.cdc.model.source;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Node {

    @SerializedName("__op")
    @Expose
    private Object op;
    @SerializedName("node_id")
    @Expose
    private String nodeId;
    @SerializedName("capacity_free")
    @Expose
    private String capacityFree;
    @SerializedName("capacity_full")
    @Expose
    private String capacityFull;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("inherit_geometry")
    @Expose
    private String inheritGeometry;
    @SerializedName("node_name")
    @Expose
    private String nodeName;
    @SerializedName("ca_set_id")
    @Expose
    private String caSetId;
    @SerializedName("material_id")
    @Expose
    private String materialId;
    @SerializedName("node_geom_id")
    @Expose
    private String nodeGeomId;
    @SerializedName("node_status_id")
    @Expose
    private String nodeStatusId;

    public Object getOp() {
        return op;
    }

    public void setOp(Object op) {
        this.op = op;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getCapacityFree() {
        return capacityFree;
    }

    public void setCapacityFree(String capacityFree) {
        this.capacityFree = capacityFree;
    }

    public String getCapacityFull() {
        return capacityFull;
    }

    public void setCapacityFull(String capacityFull) {
        this.capacityFull = capacityFull;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInheritGeometry() {
        return inheritGeometry;
    }

    public void setInheritGeometry(String inheritGeometry) {
        this.inheritGeometry = inheritGeometry;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getCaSetId() {
        return caSetId;
    }

    public void setCaSetId(String caSetId) {
        this.caSetId = caSetId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getNodeGeomId() {
        return nodeGeomId;
    }

    public void setNodeGeomId(String nodeGeomId) {
        this.nodeGeomId = nodeGeomId;
    }

    public String getNodeStatusId() {
        return nodeStatusId;
    }

    public void setNodeStatusId(String nodeStatusId) {
        this.nodeStatusId = nodeStatusId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Node.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("op");
        sb.append('=');
        sb.append(((this.op == null)?"<null>":this.op));
        sb.append(',');
        sb.append("nodeId");
        sb.append('=');
        sb.append(((this.nodeId == null)?"<null>":this.nodeId));
        sb.append(',');
        sb.append("capacityFree");
        sb.append('=');
        sb.append(((this.capacityFree == null)?"<null>":this.capacityFree));
        sb.append(',');
        sb.append("capacityFull");
        sb.append('=');
        sb.append(((this.capacityFull == null)?"<null>":this.capacityFull));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("inheritGeometry");
        sb.append('=');
        sb.append(((this.inheritGeometry == null)?"<null>":this.inheritGeometry));
        sb.append(',');
        sb.append("nodeName");
        sb.append('=');
        sb.append(((this.nodeName == null)?"<null>":this.nodeName));
        sb.append(',');
        sb.append("caSetId");
        sb.append('=');
        sb.append(((this.caSetId == null)?"<null>":this.caSetId));
        sb.append(',');
        sb.append("materialId");
        sb.append('=');
        sb.append(((this.materialId == null)?"<null>":this.materialId));
        sb.append(',');
        sb.append("nodeGeomId");
        sb.append('=');
        sb.append(((this.nodeGeomId == null)?"<null>":this.nodeGeomId));
        sb.append(',');
        sb.append("nodeStatusId");
        sb.append('=');
        sb.append(((this.nodeStatusId == null)?"<null>":this.nodeStatusId));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.capacityFull == null)? 0 :this.capacityFull.hashCode()));
        result = ((result* 31)+((this.nodeName == null)? 0 :this.nodeName.hashCode()));
        result = ((result* 31)+((this.op == null)? 0 :this.op.hashCode()));
        result = ((result* 31)+((this.nodeGeomId == null)? 0 :this.nodeGeomId.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.inheritGeometry == null)? 0 :this.inheritGeometry.hashCode()));
        result = ((result* 31)+((this.materialId == null)? 0 :this.materialId.hashCode()));
        result = ((result* 31)+((this.nodeId == null)? 0 :this.nodeId.hashCode()));
        result = ((result* 31)+((this.nodeStatusId == null)? 0 :this.nodeStatusId.hashCode()));
        result = ((result* 31)+((this.capacityFree == null)? 0 :this.capacityFree.hashCode()));
        result = ((result* 31)+((this.caSetId == null)? 0 :this.caSetId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Node) == false) {
            return false;
        }
        Node rhs = ((Node) other);
        return ((((((((((((this.capacityFull == rhs.capacityFull)||((this.capacityFull!= null)&&this.capacityFull.equals(rhs.capacityFull)))&&((this.nodeName == rhs.nodeName)||((this.nodeName!= null)&&this.nodeName.equals(rhs.nodeName))))&&((this.op == rhs.op)||((this.op!= null)&&this.op.equals(rhs.op))))&&((this.nodeGeomId == rhs.nodeGeomId)||((this.nodeGeomId!= null)&&this.nodeGeomId.equals(rhs.nodeGeomId))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.inheritGeometry == rhs.inheritGeometry)||((this.inheritGeometry!= null)&&this.inheritGeometry.equals(rhs.inheritGeometry))))&&((this.materialId == rhs.materialId)||((this.materialId!= null)&&this.materialId.equals(rhs.materialId))))&&((this.nodeId == rhs.nodeId)||((this.nodeId!= null)&&this.nodeId.equals(rhs.nodeId))))&&((this.nodeStatusId == rhs.nodeStatusId)||((this.nodeStatusId!= null)&&this.nodeStatusId.equals(rhs.nodeStatusId))))&&((this.capacityFree == rhs.capacityFree)||((this.capacityFree!= null)&&this.capacityFree.equals(rhs.capacityFree))))&&((this.caSetId == rhs.caSetId)||((this.caSetId!= null)&&this.caSetId.equals(rhs.caSetId))));
    }

}
