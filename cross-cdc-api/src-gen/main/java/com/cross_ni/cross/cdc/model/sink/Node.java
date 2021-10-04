
package com.cross_ni.cross.cdc.model.sink;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Node {

    @SerializedName("operation")
    @Expose
    private String operation;
    @SerializedName("node_id")
    @Expose
    private String nodeId;
    @SerializedName("capacity_full")
    @Expose
    private Double capacityFull;
    @SerializedName("capacity_free")
    @Expose
    private Double capacityFree;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("inherit_geometry")
    @Expose
    private Boolean inheritGeometry;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("aliases")
    @Expose
    private List<Object> aliases = new ArrayList<Object>();
    @SerializedName("customAttributes")
    @Expose
    private List<CustomAttribute> customAttributes = new ArrayList<CustomAttribute>();
    @SerializedName("externalIds")
    @Expose
    private List<ExternalId> externalIds = new ArrayList<ExternalId>();
    @SerializedName("nodeTypes")
    @Expose
    private List<String> nodeTypes = new ArrayList<String>();

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Double getCapacityFull() {
        return capacityFull;
    }

    public void setCapacityFull(Double capacityFull) {
        this.capacityFull = capacityFull;
    }

    public Double getCapacityFree() {
        return capacityFree;
    }

    public void setCapacityFree(Double capacityFree) {
        this.capacityFree = capacityFree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInheritGeometry() {
        return inheritGeometry;
    }

    public void setInheritGeometry(Boolean inheritGeometry) {
        this.inheritGeometry = inheritGeometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getAliases() {
        return aliases;
    }

    public void setAliases(List<Object> aliases) {
        this.aliases = aliases;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public List<ExternalId> getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(List<ExternalId> externalIds) {
        this.externalIds = externalIds;
    }

    public List<String> getNodeTypes() {
        return nodeTypes;
    }

    public void setNodeTypes(List<String> nodeTypes) {
        this.nodeTypes = nodeTypes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Node.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("operation");
        sb.append('=');
        sb.append(((this.operation == null)?"<null>":this.operation));
        sb.append(',');
        sb.append("nodeId");
        sb.append('=');
        sb.append(((this.nodeId == null)?"<null>":this.nodeId));
        sb.append(',');
        sb.append("capacityFull");
        sb.append('=');
        sb.append(((this.capacityFull == null)?"<null>":this.capacityFull));
        sb.append(',');
        sb.append("capacityFree");
        sb.append('=');
        sb.append(((this.capacityFree == null)?"<null>":this.capacityFree));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("inheritGeometry");
        sb.append('=');
        sb.append(((this.inheritGeometry == null)?"<null>":this.inheritGeometry));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("aliases");
        sb.append('=');
        sb.append(((this.aliases == null)?"<null>":this.aliases));
        sb.append(',');
        sb.append("customAttributes");
        sb.append('=');
        sb.append(((this.customAttributes == null)?"<null>":this.customAttributes));
        sb.append(',');
        sb.append("externalIds");
        sb.append('=');
        sb.append(((this.externalIds == null)?"<null>":this.externalIds));
        sb.append(',');
        sb.append("nodeTypes");
        sb.append('=');
        sb.append(((this.nodeTypes == null)?"<null>":this.nodeTypes));
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
        result = ((result* 31)+((this.aliases == null)? 0 :this.aliases.hashCode()));
        result = ((result* 31)+((this.externalIds == null)? 0 :this.externalIds.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.capacityFree == null)? 0 :this.capacityFree.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.inheritGeometry == null)? 0 :this.inheritGeometry.hashCode()));
        result = ((result* 31)+((this.nodeTypes == null)? 0 :this.nodeTypes.hashCode()));
        result = ((result* 31)+((this.operation == null)? 0 :this.operation.hashCode()));
        result = ((result* 31)+((this.nodeId == null)? 0 :this.nodeId.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        result = ((result* 31)+((this.customAttributes == null)? 0 :this.customAttributes.hashCode()));
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
        return (((((((((((((this.capacityFull == rhs.capacityFull)||((this.capacityFull!= null)&&this.capacityFull.equals(rhs.capacityFull)))&&((this.aliases == rhs.aliases)||((this.aliases!= null)&&this.aliases.equals(rhs.aliases))))&&((this.externalIds == rhs.externalIds)||((this.externalIds!= null)&&this.externalIds.equals(rhs.externalIds))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.capacityFree == rhs.capacityFree)||((this.capacityFree!= null)&&this.capacityFree.equals(rhs.capacityFree))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.inheritGeometry == rhs.inheritGeometry)||((this.inheritGeometry!= null)&&this.inheritGeometry.equals(rhs.inheritGeometry))))&&((this.nodeTypes == rhs.nodeTypes)||((this.nodeTypes!= null)&&this.nodeTypes.equals(rhs.nodeTypes))))&&((this.operation == rhs.operation)||((this.operation!= null)&&this.operation.equals(rhs.operation))))&&((this.nodeId == rhs.nodeId)||((this.nodeId!= null)&&this.nodeId.equals(rhs.nodeId))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))))&&((this.customAttributes == rhs.customAttributes)||((this.customAttributes!= null)&&this.customAttributes.equals(rhs.customAttributes))));
    }

}
