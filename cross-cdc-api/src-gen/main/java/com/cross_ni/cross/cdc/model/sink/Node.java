
package com.cross_ni.cross.cdc.model.sink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "operation",
    "attribute_type",
    "node_id",
    "name",
    "description",
    "inherit_geometry",
    "capacity_full",
    "capacity_free",
    "nodeTypes"
})
@Generated("jsonschema2pojo")
public class Node {

    @JsonProperty("operation")
    private String operation;
    @JsonProperty("attribute_type")
    private Node.AttributeType attributeType;
    @JsonProperty("node_id")
    private String nodeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("inherit_geometry")
    private Boolean inheritGeometry;
    @JsonProperty("capacity_full")
    private Double capacityFull;
    @JsonProperty("capacity_free")
    private Double capacityFree;
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

    @JsonProperty("attribute_type")
    public Node.AttributeType getAttributeType() {
        return attributeType;
    }

    @JsonProperty("attribute_type")
    public void setAttributeType(Node.AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    @JsonProperty("node_id")
    public String getNodeId() {
        return nodeId;
    }

    @JsonProperty("node_id")
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
        sb.append("attributeType");
        sb.append('=');
        sb.append(((this.attributeType == null)?"<null>":this.attributeType));
        sb.append(',');
        sb.append("nodeId");
        sb.append('=');
        sb.append(((this.nodeId == null)?"<null>":this.nodeId));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("inheritGeometry");
        sb.append('=');
        sb.append(((this.inheritGeometry == null)?"<null>":this.inheritGeometry));
        sb.append(',');
        sb.append("capacityFull");
        sb.append('=');
        sb.append(((this.capacityFull == null)?"<null>":this.capacityFull));
        sb.append(',');
        sb.append("capacityFree");
        sb.append('=');
        sb.append(((this.capacityFree == null)?"<null>":this.capacityFree));
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
        result = ((result* 31)+((this.attributeType == null)? 0 :this.attributeType.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.inheritGeometry == null)? 0 :this.inheritGeometry.hashCode()));
        result = ((result* 31)+((this.nodeTypes == null)? 0 :this.nodeTypes.hashCode()));
        result = ((result* 31)+((this.operation == null)? 0 :this.operation.hashCode()));
        result = ((result* 31)+((this.nodeId == null)? 0 :this.nodeId.hashCode()));
        result = ((result* 31)+((this.capacityFree == null)? 0 :this.capacityFree.hashCode()));
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
        return ((((((((((this.capacityFull == rhs.capacityFull)||((this.capacityFull!= null)&&this.capacityFull.equals(rhs.capacityFull)))&&((this.attributeType == rhs.attributeType)||((this.attributeType!= null)&&this.attributeType.equals(rhs.attributeType))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.inheritGeometry == rhs.inheritGeometry)||((this.inheritGeometry!= null)&&this.inheritGeometry.equals(rhs.inheritGeometry))))&&((this.nodeTypes == rhs.nodeTypes)||((this.nodeTypes!= null)&&this.nodeTypes.equals(rhs.nodeTypes))))&&((this.operation == rhs.operation)||((this.operation!= null)&&this.operation.equals(rhs.operation))))&&((this.nodeId == rhs.nodeId)||((this.nodeId!= null)&&this.nodeId.equals(rhs.nodeId))))&&((this.capacityFree == rhs.capacityFree)||((this.capacityFree!= null)&&this.capacityFree.equals(rhs.capacityFree))));
    }

    @Generated("jsonschema2pojo")
    public enum AttributeType {

        NODE_TYPE("NODE_TYPE");
        private final String value;
        private final static Map<String, Node.AttributeType> CONSTANTS = new HashMap<String, Node.AttributeType>();

        static {
            for (Node.AttributeType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AttributeType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Node.AttributeType fromValue(String value) {
            Node.AttributeType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
