package com.cross_ni.cross.cdc.model.source;

public class CaSet {

	private final long id;

	public CaSet(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "CaSet{" +
				"id=" + id +
				'}';
	}
}
