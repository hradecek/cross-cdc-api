package com.cross_ni.cross.cdc.model.source;

import java.util.HashSet;
import java.util.Set;

public class Node {

	private long nodeId;
	private long caSetId;
	private String nodeName;
	private String description;
	private Set<NodeType> nodeTypes = new HashSet<>();
	private Set<CaVal> customAttributes = new HashSet<>();

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public long getCaSetId() {
		return caSetId;
	}

	public void setCaSetId(long caSetId) {
		this.caSetId = caSetId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Node addCa(CaVal caValue) {
		this.customAttributes.add(caValue);
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<NodeType> getNodeTypes() {
		return nodeTypes;
	}

	public void setNodeTypes(Set<NodeType> nodeTypes) {
		this.nodeTypes = nodeTypes;
	}

	public Node setNodeTypes(NodeTypes nodeTypes) {
		this.nodeTypes = nodeTypes.getNodeTypes();
		return this;
	}



	@Override
	public String toString() {
		return "Node{" +
				"nodeId=" + nodeId +
				", nodeName='" + nodeName + '\'' +
				", description='" + description + '\'' +
				", nodeTypes=" + nodeTypes +
				", customAttributes=" + customAttributes +
				'}';
	}
}
