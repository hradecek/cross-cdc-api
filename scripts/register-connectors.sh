#/bin/bash
directory=$1
for connector_file in $(ls $directory/*.json); do
    connector_name="${connector_file%.*}"
    http PUT :8083/connectors/$connector_name/config < $connector_file
done
