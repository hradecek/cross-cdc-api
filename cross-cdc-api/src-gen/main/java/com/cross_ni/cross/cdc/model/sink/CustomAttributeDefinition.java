
package com.cross_ni.cross.cdc.model.sink;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CustomAttributeDefinition {

    @SerializedName("attributeClass")
    @Expose
    private String attributeClass;

    public String getAttributeClass() {
        return attributeClass;
    }

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
