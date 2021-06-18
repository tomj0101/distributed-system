### start VM

```
vagrant up
``

### Vm Details
```

This version was created over 4 years ago.

PostgreSQL

Minimum Specs:
memory: 512MB
cpu: 1

Software Versions:

PostgreSQL: 9.6.1
PostgreSQL Credentials

username: postgresql
password:

```

# Install PostgreSQL Client on Developer machine
```

sudo apt install pgadmin3 -y

```



### Initial script for the database
username: usr_ebank
password: ebank1234
database: ebankv1

### Create user
```

-- Role: usr_ebank

-- DROP ROLE usr_ebank;

CREATE ROLE usr_ebank LOGIN
ENCRYPTED PASSWORD 'md5d1f68dfe6a808773402a65bfcd73e47f'
SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION CONNECTION LIMIT 50 VALID UNTIL '2022-06-11 00:00:00';
COMMENT ON ROLE usr_ebank IS 'Database user, also know as services user';

```

### Create database
```

CREATE DATABASE ebankv1
WITH ENCODING='UTF8'
CONNECTION LIMIT=-1;

COMMENT ON DATABASE ebankv1
IS 'Database for Version 1-Monolithic version';

```

```
