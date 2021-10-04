
package com.cross_ni.cross.cdc.model.sink;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Link {

    @SerializedName("linkEnds")
    @Expose
    private List<Double> linkEnds = new ArrayList<Double>();
    @SerializedName("linkType")
    @Expose
    private String linkType;
    @SerializedName("customAttributes")
    @Expose
    private List<CustomAttribute> customAttributes = new ArrayList<CustomAttribute>();
    @SerializedName("externalIds")
    @Expose
    private List<ExternalId> externalIds = new ArrayList<ExternalId>();

    public List<Double> getLinkEnds() {
        return linkEnds;
    }

    public void setLinkEnds(List<Double> linkEnds) {
        this.linkEnds = linkEnds;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public List<ExternalId> getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(List<ExternalId> externalIds) {
        this.externalIds = externalIds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Link.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("linkEnds");
        sb.append('=');
        sb.append(((this.linkEnds == null)?"<null>":this.linkEnds));
        sb.append(',');
        sb.append("linkType");
        sb.append('=');
        sb.append(((this.linkType == null)?"<null>":this.linkType));
        sb.append(',');
        sb.append("customAttributes");
        sb.append('=');
        sb.append(((this.customAttributes == null)?"<null>":this.customAttributes));
        sb.append(',');
        sb.append("externalIds");
        sb.append('=');
        sb.append(((this.externalIds == null)?"<null>":this.externalIds));
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
        result = ((result* 31)+((this.externalIds == null)? 0 :this.externalIds.hashCode()));
        result = ((result* 31)+((this.linkType == null)? 0 :this.linkType.hashCode()));
        result = ((result* 31)+((this.linkEnds == null)? 0 :this.linkEnds.hashCode()));
        result = ((result* 31)+((this.customAttributes == null)? 0 :this.customAttributes.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Link) == false) {
            return false;
        }
        Link rhs = ((Link) other);
        return (((((this.externalIds == rhs.externalIds)||((this.externalIds!= null)&&this.externalIds.equals(rhs.externalIds)))&&((this.linkType == rhs.linkType)||((this.linkType!= null)&&this.linkType.equals(rhs.linkType))))&&((this.linkEnds == rhs.linkEnds)||((this.linkEnds!= null)&&this.linkEnds.equals(rhs.linkEnds))))&&((this.customAttributes == rhs.customAttributes)||((this.customAttributes!= null)&&this.customAttributes.equals(rhs.customAttributes))));
    }

}
