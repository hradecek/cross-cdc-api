
package com.cross_ni.cross.cdc.model.sink;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "operation",
    "external_id",
    "system_id"
})
@Generated("jsonschema2pojo")
public class ExternalId {

    @JsonProperty("operation")
    private String operation;
    @JsonProperty("external_id")
    private String externalId;
    @JsonProperty("system_id")
    private String systemId;

    @JsonProperty("operation")
    public String getOperation() {
        return operation;
    }

    @JsonProperty("operation")
    public void setOperation(String operation) {
        this.operation = operation;
    }

    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @JsonProperty("system_id")
    public String getSystemId() {
        return systemId;
    }

    @JsonProperty("system_id")
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExternalId.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("operation");
        sb.append('=');
        sb.append(((this.operation == null)?"<null>":this.operation));
        sb.append(',');
        sb.append("externalId");
        sb.append('=');
        sb.append(((this.externalId == null)?"<null>":this.externalId));
        sb.append(',');
        sb.append("systemId");
        sb.append('=');
        sb.append(((this.systemId == null)?"<null>":this.systemId));
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
        result = ((result* 31)+((this.externalId == null)? 0 :this.externalId.hashCode()));
        result = ((result* 31)+((this.systemId == null)? 0 :this.systemId.hashCode()));
        result = ((result* 31)+((this.operation == null)? 0 :this.operation.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExternalId) == false) {
            return false;
        }
        ExternalId rhs = ((ExternalId) other);
        return ((((this.externalId == rhs.externalId)||((this.externalId!= null)&&this.externalId.equals(rhs.externalId)))&&((this.systemId == rhs.systemId)||((this.systemId!= null)&&this.systemId.equals(rhs.systemId))))&&((this.operation == rhs.operation)||((this.operation!= null)&&this.operation.equals(rhs.operation))));
    }

}
