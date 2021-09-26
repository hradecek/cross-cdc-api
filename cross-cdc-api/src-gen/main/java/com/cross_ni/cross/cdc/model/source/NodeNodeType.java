
package com.cross_ni.cross.cdc.model.source;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class NodeNodeType {

    @SerializedName("node_id")
    @Expose
    private String nodeId;
    @SerializedName("discriminator")
    @Expose
    private String discriminator;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NodeNodeType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("nodeId");
        sb.append('=');
        sb.append(((this.nodeId == null)?"<null>":this.nodeId));
        sb.append(',');
        sb.append("discriminator");
        sb.append('=');
        sb.append(((this.discriminator == null)?"<null>":this.discriminator));
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
        result = ((result* 31)+((this.nodeId == null)? 0 :this.nodeId.hashCode()));
        result = ((result* 31)+((this.discriminator == null)? 0 :this.discriminator.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NodeNodeType) == false) {
            return false;
        }
        NodeNodeType rhs = ((NodeNodeType) other);
        return (((this.nodeId == rhs.nodeId)||((this.nodeId!= null)&&this.nodeId.equals(rhs.nodeId)))&&((this.discriminator == rhs.discriminator)||((this.discriminator!= null)&&this.discriminator.equals(rhs.discriminator))));
    }

}
