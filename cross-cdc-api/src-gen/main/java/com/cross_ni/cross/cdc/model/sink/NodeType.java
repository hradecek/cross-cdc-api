
package com.cross_ni.cross.cdc.model.sink;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "discriminator",
    "name"
})
@Generated("jsonschema2pojo")
public class NodeType {

    @JsonProperty("discriminator")
    private String discriminator;
    @JsonProperty("name")
    private String name;

    @JsonProperty("discriminator")
    public String getDiscriminator() {
        return discriminator;
    }

    @JsonProperty("discriminator")
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NodeType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("discriminator");
        sb.append('=');
        sb.append(((this.discriminator == null)?"<null>":this.discriminator));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
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
        return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.discriminator == rhs.discriminator)||((this.discriminator!= null)&&this.discriminator.equals(rhs.discriminator))));
    }

}
