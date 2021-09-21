#!/bin/bash
IP="${1:-172.26.0.3:9092}"
consumer_groups="echo 'Resetting offsets'; ./bin/kafka-consumer-groups.sh --bootstrap-server $IP --all-groups --reset-offsets --to-earliest --all-topics --execute"
streams_application="./bin/kafka-streams-application-reset.sh --application-id dev --bootstrap-servers $IP"
# delete_topic="echo 'Removing sink topic'; ./bin/kafka-topics.sh --bootstrap-server $IP --delete --topic cross.cdc.node"

sudo docker-compose exec kafka /bin/bash -c "$consumer_groups;$streams_application" # ;$delete_topic"
echo "Removing local state-store"
rm -rf /tmp/kafka-streams/
