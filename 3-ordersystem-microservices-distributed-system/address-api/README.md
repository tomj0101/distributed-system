# addressapi
address api: develop with Python + Cassandra.

## Run in Dev

To start your application in the dev profile, run:

```
flask run
open http://localhost:5000/api
open http://localhost:8081/api/addresses
```

## Run in Prod mode
uWSGI

uWSGI is a fast application server written in C. It is very configurable which makes it more complicated to setup than gunicorn.

Running uWSGI HTTP Router:
```
uwsgi --socket 0.0.0.0:8081 --protocol=http -w wsgi:app
--- OR ---
./entrypoint.sh

Open http://localhost:8081/api
```

### Python3 init install for prod server
```
sudo apt install nginx
sudo systemctl disable nginx # Just do this in you dev machine for avoid auto-start

sudo apt install python3-pip python3-dev build-essential libssl-dev libffi-dev python3-setuptools

pip3 install virtualenv

python3 -m virtualenv addressapienv
source addressapienv/bin/activate
pip install wheel
deactivate
```

## Cassandra Initial configuration
```
cqlsh

#create a cassandra keyspace:
CREATE KEYSPACE ebank_data
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

# list keyspaces
SELECT * FROM system_schema.keyspaces;

USE ebank_data;

CREATE TABLE address(
   id uuid PRIMARY KEY,
   streetAddress text,
   postalCode varint,
   city text,
   stateProvince text,
   country text,
   created timestamp,
   gpsLatLong tuple<text, text>
);

// gpsLatLong list<int> // A list of integers [34.0207289, -118.6926043]
// gpsLatLong tuple<text, text> // A list of integers ('34.0207289', '-118.6926043')
phones map<text, phone>

select * from address;

INSERT INTO address(id, streetAddress, postalCode, city, stateProvince, country, created, gpsLatLong) 
VALUES (
    uuid(),
    '111 Stree',
    05896,
    'LA',
    'CA',
    'USA',
    dateof(now()),
    ('34.0207289', '-118.6926043')
);

select * from address;

#Note: in cassandra system.local is like dual in oracle
```