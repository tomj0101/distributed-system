# Order System V2

Order management system: Monolithic Enterprise, Java 11+, Spring Boot,JWT Authentication,  PostgreSQL, Redis for Cache, Apache Kafka for Queue, and Elasticsearch for Search-Engine


### Start & stop the pre-install requirements
```
# start
sudo systemctl start redis-server
sudo systemctl start elasticsearch  # Make sure to tunning for less memory consumming (+Bonus)
sudo systemctl start kibana
sudo systemctl start postgresql
# kafka
cd ~/Documents/Software/kafka_2.13-2.8.0
bin/zookeeper-server-start.sh config/zookeeper.properties #start zookeeper
bin/kafka-server-start.sh config/server.properties #start kafka


#stop
sudo systemctl stop redis-server
sudo systemctl stop elasticsearch  
sudo systemctl stop kibana
sudo systemctl stop postgresql

```

## Software Installed & start all the requirements.
```
PostgreSQL VM or Installed:
==========================
cd postgresql-vagrant/
vagrant up

---
sudo apt install postgresql postgresql-contrib
sudo systemctl disable postgresql  #this is a developer machine, I don't want to start postgresql when my OS start
sudo -i -u postgres
psql
SELECT version();
# make sure to create the user here!, this will be your admin user for playground, this one is NOT the application user.
CREATE USER dev WITH PASSWORD 'dev' SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION CONNECTION LIMIT 50 VALID UNTIL '2021-12-20 00:00:00';
\q 
docs: https://www.digitalocean.com/community/tutorials/how-to-install-postgresql-on-ubuntu-20-04-quickstart



Redis:
==========================
sudo apt install redis-server
sudo systemctl restart redis.service
sudo systemctl status redis
sudo systemctl status redis-server
sudo systemctl disable redis-server
redis-cli
>ping
>set test "It's working!"
>get test
# list all the key create in REDIS
KEYS *
# know the type of a key
type test
docs: https://www.digitalocean.com/community/tutorials/how-to-install-and-secure-redis-on-ubuntu-18-04


Elasticsearch:
==========================
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
sudo apt-get install apt-transport-https
echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee /etc/apt/sources.list.d/elastic-7.x.list
sudo apt-get update && sudo apt-get install elasticsearch

sudo systemctl restart elasticsearch.service
sudo systemctl status elasticsearch
http://localhost:9200

# List all the index
curl -XGET 'http://localhost:9200/_cat/indices'
docs: https://www.elastic.co/guide/en/elasticsearch/reference/current/deb.html


Kibana GUI
==========================
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
sudo apt-get install apt-transport-https
echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee -a /etc/apt/sources.list.d/elastic-7.x.list
sudo apt-get update && sudo apt-get install kibana

sudo systemctl restart kibana.service
sudo systemctl status kibana
http://localhost:5601
docs: https://www.elastic.co/guide/en/kibana/current/deb.html


Kafka:
==========================
# Start the ZooKeeper service
# Note: Soon, ZooKeeper will no longer be required by Apache Kafka.
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start the Kafka broker service
bin/kafka-server-start.sh config/server.properties

#create topics
bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092

#write some event
bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
This is my first event
This is my second event

#read the event
bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092

#List all the topics
bin/kafka-topics.sh --list --zookeeper localhost:2181
docs: https://kafka.apache.org/quickstart


#remember to shutdown all the services.
sudo systemctl stop redis-server
sudo systemctl stop elasticsearch
sudo systemctl stop kibana
CTL^C kafka & zookeeper
cd postgresql-vagrant/
vagrant halt
```

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

We use npm scripts and [Webpack][] as our build system.

If you are using redis as a cache, you will have to launch a cache server.
To start your cache server, run:

```
docker-compose -f src/main/docker/redis.yml up -d
```

The cache can also be turned off by adding to the application yaml:

```
spring:
    cache:
        type: none
```

