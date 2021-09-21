package com.cross_ni.cross.cdc.model.source;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NodeTypes {

	private final Set<NodeType> nodeTypes;

	public NodeTypes() {
		nodeTypes = new HashSet<>();
	}

	public NodeTypes addNodeType(NodeType nodeType) {
		nodeTypes.add(nodeType);
		return this;
	}

	public Set<NodeType> getNodeTypes() {
		return nodeTypes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NodeTypes nodeTypes1 = (NodeTypes) o;
		return Objects.equals(nodeTypes, nodeTypes1.nodeTypes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nodeTypes);
	}

	@Override
	public String toString() {
		return "NodeTypes{" +
				"nodeTypes=" + nodeTypes +
				'}';
	}
}
