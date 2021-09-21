package com.cross_ni.cross.cdc.model.source;

public class NodeType {
	private String discriminator;
	private String name;

	public String getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "NodeType{" +
				"discriminator='" + discriminator + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
