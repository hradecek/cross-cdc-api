
package com.cross_ni.cross.cdc.model.source;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Operation {

    @SerializedName("__op")
    @Expose
    private String op;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Operation.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("op");
        sb.append('=');
        sb.append(((this.op == null)?"<null>":this.op));
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
        result = ((result* 31)+((this.op == null)? 0 :this.op.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Operation) == false) {
            return false;
        }
        Operation rhs = ((Operation) other);
        return ((this.op == rhs.op)||((this.op!= null)&&this.op.equals(rhs.op)));
    }

}
