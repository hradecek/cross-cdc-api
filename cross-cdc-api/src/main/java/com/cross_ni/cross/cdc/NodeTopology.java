package com.cross_ni.cross.cdc;

import com.cross_ni.cross.cdc.model.source.CaDefinition;
import com.cross_ni.cross.cdc.model.source.CustomAttribute;
import com.cross_ni.cross.cdc.model.source.CustomAttributes;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.model.source.NodeType;
import com.cross_ni.cross.cdc.model.source.NodeTypes;
import com.cross_ni.cross.cdc.serialization.json.JsonSerdes;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;

public class NodeTopology {

	// TODO: Externalize
	private static final String TOPIC_NAME_CDC_NODES = "cross.cdc.node";

	private static final String TOPIC_NAME_NODE = "crossdb.public.node";
	private static final String TOPIC_NAME_NODE_TYPE = "crossdb.public.node_type";
	private static final String TOPIC_NAME_NODE_NODE_TYPE = "crossdb.public.node_node_type";

	private static final String TOPIC_NAME_CA_DEF = "crossdb.public.ca_def";
	private static final String TOPIC_NAME_CA_VALS = "crossdb.public.ca_vals";

	public static Topology build() {
		final StreamsBuilder builder = new StreamsBuilder();

		final GlobalKTable<String, CaDefinition> caDefs =
				builder.globalTable(TOPIC_NAME_CA_DEF, Consumed.with(Serdes.String(), JsonSerdes.CaDefinition()));
		final KTable<String, CustomAttributes> customAttributes =
				builder.stream(TOPIC_NAME_CA_VALS, Consumed.with(Serdes.String(), JsonSerdes.CaVal()))
						.join(caDefs, (k, v) -> v.getCaDefId(), CustomAttribute::setCaDefinition)
						.groupByKey()
						.aggregate(
								CustomAttributes::new,
								(key, value, aggregate) -> {
									System.out.println("Custom attributes aggregation: " + key);
									return aggregate.addCustomAttribute(value);
								},
								Materialized.with(Serdes.String(), JsonSerdes.CustomAttributes()));
		customAttributes.toStream().foreach((k, v) -> System.out.println(k));

		final KTable<String, Node> nodes =
				withNodeTypes(builder)
						.toStream()
						.selectKey((k, v) -> String.valueOf(v.getCaSetId()))
						.toTable(Materialized.with(Serdes.String(), JsonSerdes.Node()));

		final KTable<String, Node> nodesWithCa =
				nodes.outerJoin(
						customAttributes,
						(left, right) -> {
							if (left != null && right != null) {
								return left.setCa(right);
							} else {
								return null;
							}
						},
						Materialized.with(Serdes.String(), JsonSerdes.Node()));
		nodesWithCa.toStream().foreach((k, v) -> System.out.println(k + " " + v));

		return builder.build();
	}

	private static KTable<String, Node> withNodeTypes(StreamsBuilder builder) {
		final KTable<String, NodeTypes> nodeTypes = nodeTypes(builder);
		final KTable<String, Node> nodes = nodes(builder);

		return nodeTypes.join(
				nodes,
				(left, right) -> right.setNodeTypes(left),
				Materialized.with(Serdes.String(), JsonSerdes.Node()));
	}

	private static KTable<String, NodeTypes> nodeTypes(final StreamsBuilder builder) {
		final KStream<String, NodeNodeType> nodeNodeTypes = nodeNodeTypes(builder);
		final GlobalKTable<String, NodeType> globalNodeTypes = builder.globalTable(TOPIC_NAME_NODE_TYPE, Consumed.with(Serdes.String(), JsonSerdes.NodeType()));
		final KStream<String, NodeType> join = nodeNodeTypes.join(globalNodeTypes, (key, value) -> value.getDiscriminator(), (left, right) -> right);

		return join
				.groupByKey(Grouped.with(Serdes.String(), JsonSerdes.NodeType()))
				.aggregate(
						NodeTypes::new,
						(key, value, aggregate) -> {
							System.out.println("Node type aggregation: " + key);
							return aggregate.addNodeType(value);
						},
						Materialized.with(Serdes.String(), JsonSerdes.NodeTypes()));

	}

	private static KStream<String, NodeNodeType> nodeNodeTypes(final StreamsBuilder builder) {
		return builder.stream(TOPIC_NAME_NODE_NODE_TYPE, Consumed.with(JsonSerdes.NodeNodeType(), JsonSerdes.NodeNodeType()))
						.selectKey((key, value) -> String.valueOf(key.getNodeId()));
	}

	private static KTable<String, Node> nodes(final StreamsBuilder builder) {
		return builder.table(TOPIC_NAME_NODE, Consumed.with(Serdes.String(), JsonSerdes.Node()));
	}
}
