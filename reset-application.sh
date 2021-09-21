#!/bin/bash
IP="172.26.0.3:9092"
consumer_groups="./bin/kafka-consumer-groups.sh --bootstrap-server $IP --all-groups --reset-offsets --to-earliest --all-topics --execute"
streams_application="./bin/kafka-streams-application-reset.sh --application-id dev --bootstrap-servers $IP"
delete_topic="./bin/kafka-topics.sh --bootstrap-server $IP --delete --topic cross.cdc.node"

rm -rf /tmp/kafka-streams/
sudo docker-compose exec kafka /bin/bash -c "$consumer_groups;$streams_application;$delete_topic"
