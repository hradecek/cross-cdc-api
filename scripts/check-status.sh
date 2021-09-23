#/bin/bash
directory=$1
for connector_file in $(ls $directory/*.json); do
    connector_name=$(basename -- "${connector_file%.*}")
    http :8083/connectors/$connector_name/status
done
