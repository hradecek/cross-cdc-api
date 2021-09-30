
package com.cross_ni.cross.cdc.model.source;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ExternalId {

    @SerializedName("__op")
    @Expose
    private String op;
    @SerializedName("__source_ts_ms")
    @Expose
    private Double sourceTsMs;
    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("external_id")
    @Expose
    private String externalId;
    @SerializedName("system_id")
    @Expose
    private String systemId;
    @SerializedName("internal_id")
    @Expose
    private String internalId;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Double getSourceTsMs() {
        return sourceTsMs;
    }

    public void setSourceTsMs(Double sourceTsMs) {
        this.sourceTsMs = sourceTsMs;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

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

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExternalId.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("op");
        sb.append('=');
        sb.append(((this.op == null)?"<null>":this.op));
        sb.append(',');
        sb.append("sourceTsMs");
        sb.append('=');
        sb.append(((this.sourceTsMs == null)?"<null>":this.sourceTsMs));
        sb.append(',');
        sb.append("entity");
        sb.append('=');
        sb.append(((this.entity == null)?"<null>":this.entity));
        sb.append(',');
        sb.append("externalId");
        sb.append('=');
        sb.append(((this.externalId == null)?"<null>":this.externalId));
        sb.append(',');
        sb.append("systemId");
        sb.append('=');
        sb.append(((this.systemId == null)?"<null>":this.systemId));
        sb.append(',');
        sb.append("internalId");
        sb.append('=');
        sb.append(((this.internalId == null)?"<null>":this.internalId));
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
        result = ((result* 31)+((this.sourceTsMs == null)? 0 :this.sourceTsMs.hashCode()));
        result = ((result* 31)+((this.op == null)? 0 :this.op.hashCode()));
        result = ((result* 31)+((this.internalId == null)? 0 :this.internalId.hashCode()));
        result = ((result* 31)+((this.systemId == null)? 0 :this.systemId.hashCode()));
        result = ((result* 31)+((this.externalId == null)? 0 :this.externalId.hashCode()));
        result = ((result* 31)+((this.entity == null)? 0 :this.entity.hashCode()));
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
        return (((((((this.sourceTsMs == rhs.sourceTsMs)||((this.sourceTsMs!= null)&&this.sourceTsMs.equals(rhs.sourceTsMs)))&&((this.op == rhs.op)||((this.op!= null)&&this.op.equals(rhs.op))))&&((this.internalId == rhs.internalId)||((this.internalId!= null)&&this.internalId.equals(rhs.internalId))))&&((this.systemId == rhs.systemId)||((this.systemId!= null)&&this.systemId.equals(rhs.systemId))))&&((this.externalId == rhs.externalId)||((this.externalId!= null)&&this.externalId.equals(rhs.externalId))))&&((this.entity == rhs.entity)||((this.entity!= null)&&this.entity.equals(rhs.entity))));
    }

}
