
package com.cross_ni.cross.cdc.model.sink;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "definition"
})
@Generated("jsonschema2pojo")
public class CustomAttribute {

    @JsonProperty("value")
    private Object value;
    @JsonProperty("definition")
    private CustomAttributeDefinition definition;

    @JsonProperty("value")
    public Object getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Object value) {
        this.value = value;
    }

    @JsonProperty("definition")
    public CustomAttributeDefinition getDefinition() {
        return definition;
    }

    @JsonProperty("definition")
    public void setDefinition(CustomAttributeDefinition definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CustomAttribute.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
        sb.append(',');
        sb.append("definition");
        sb.append('=');
        sb.append(((this.definition == null)?"<null>":this.definition));
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
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.definition == null)? 0 :this.definition.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CustomAttribute) == false) {
            return false;
        }
        CustomAttribute rhs = ((CustomAttribute) other);
        return (((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value)))&&((this.definition == rhs.definition)||((this.definition!= null)&&this.definition.equals(rhs.definition))));
    }

}
