
package com.cross_ni.cross.cdc.model.sink;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "op",
    "node_id",
    "discriminator",
    "capacity_full",
    "multiparent",
    "name",
    "root_type"
})
@Generated("jsonschema2pojo")
public class NodeType {

    @JsonProperty("op")
    private String op;
    @JsonProperty("node_id")
    private String nodeId;
    @JsonProperty("discriminator")
    private String discriminator;
    @JsonProperty("capacity_full")
    private String capacityFull;
    @JsonProperty("multiparent")
    private String multiparent;
    @JsonProperty("name")
    private String name;
    @JsonProperty("root_type")
    private String rootType;

    @JsonProperty("op")
    public String getOp() {
        return op;
    }

    @JsonProperty("op")
    public void setOp(String op) {
        this.op = op;
    }

    @JsonProperty("node_id")
    public String getNodeId() {
        return nodeId;
    }

    @JsonProperty("node_id")
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonProperty("discriminator")
    public String getDiscriminator() {
        return discriminator;
    }

    @JsonProperty("discriminator")
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    @JsonProperty("capacity_full")
    public String getCapacityFull() {
        return capacityFull;
    }

    @JsonProperty("capacity_full")
    public void setCapacityFull(String capacityFull) {
        this.capacityFull = capacityFull;
    }

    @JsonProperty("multiparent")
    public String getMultiparent() {
        return multiparent;
    }

    @JsonProperty("multiparent")
    public void setMultiparent(String multiparent) {
        this.multiparent = multiparent;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("root_type")
    public String getRootType() {
        return rootType;
    }

    @JsonProperty("root_type")
    public void setRootType(String rootType) {
        this.rootType = rootType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NodeType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("op");
        sb.append('=');
        sb.append(((this.op == null)?"<null>":this.op));
        sb.append(',');
        sb.append("nodeId");
        sb.append('=');
        sb.append(((this.nodeId == null)?"<null>":this.nodeId));
        sb.append(',');
        sb.append("discriminator");
        sb.append('=');
        sb.append(((this.discriminator == null)?"<null>":this.discriminator));
        sb.append(',');
        sb.append("capacityFull");
        sb.append('=');
        sb.append(((this.capacityFull == null)?"<null>":this.capacityFull));
        sb.append(',');
        sb.append("multiparent");
        sb.append('=');
        sb.append(((this.multiparent == null)?"<null>":this.multiparent));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("rootType");
        sb.append('=');
        sb.append(((this.rootType == null)?"<null>":this.rootType));
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
        result = ((result* 31)+((this.op == null)? 0 :this.op.hashCode()));
        result = ((result* 31)+((this.multiparent == null)? 0 :this.multiparent.hashCode()));
        result = ((result* 31)+((this.rootType == null)? 0 :this.rootType.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.nodeId == null)? 0 :this.nodeId.hashCode()));
        result = ((result* 31)+((this.discriminator == null)? 0 :this.discriminator.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NodeType) == false) {
            return false;
        }
        NodeType rhs = ((NodeType) other);
        return ((((((((this.capacityFull == rhs.capacityFull)||((this.capacityFull!= null)&&this.capacityFull.equals(rhs.capacityFull)))&&((this.op == rhs.op)||((this.op!= null)&&this.op.equals(rhs.op))))&&((this.multiparent == rhs.multiparent)||((this.multiparent!= null)&&this.multiparent.equals(rhs.multiparent))))&&((this.rootType == rhs.rootType)||((this.rootType!= null)&&this.rootType.equals(rhs.rootType))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.nodeId == rhs.nodeId)||((this.nodeId!= null)&&this.nodeId.equals(rhs.nodeId))))&&((this.discriminator == rhs.discriminator)||((this.discriminator!= null)&&this.discriminator.equals(rhs.discriminator))));
    }

}
