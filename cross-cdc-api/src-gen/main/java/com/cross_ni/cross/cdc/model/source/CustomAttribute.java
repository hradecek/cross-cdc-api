
package com.cross_ni.cross.cdc.model.source;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CustomAttribute {

    @SerializedName("__op")
    @Expose
    private String op;
    @SerializedName("__source_ts_ms")
    @Expose
    private Double sourceTsMs;
    @SerializedName("value")
    @Expose
    private Object value;

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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CustomAttribute.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("op");
        sb.append('=');
        sb.append(((this.op == null)?"<null>":this.op));
        sb.append(',');
        sb.append("sourceTsMs");
        sb.append('=');
        sb.append(((this.sourceTsMs == null)?"<null>":this.sourceTsMs));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
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
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
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
        return ((((this.sourceTsMs == rhs.sourceTsMs)||((this.sourceTsMs!= null)&&this.sourceTsMs.equals(rhs.sourceTsMs)))&&((this.op == rhs.op)||((this.op!= null)&&this.op.equals(rhs.op))))&&((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value))));
    }

}
