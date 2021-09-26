
package com.cross_ni.cross.cdc.model.sink;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "customAttributes",
    "nodeTypes"
})
@Generated("jsonschema2pojo")
public class Node {

    @JsonProperty("name")
    private String name;
    @JsonProperty("customAttributes")
    private List<CustomAttribute> customAttributes = new ArrayList<CustomAttribute>();
    @JsonProperty("nodeTypes")
    private List<NodeType> nodeTypes = new ArrayList<NodeType>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("customAttributes")
    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    @JsonProperty("customAttributes")
    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    @JsonProperty("nodeTypes")
    public List<NodeType> getNodeTypes() {
        return nodeTypes;
    }

    @JsonProperty("nodeTypes")
    public void setNodeTypes(List<NodeType> nodeTypes) {
        this.nodeTypes = nodeTypes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Node.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("customAttributes");
        sb.append('=');
        sb.append(((this.customAttributes == null)?"<null>":this.customAttributes));
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.nodeTypes == null)? 0 :this.nodeTypes.hashCode()));
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
        return ((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.nodeTypes == rhs.nodeTypes)||((this.nodeTypes!= null)&&this.nodeTypes.equals(rhs.nodeTypes))))&&((this.customAttributes == rhs.customAttributes)||((this.customAttributes!= null)&&this.customAttributes.equals(rhs.customAttributes))));
    }

}
