
package com.cross_ni.cross.cdc.model.source;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class NodeTypes {

    @SerializedName("nodeTypes")
    @Expose
    private List<NodeType> nodeTypes = new ArrayList<NodeType>();

    public List<NodeType> getNodeTypes() {
        return nodeTypes;
    }

    public void setNodeTypes(List<NodeType> nodeTypes) {
        this.nodeTypes = nodeTypes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NodeTypes.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        result = ((result* 31)+((this.nodeTypes == null)? 0 :this.nodeTypes.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NodeTypes) == false) {
            return false;
        }
        NodeTypes rhs = ((NodeTypes) other);
        return ((this.nodeTypes == rhs.nodeTypes)||((this.nodeTypes!= null)&&this.nodeTypes.equals(rhs.nodeTypes)));
    }

}
