#/bin/bash
directory=$1
db_name=$2
for connector_file in $(ls $directory/*.json); do
    connector_name=$(basename -- "${connector_file%.*}")
    if [ ! -z "$db_name" ]; then
	    jq '."database.dbname" = "'$db_name'"' $connector_file > $connector_file.tmp && mv $connector_file.tmp  $connector_file

    fi
    echo "Registering $connector_name from $connector_file"
    http PUT :8083/connectors/$connector_name/config < $connector_file
done
