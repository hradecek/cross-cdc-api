
package com.cross_ni.cross.cdc.model.sink;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "attributeClass"
})
@Generated("jsonschema2pojo")
public class CustomAttributeDefinition {

    @JsonProperty("attributeClass")
    private String attributeClass;

    @JsonProperty("attributeClass")
    public String getAttributeClass() {
        return attributeClass;
    }

    @JsonProperty("attributeClass")
    public void setAttributeClass(String attributeClass) {
        this.attributeClass = attributeClass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CustomAttributeDefinition.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("attributeClass");
        sb.append('=');
        sb.append(((this.attributeClass == null)?"<null>":this.attributeClass));
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
        result = ((result* 31)+((this.attributeClass == null)? 0 :this.attributeClass.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CustomAttributeDefinition) == false) {
            return false;
        }
        CustomAttributeDefinition rhs = ((CustomAttributeDefinition) other);
        return ((this.attributeClass == rhs.attributeClass)||((this.attributeClass!= null)&&this.attributeClass.equals(rhs.attributeClass)));
    }

}
