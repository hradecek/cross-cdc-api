package com.cross_ni.cross.cdc.model.source;

import com.google.gson.annotations.SerializedName;

public class CustomAttribute {

	private final String caDefId;
	private final long caSetId;
	private CaDefinition caDefinition;

	@SerializedName(value = "value", alternate = {"string_value", "int_value"})
	private Object value;

	public CustomAttribute(String caDefId, long caSetId) {
		this.caDefId = caDefId;
		this.caSetId = caSetId;
	}

	public String getCaDefId() {
		return caDefId;
	}

	public long getCaSetId() {
		return caSetId;
	}

	public CaDefinition getCaDefinition() {
		return caDefinition;
	}

	public CustomAttribute setCaDefinition(CaDefinition caDefinition) {
		this.caDefinition = caDefinition;
		return this;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "CaVal{" +
				"caDefId='" + caDefId + '\'' +
				", caSetId=" + caSetId +
				", caDefinition=" + caDefinition +
				", value=" + value +
				'}';
	}
}
