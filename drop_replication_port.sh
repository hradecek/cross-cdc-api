#!/bin/bash
psql -c "select pg_drop_replication_slot('debezium');"
