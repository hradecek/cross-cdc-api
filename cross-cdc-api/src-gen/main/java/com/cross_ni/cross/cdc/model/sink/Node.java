
package com.cross_ni.cross.cdc.model.sink;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "operation",
    "node_id",
    "capacity_full",
    "capacity_free",
    "description",
    "inherit_geometry",
    "name",
    "status",
    "aliases",
    "customAttributes",
    "externalIds",
    "nodeTypes"
})
@Generated("jsonschema2pojo")
public class Node {

    @JsonProperty("operation")
    private String operation;
    @JsonProperty("node_id")
    private String nodeId;
    @JsonProperty("capacity_full")
    private Double capacityFull;
    @JsonProperty("capacity_free")
    private Double capacityFree;
    @JsonProperty("description")
    private String description;
    @JsonProperty("inherit_geometry")
    private Boolean inheritGeometry;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("aliases")
    private List<Object> aliases = new ArrayList<Object>();
    @JsonProperty("customAttributes")
    private List<CustomAttribute> customAttributes = new ArrayList<CustomAttribute>();
    @JsonProperty("externalIds")
    private List<ExternalId> externalIds = new ArrayList<ExternalId>();
    @JsonProperty("nodeTypes")
    private List<String> nodeTypes = new ArrayList<String>();

    @JsonProperty("operation")
    public String getOperation() {
        return operation;
    }

    @JsonProperty("operation")
    public void setOperation(String operation) {
        this.operation = operation;
    }

    @JsonProperty("node_id")
    public String getNodeId() {
        return nodeId;
    }

    @JsonProperty("node_id")
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonProperty("capacity_full")
    public Double getCapacityFull() {
        return capacityFull;
    }

    @JsonProperty("capacity_full")
    public void setCapacityFull(Double capacityFull) {
        this.capacityFull = capacityFull;
    }

    @JsonProperty("capacity_free")
    public Double getCapacityFree() {
        return capacityFree;
    }

    @JsonProperty("capacity_free")
    public void setCapacityFree(Double capacityFree) {
        this.capacityFree = capacityFree;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("inherit_geometry")
    public Boolean getInheritGeometry() {
        return inheritGeometry;
    }

    @JsonProperty("inherit_geometry")
    public void setInheritGeometry(Boolean inheritGeometry) {
        this.inheritGeometry = inheritGeometry;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("aliases")
    public List<Object> getAliases() {
        return aliases;
    }

    @JsonProperty("aliases")
    public void setAliases(List<Object> aliases) {
        this.aliases = aliases;
    }

    @JsonProperty("customAttributes")
    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    @JsonProperty("customAttributes")
    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @JsonProperty("externalIds")
    public List<ExternalId> getExternalIds() {
        return externalIds;
    }

    @JsonProperty("externalIds")
    public void setExternalIds(List<ExternalId> externalIds) {
        this.externalIds = externalIds;
    }

    @JsonProperty("nodeTypes")
    public List<String> getNodeTypes() {
        return nodeTypes;
    }

    @JsonProperty("nodeTypes")
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
