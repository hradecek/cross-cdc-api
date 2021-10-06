
package com.cross_ni.cross.cdc.model.sink;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ExternalId {

    @SerializedName("external_id")
    @Expose
    private String externalId;
    @SerializedName("system_id")
    @Expose
    private String systemId;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExternalId.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        return (((this.externalId == rhs.externalId)||((this.externalId!= null)&&this.externalId.equals(rhs.externalId)))&&((this.systemId == rhs.systemId)||((this.systemId!= null)&&this.systemId.equals(rhs.systemId))));
    }

}
