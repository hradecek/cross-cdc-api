#/bin/bash
for connector_file in $(ls *.json); do
    connector_name="${connector_file%.*}"
    http :8083/connectors/$connector_name/status
done
