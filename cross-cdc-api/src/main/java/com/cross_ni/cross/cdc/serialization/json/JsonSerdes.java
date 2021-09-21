package com.cross_ni.cross.cdc.serialization.json;

import com.cross_ni.cross.cdc.model.source.CaDefinition;
import com.cross_ni.cross.cdc.model.source.CaSet;
import com.cross_ni.cross.cdc.model.source.CaVal;
import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.model.source.NodeNodeType;
import com.cross_ni.cross.cdc.model.source.NodeType;
import com.cross_ni.cross.cdc.model.source.NodeTypes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class JsonSerdes {

  public static Serde<CaDefinition> CaDefinition() {
    return createSerde(CaDefinition.class);
  }

  public static Serde<Node> Node() {
    return createSerde(Node.class);
  }

  public static Serde<CaSet> CaSet() {
    return createSerde(CaSet.class);
  }

  public static Serde<CaVal> CaVal() {
    return createSerde(CaVal.class);
  }

  public static Serde<NodeType> NodeType() {
    return createSerde(NodeType.class);
  }

  public static Serde<NodeTypes> NodeTypes() {
    return createSerde(NodeTypes.class);
  }

  public static Serde<NodeNodeType> NodeNodeType() {
    return createSerde(NodeNodeType.class);
  }

  private static <T> Serde<T> createSerde(Class<T> clazz) {
    JsonSerializer<T> serializer = new JsonSerializer<>();
    JsonDeserializer<T> deserializer = new JsonDeserializer<>(clazz);
    return Serdes.serdeFrom(serializer, deserializer);
  }
}
