
package com.cross_ni.cross.cdc.model.source;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class NodeType {

    @SerializedName("discriminator")
    @Expose
    private String discriminator;
    @SerializedName("capacity_full")
    @Expose
    private String capacityFull;
    @SerializedName("multiparent")
    @Expose
    private String multiparent;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("root_type")
    @Expose
    private String rootType;

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getCapacityFull() {
        return capacityFull;
    }

    public void setCapacityFull(String capacityFull) {
        this.capacityFull = capacityFull;
    }

    public String getMultiparent() {
        return multiparent;
    }

    public void setMultiparent(String multiparent) {
        this.multiparent = multiparent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRootType() {
        return rootType;
    }

    public void setRootType(String rootType) {
        this.rootType = rootType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NodeType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.multiparent == null)? 0 :this.multiparent.hashCode()));
        result = ((result* 31)+((this.rootType == null)? 0 :this.rootType.hashCode()));
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
        return ((((((this.capacityFull == rhs.capacityFull)||((this.capacityFull!= null)&&this.capacityFull.equals(rhs.capacityFull)))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.multiparent == rhs.multiparent)||((this.multiparent!= null)&&this.multiparent.equals(rhs.multiparent))))&&((this.rootType == rhs.rootType)||((this.rootType!= null)&&this.rootType.equals(rhs.rootType))))&&((this.discriminator == rhs.discriminator)||((this.discriminator!= null)&&this.discriminator.equals(rhs.discriminator))));
    }

}
