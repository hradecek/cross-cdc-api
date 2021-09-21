package com.cross_ni.cross.cdc.model.source;

public class CaVal {

	private final String caDefId;
	private final long caSetId;
	private final String stringValue;

	private CaDefinition caDefinition;

	public CaVal(String caDefId, long caSetId, String stringValue) {
		this.caDefId = caDefId;
		this.caSetId = caSetId;
		this.stringValue = stringValue;
	}

	public String getCaDefId() {
		return caDefId;
	}

	public long getCaSetId() {
		return caSetId;
	}

	public String getStringValue() {
		return stringValue;
	}

	public CaDefinition getCaDefinition() {
		return caDefinition;
	}

	public CaVal setCaDefinition(CaDefinition caDefinition) {
		this.caDefinition = caDefinition;
		return this;
	}

	@Override
	public String toString() {
		return "CaVal{" +
				"caDefId='" + caDefId + '\'' +
				", caSetId=" + caSetId +
				", stringValue='" + stringValue + '\'' +
				", caDefinition=" + caDefinition +
				'}';
	}
}
