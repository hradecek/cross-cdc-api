package com.cross_ni.cross.cdc.model.source;

import com.google.gson.annotations.SerializedName;

public class CaDefinition {

	private final String attributeName;
	@SerializedName(value = "class")
	private final String attributeClass;

	public CaDefinition(String attributeName, String attributeClass) {
		this.attributeName = attributeName;
		this.attributeClass = attributeClass;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public String getAttributeClass() {
		return attributeClass;
	}

	@Override
	public String toString() {
		return "CaDefinition{" +
				"attributeName='" + attributeName + '\'' +
				", attributeClass='" + attributeClass + '\'' +
				'}';
	}
}
