package com.cross_ni.cross.cdc;

import com.cross_ni.cross.cdc.model.source.CaDefinition;
import com.cross_ni.cross.cdc.model.source.CaSet;
import com.cross_ni.cross.cdc.model.source.CaVal;
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
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;

public class NodeTopology {


	// TODO: Externalize
	private static final String TOPIC_NAME_CDC_NODES = "cross.cdc.node";

	private static final String TOPIC_NAME_NODE = "crossdb.public.node";
	private static final String TOPIC_NAME_NODE_TYPE = "crossdb.public.node_type";
	private static final String TOPIC_NAME_NODE_NODE_TYPE = "crossdb.public.node_node_type";

	private static final String TOPIC_NAME_CA_VAL_STRING = "crossdb.public.ca_val_string";
//	private static final String TOPIC_NAME_CA_VAL_INTEGER = "crossdb.public.ca_val_int";
	private static final String TOPIC_NAME_CA_DEF = "crossdb.public.ca_def";

	public static Topology build() {
		final StreamsBuilder builder = new StreamsBuilder();
		final GlobalKTable<String, CaDefinition> caDefs = builder.globalTable(TOPIC_NAME_CA_DEF, Consumed.with(Serdes.String(), JsonSerdes.CaDefinition()));
		final KStream<String, CaVal> caVals = builder.stream(TOPIC_NAME_CA_VAL_STRING, Consumed.with(JsonSerdes.CaVal(), JsonSerdes.CaVal())).selectKey((k, v) -> String.valueOf(v.getCaSetId()));
		caVals
				.join(caDefs, (k, v) -> v.getCaDefId(), CaVal::setCaDefinition)
				.join(withNodeTypes(builder), (left, right) -> right.addCa(left), Joined.with(Serdes.String(), JsonSerdes.CaVal(), JsonSerdes.Node()))
				.foreach((k, v) -> System.out.println(k + " " + v));

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
						(key, value, aggregate) -> aggregate.addNodeType(value),
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
