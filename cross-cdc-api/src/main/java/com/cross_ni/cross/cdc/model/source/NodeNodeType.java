package com.cross_ni.cross.cdc.model.source;

public class NodeNodeType {

	private long nodeId;
	private String discriminator;

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	@Override
	public String toString() {
		return "NodeNodeType{" +
				"nodeId=" + nodeId +
				", discriminator='" + discriminator + '\'' +
				'}';
	}
}
