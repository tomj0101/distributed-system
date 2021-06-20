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
username: usr_ebankv2
password: ebank1234
database: ebankv2

### Create user & Database
```
CREATE ROLE usr_ebankv2 LOGIN
  ENCRYPTED PASSWORD 'md582b91e414a894a099ae514cf33d826f6'
  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION CONNECTION LIMIT 50 VALID UNTIL '2021-12-20 00:00:00';

COMMENT ON ROLE usr_ebankv2 IS 'Database user, also know as services user';

CREATE DATABASE ebankv2
WITH ENCODING='UTF8'
CONNECTION LIMIT=-1;

COMMENT ON DATABASE ebankv2
IS 'Database for Version 1-Monolithic version';

```

### Drop tables (clean table for dev env)
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

DROP SEQUENCE public.sequence_generator CASCADE;


```

### Truncate/cleanup table (for dev env)
```
TRUNCATE TABLE public.address CASCADE;
TRUNCATE TABLE public.order_details CASCADE;
TRUNCATE TABLE public.order_master CASCADE;
TRUNCATE TABLE public.product CASCADE;
TRUNCATE TABLE public.status CASCADE;
```

### Insert some data
```
insert into status (description, name, register_date, id) values (?, ?, ?, ?)
insert into status (description, name, register_date, id) values (?, ?, ?, ?)

insert into address (city, postal_code, state_province, street_address, user_id, id) values (?, ?, ?, ?, ?, ?)
insert into address (city, postal_code, state_province, street_address, user_id, id) values (?, ?, ?, ?, ?, ?)

```

### Dev mode Database connection from Java-Spring JPA
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
    url: jdbc:postgresql://localhost:5432/ebankv2
    username: usr_ebankv2
    password: ebank1234
```