See [here](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html#boot-features-caching-provider-none) for details.

**WARNING**: If you using second level hibernate cache and disabling the spring cache, you have to disable the second level hibernate cache as well since they are using
the same CacheManager.

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

```
./mvnw
npm start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

### PWA Support

ships with PWA (Progressive Web App) support, and it's turned off by default. One of the main components of a PWA is a service worker.

The service worker initialization code is commented out by default. To enable it, uncomment the following code in `src/main/webapp/index.html`:

```html
<script>
  if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('./service-worker.js').then(function () {
      console.log('Service Worker Registered');
    });
  }
</script>
```

Note: [Workbox](https://developers.google.com/web/tools/workbox/) powers service worker. It dynamically generates the `service-worker.js` file.

### Managing dependencies

For example, to add [Leaflet][] library as a runtime dependency of your application, you would run following command:

```
npm install --save --save-exact leaflet
```

To benefit from TypeScript type definitions from [DefinitelyTyped][] repository in development, you would run following command:

```
npm install --save-dev --save-exact @types/leaflet
```

Then you would import the JS and CSS files specified in library's installation instructions so that [Webpack][] knows about them:
Note: There are still a few other things remaining to do for Leaflet that we won't detail here.

For further instructions on how to develop, have a look at [Using in development][].

## Building for production

### Packaging as jar

To build the final jar and optimize the ordersystemv2 application for production, run:

```
./mvnw -Pprod clean verify
```

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

Refer to [Using in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

### Client tests

Unit tests are run by [Jest][]. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

```
npm test
```

### Other tests

Performance tests are run by [Gatling][] and written in Scala. They're located in [src/test/gatling](src/test/gatling).

To use those tests, you must install Gatling from [https://gatling.io/](https://gatling.io/).

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Note: we have turned off authentication in [src/main/docker/sonar.yml](src/main/docker/sonar.yml) for out of the box experience while trying out SonarQube, for real use cases turn it back on.

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```


## Using Vagrant to simplify development (optional)
```
vagrant up

# database credentials
username: usr_ebankv2
password: ebank1234
database: ebankv2

# script for create user and database

CREATE ROLE usr_ebankv2 LOGIN
ENCRYPTED PASSWORD 'md5d1f68dfe6a808773402a65bfcd73e47f'
SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION CONNECTION LIMIT 50 VALID UNTIL '2021-12-20 00:00:00';

COMMENT ON ROLE usr_ebankv2 IS 'Database user, also know as services user';

CREATE DATABASE ebankv2
WITH ENCODING='UTF8'
CONNECTION LIMIT=-1;

COMMENT ON DATABASE ebankv2
IS 'Database for Version 1-Monolithic version';

```

### (+Bonus) Tunning Elasticsearch for use less memory (JVM Options & Heap size)
```
IMPORTANT NOTE: This tunning is for your developer machine, DON't do that in production
sudo vim /etc/elasticsearch/jvm.options
-Xmx2g
docs: https://www.elastic.co/guide/en/elasticsearch/reference/6.8/jvm-options.html
```


[Tech References]

[Java 11]: https://docs.oracle.com/javase/tutorial/
[OpenJDK 11]: https://openjdk.java.net/projects/jdk/11/
[Spring Boot]: https://spring.io/projects/spring-boot
[Spring Data]: https://spring.io/projects/spring-data
[Spring Security]: https://spring.io/projects/spring-security
[Spring REST]: https://spring.io/projects/spring-restdocs#overview

[PostgreSQL]: https://www.postgresql.org/docs/9.6/index.html
[Redis Cache]: https://redis.io/documentation
[Kafka Messages Queue]: https://kafka.apache.org/documentation/
[Elasticsearch]: https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html

[Vagrant]: https://www.vagrantup.com/intro
[Apache Tomcat]: http://tomcat.apache.org/

[ReactJS]: https://reactjs.org/
[Typescript]: https://www.typescriptlang.org/

[gatling]: https://gatling.io/
[node.js]: https://nodejs.org/
[webpack]: https://webpack.github.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: https://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
[online HTML editor]: https://html-online.com/editor/
