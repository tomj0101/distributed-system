#!/bin/sh

# Below shells script is required because the flask container need to wait for cassandra db server to startup before
# accessing it below.

RETRIES=5
USER=cassandra
PASSWORD=cassandra
KEYSPACE=EBANK_DATA
HOST=127.0.0.1

test_db_conection(){
  until cqlsh -h $HOST -U $USER -p $DATABASE -c "select 1" > /dev/null 2>&1 || [ $RETRIES -eq 0 ]; do
    echo "Waiting for cassandra server to start, $((RETRIES)) remaining attempts..."
    RETRIES=$((RETRIES-=1))
    sleep 1
  done
}

# test_db_conection()

echo "Cassandra started!"

# now run the application
#flask run # just use this for development mode

# production run
#uwsgi --ini /etc/uwsgi.ini
uwsgi --socket 0.0.0.0:5000 --protocol=http -w wsgi:app
