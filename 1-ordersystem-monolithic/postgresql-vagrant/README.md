### start VM

```
vagrant up
```

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
SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION CONNECTION LIMIT 50 VALID UNTIL '2021-12-20 00:00:00';
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

# Drop tables (clean table for dev env)
```
DROP TABLE public.address CASCADE;
DROP TABLE public.databasechangelog CASCADE;
DROP TABLE public.databasechangeloglock CASCADE;
DROP TABLE public.order_details CASCADE;
DROP TABLE public.order_master CASCADE;
DROP TABLE public.product CASCADE;
DROP TABLE public.status CASCADE;
DROP TABLE public.tbl_authority CASCADE;
DROP TABLE public.tbl_persistent_token CASCADE;
DROP TABLE public.tbl_user CASCADE;
DROP TABLE public.tbl_user_authority CASCADE;
```

# Truncate/cleanup table (for dev env)
```
TRUNCATE TABLE public.address CASCADE;
TRUNCATE TABLE public.order_details CASCADE;
TRUNCATE TABLE public.order_master CASCADE;
TRUNCATE TABLE public.product CASCADE;
TRUNCATE TABLE public.status CASCADE;
```

# Insert some data
```
insert into status (description, name, register_date, id) values (?, ?, ?, ?)
insert into status (description, name, register_date, id) values (?, ?, ?, ?)

insert into address (city, postal_code, state_province, street_address, user_id, id) values (?, ?, ?, ?, ?, ?)
insert into address (city, postal_code, state_province, street_address, user_id, id) values (?, ?, ?, ?, ?, ?)

```

# Dev mode Database connection from Java-Spring JPA
```
spring:
  devtools:
    restart:
      enabled: true
      additional-exclude: static
    livereload:
      enabled: false # we use Webpack dev server + BrowserSync for livereload
  jackson:
    serialization:
      indent-output: true
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:postgresql://localhost:5432/ebankv1
    username: usr_ebank
    password: ebank1234
```