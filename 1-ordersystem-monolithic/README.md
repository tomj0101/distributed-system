# Order System

Order management system: Monolithic, Java 11+, Spring Boot, HTTP Session Authentication (stateful, default Spring Security mechanism),  PostgreSQL

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

To build the final jar and optimize the ordersystem application for production, run:

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

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Vagrant to simplify development and run postgreSQL on a VM(optional)
```
vagrant up

# database credentials
username: usr_ebank
password: ebank1234
database: ebankv1

# script for create user and database

CREATE ROLE usr_ebank LOGIN
ENCRYPTED PASSWORD 'md5d1f68dfe6a808773402a65bfcd73e47f'
SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION CONNECTION LIMIT 50 VALID UNTIL '2021-12-20 00:00:00';

COMMENT ON ROLE usr_ebank IS 'Database user, also know as services user';

CREATE DATABASE ebankv1
WITH ENCODING='UTF8'
CONNECTION LIMIT=-1;

COMMENT ON DATABASE ebankv1
IS 'Database for Version 1-Monolithic version';

```

## You can install and run postgreSQL if you want
```
Installation here: distributed-system/0-dev-machine-setup/README.md

sudo systemctl status postgresql # check the service status if is not active, then start the service
sudo systemctl start postgresql  # start postgresql service 

```

[Tech References]

[Java 11]: https://docs.oracle.com/javase/tutorial/
[OpenJDK 11]: https://openjdk.java.net/projects/jdk/11/
[Spring Boot]: https://spring.io/projects/spring-boot
[Spring Data]: https://spring.io/projects/spring-data
[Spring Security]: https://spring.io/projects/spring-security
[Spring REST]: https://spring.io/projects/spring-restdocs#overview

[PostgreSQL]: https://www.postgresql.org/docs/9.6/index.html
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

### This is how look when you run the monolithic
![Monolithic-Screenshot-1](/docs/screenshot/Monolithic-Screenshot-1.png)
![Monolithic-Screenshot-2](/docs/screenshot/Monolithic-Screenshot-2.png)
![Monolithic-Screenshot-3](/docs/screenshot/Monolithic-Screenshot-3.png)