# Microsoft Windows Dev Machine setup (Using Windows Subsystem for Linux - Ubuntu 20.04) All-in-One
The setup of this script is for run in WSL2 version distro, GNU/Linux Ubuntu 20.04, if you are macOS User you can found the equivalence software in .dmg.

Included: User software, tools, script, library, Framework, Programming Language 
### Table of Contents

Table of contents
=================

<!--ts-->
   * [USER-POWER-USER-SOFTWARE](#USER-POWER-USER-SOFTWARE)
      * [Web Browser](#USER-POWER-USER-SOFTWARE)
      * [Video Conferences](#USER-POWER-USER-SOFTWARE)
      * [Video Player](#USER-POWER-USER-SOFTWARE)
   * [WEB-GRAPHIC-DESIGNER-VIDEO-EDITORS-AUDIO](#WEB-GRAPHIC-DESIGNER-VIDEO-EDITORS-AUDIO)
   * [DEV-TOOLS](#DEV-TOOLS)
   * [PROGRAMMING-LANGUAGES](#PROGRAMMING-LANGUAGES)
   * [FULLSTACK-NodeJS-Backend-AND-ReactJS-Frontend-Web-Mobile](#FULLSTACK-NodeJS-Backend-AND-ReactJS-Frontend-Web-Mobile)
   * [DATABASE-SYSTEMS](#DATABASE-SYSTEMS)
   *    [SQL-NOSQL, CACHE, QUEUE, GRAPH](#DATABASE-SYSTEMS)
   * [DEVOPS-AND-CLOUD-ADMIN-AND-LINUX-SYSADMIN](#DEVOPS-AND-CLOUD-ADMIN-AND-LINUX-SYSADMIN)

<!--te-->

WINDOWS-GUI-SOFTWARE
-----
### user-power-user-software
```
Google Chrome web browser:
https://www.google.com/chrome/?brand=CHBD&brand=CHBD&geo=US&gclid=CjwKCAjwrPCGBhALEiwAUl9X03vDSCtTMG2Qqmrkzi8FVe67TjNeCoq2PBJPf6cSyyypMaEHEPKLjhoCuSgQAvD_BwE&gclsrc=aw.ds

Visual Studio Code:
https://code.visualstudio.com/

Microsoft Team for Desktop:
https://www.microsoft.com/en-us/microsoft-teams/download-app

Zoom video conferences Client Desktop:
https://zoom.us/download

Skype for windows:
https://www.skype.com/en/get-skype/

Libreoffice
https://www.libreoffice.org/

Spotify for windows store

VLC:
https://get.videolan.org/vlc/3.0.16/win64/vlc-3.0.16-win64.exe

### web-graphic-designer-videos-editor-audio
Images Manipulator(like photoshop), Vector Images(like Adobe Illustrator, Freehand, CoreDraw), 3D tool like 3D max or Maya
Gimp: https://www.gimp.org/downloads/
inkscape: https://inkscape.org/release/inkscape-1.1/
blender: https://www.blender.org/download/

Video Editor (Like iMovie for apple)
https://shotcut.org/download/
```

### dev-tools
```
README, Live Markdown Editor for github documentation
https://typora.io/#windows

Postman
https://www.postman.com/downloads/


Pomodoro for windows
https://tomighty.github.io/

Create Diagrams
drawio
https://github.com/jgraph/drawio-desktop/releases/tag/v14.6.13

Count line of code using Powershell
dir -Recurse -Include *.ts,*.tsx -Exclude *node_modules* | Get-Content | Measure-Object -Line

# Geany is a lightweight GUI text editor, including basic IDE features. C++
https://www.geany.org/download/releases/


=============== R for Maths and Statistical analysis =============== 
#LaTex in windows
Compiler:
https://miktex.org/download

Editor:
https://www.xm1math.net/texmaker/


# Docker
https://docs.docker.com/docker-for-windows/install/

# VirtualBox
# Open-source hosted hypervisor for x86 virtualization
https://www.virtualbox.org/wiki/Downloads

# Vagrant 
# building and maintaining portable virtual software development environments
https://www.vagrantup.com/downloads

```
USER-POWER-USER-SOFTWARE
-----

```
#check OS Update
sudo apt update -y
apt list --upgradable

#IDE & Text Editor
#nano is installed

```

WEB-GRAPHIC-DESIGNER-VIDEO-EDITORS-AUDIO
-----
```
# Video Editor (Like iMovie for apple)
sudo snap install shotcut --classic

```

DEV-TOOLS
-----

```  
#sudo apt install wget -y  (lubuntu 18.10 come with wget include)

# htop
sudo apt install htop -y

#utility and tools
sudo apt install curl -y 

# Terminal with SuperPowers: 
sudo apt install terminator -y

#Copy text to clipboard
sudo apt install xclip -y

# cloc is Count, or compute differences of, lines of source code and comments.
sudo apt install cloc -y
# cloc .

#Exec Parallel command
sudo apt install parallel -y

#Parsing JSON on CLI 
sudo apt install jq -y


#Install ab - Command Load Test tool with Apache util
sudo apt install apache2-utils -y

#ngrok provides public URL for you locally running a server (Good for quick customers demo.)
sudo snap install ngrok
# docs: https://ngrok.com/docs

#Install Code version control git
###------------------------------------------
sudo apt install git -y
git config --global color.ui true
git config --global user.name "user"
git config --global user.email "name@gmail.com"
ssh-keygen -t rsa -b 4096 -C "name@gmail.com"
git config credential.helper store
git config --global credential.helper store # use --global to set the default preference to all repo.
# Use should also specify caching expire,
git config --global credential.helper 'cache --timeout 7200'

# create security connection with you code repo
# github security connect ssh with linux ubuntu
# https://help.github.com/en/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent
#Generate a Key
cat ~/.ssh/id_rsa.pub
# pase the result in your git setting https://github.com/settings/keys
ssh -T git@github.com

#copy the key
xclip -sel clip < ~/.ssh/id_rsa.pub

#pass in Github key setup
https://github.com/settings/keys

```

PROGRAMMING-LANGUAGES
----

```
=============== C & C++ =============== 
# C/C++ Development
sudo apt install build-essential -y
# (1 hour 30 min for compiled the library)
# (1 hour 30 min for compiled the program)
sudo snap install cmake --classic
# RESTapi framework for C++
# https://microsoft.github.io/cpprestsdk/index.html
sudo apt-get install libcpprest-dev -y
#QT Creator
# Qt: can't find -lGL error.... solution: libgl1-mesa-dev
sudo apt install libgl1-mesa-dev -y

# Geany is a lightweight GUI text editor, including basic IDE features. 
sudo apt install geany -y

#QT OpenSources: Free C++/QT IDE
https://www.qt.io/download-open-source
# Fix QT Editor error
:'
Install mesa fix error working with QT
Error:
:-1: error: cannot find -lGL
:-1: error: collect2: error: ld returned 1 exit status
'
#sudo apt install libglu1-mesa-dev is really install
:'
Install some library fix error working with QT
Error:
Invalid use of incomplete type ‘class QDebug’
'

=============== JAVA =============== 
#Java 11 (default is 11 on 2021.01.01)
sudo apt install default-jre -y && sudo apt install default-jdk -y

#set different java version
: '
$ update-java-alternatives --lis
   java-1.14.0-openjdk-amd64      1411       /usr/lib/jvm/java-1.14.0-openjdk-amd64
   java-1.8.0-openjdk-amd64       1081       /usr/lib/jvm/java-1.8.0-openjdk-amd64
$ sudo update-java-alternatives --set /usr/lib/jvm/java-1.8.0-openjdk-amd64
'

#Create a new directory and go into it mkdir myApp && cd myApp
#Run JHipster and follow instructions on screen jhipster
#Model your entities with JDL Studio and download the resulting jhipster-jdl.jh file
#Generate your entities with jhipster import-jdl jhipster-jdl.jh
#Jhipster - Java Framework
#Install JHipster :
sudo npm install -g generator-jhipster

=============== PYTHON =============== 
sudo apt install python3 -y
sudo apt install python3-pip -y

sudo apt install python -y # still using Python 2 for Linux SysAdmin, Batch task with server without Python3

# python3 -m http.server 8080

#Run Python faster using PyPy: PyPy is a just-in-time compiler while CPython is an interpreter, On average, PyPy is 4.2 times faster than CPython
sudo apt install pypy -y # for Python 3
# pypy3 test.py


```

FULLSTACK-NodeJS-Backend-AND-ReactJS-Frontend-Web-Mobile
-----

```
# nodejs
sudo apt install nodejs -y && \
sudo apt install npm -y

# reload nodejs script, automatically restarting the node application when file changes in the directory are detected.
sudo npm install -g nodemon
# $ nodemon index.js

#typescript lang
sudo npm install -g typescript
# tsc -w

# Reactjs & Firebase
# https://www.npmjs.com/package/generator-react-firebase
sudo npm install -g yo generator-react-firebase npx

# React Native with Expo.io
sudo npm install expo-cli --global
#expo init my-new-project
#cd my-new-project
#expo start

# CLI for firebase developer:
curl -sL https://firebase.tools | bash
# $ firebase login
# $ firebase projects:list

#GCP (Google Cloud platoform)
https://cloud.google.com/sdk/docs/install#deb


#php 7.4 Lang (just in case you want to use, HHVM  virtual machine designed for executing programs written in Hack. )
sudo apt install php-cli -y
# this is part of the facebook world
#doc: https://hhvm.com/


=============== OTHERS LANGUAGES =============== 

#Scala Lang (Install JVM, JDK before Scala)
sudo apt install scala -y

# Install Golang (Look like very good alternative for system programming, faster than Python and easy than C++)
sudo apt install golang -y && \
sudo apt install gccgo -y
# Dev Rest api in Golang
https://www.codementor.io/codehakase/building-a-restful-api-with-golang-a6yivzqdo

#Erlang Lang: WhatsApp secret power is running on Erlang VMs.
sudo snap install erlang --classic

#Haskell Lang
sudo apt install haskell-platform -y
# $ ghci
```

DATABASE-SYSTEMS 
-----
SQL, NOSQL, CACHE, QUEUE, GRAPH

```
# Note: if your role is just dev, probably a Docker file to run your local database will be enough, but if your Role is DBA or System Admin this database, then yes this section is or you.

# Database client and tools:
sudo snap install dbeaver-ce # GUI tools for Oracle, SQL Server, MySQL and Other DB
sudo snap install mysql-workbench-community # Official Admin GUI for MySQL
# Solvind issue: cannot-connect-mysql-workbench-to-mysql-server
1. Go to Ubuntu Software.
2. Search for mysql-workbench and click on Mysql Workbench Community.
3. Click on Permissions.
4. Enable Read, add, change, or remove saved password̀s and ssh-keys (if you are also using ssh keys)


sudo snap install robomongo # GUI for MongoDB
sudo snap install redis-desktop-manager # GUI for Redis 
sudo snap install cass  # GUI for cassandra
sudo apt install pgadmin3 -y # Desktop GUI for PostgreSQL
sudo pip3 install pgadmin4 # Web GUI for PostgreSQL, is a little bit slower.
# $ pgadmin4
# http://127.0.0.1:5050 


#PostgreSQL Server

sudo apt install postgresql postgresql-contrib
sudo systemctl disable postgresql  #this is a developer machine, I don't want to start postgresql when my OS start
sudo -i -u postgres
psql
SELECT version();
# make sure to create the user here!, this will be your admin user for playground, this one is NOT the application user.
CREATE USER dev WITH PASSWORD 'dev' SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION CONNECTION LIMIT 50 VALID UNTIL '2021-12-20 00:00:00';
\q 
docs: https://www.digitalocean.com/community/tutorials/how-to-install-postgresql-on-ubuntu-20-04-quickstart


#Mysql Server

sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
root
Dev20202021  #just sample password
sudo service mysql restart
sudp service mysql status
sudo service mysql disable # you don't want to load your dev machine
sudo mysql -u root
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Dev20202021'; 

#sqlite (and the libsqlite3-dev for C++)

sudo apt install sqlite3 libsqlite3-dev


# Oracle Database Server

# Download Oracle VM Virtual Appliances or OVA from Oracle website.


#sql server

sudo apt install curl -y
sudo curl https://packages.microsoft.com/keys/microsoft.asc | sudo apt-key add -
sudo curl https://packages.microsoft.com/config/ubuntu/16.04/prod.list | sudo tee /etc/apt/sources.list.d/msprod.list
sudo apt-get update -y
sudo apt install libcurl3 -y
sudo apt-get install -y mssql-server
sudo apt-get install mssql-tools unixodbc-dev
echo 'export PATH="$PATH:/opt/mssql-tools/bin"' >> ~/.bash_profile
echo 'export PATH="$PATH:/opt/mssql-tools/bin"' >> ~/.bashrc
source ~/.bashrc
sudo /opt/mssql/bin/mssql-conf setup
sudo systemctl status mssql-server
#systemctl disable mssql-server

#sqlcmd -S localhost -U SA -P 'Tecno12345'
#curl -XGET http://localhost:9200/_stats
#curl -XGET http://localhost:9200/_cat/indices?v

#error: sqlcmd -S localhost -U SA -P 'Tecno12345'
#    Sqlcmd: Error: Microsoft ODBC Driver 17 for SQL Server : Login failed for user 'sa'. Reason: Server is in script upgrade mode. Only administrator can connect at this time..
#Soluction:
#sudo /opt/mssql/bin/mssql-conf set sqlagent.enabled true
#sudo systemctl restart mssql-server

# MongoDB (NoSQL, Document Database) 

sudo mkdir /data
sudo mkdir /data/db
sudo chmod 777 -R /data
sudo apt install mongodb -y
#start by default with the OS 
#sudo systemctl stop mongodb
#sudo systemctl start mongodb
sudo systemctl disable mongodb
sudo systemctl status mongodb
#GUI: download last version of MongoDB Compass

#cassandra (Wide-Column Database)

echo "deb http://www.apache.org/dist/cassandra/debian 311x main" | sudo tee -a /etc/apt/sources.list.d/cassandra.sources.list
curl https://www.apache.org/dist/cassandra/KEYS | sudo apt-key add -
--- OR ---
sudo apt install apt-transport-https
wget -q -O - https://www.apache.org/dist/cassandra/KEYS | sudo apt-key add -
sudo sh -c 'echo "deb http://www.apache.org/dist/cassandra/debian 311x main" > /etc/apt/sources.list.d/cassandra.list'

sudo apt-get update	
sudo apt install cassandra
#service cassandra start
#service cassandra stop
sudo systemctl start cassandra
sudo systemctl stop cassandra
sudo systemctl disable cassandra
sudo systemctl status cassandra
#cqlsh localhost 9042
cqlsh -u cassandra -p cassandra  
cqlsh -u root_user -p cassandra # login as superuser.
cqlsh> SELECT cql_version FROM system.local;

#create a cassandra keyspace:
CREATE KEYSPACE ebank_data
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

# list keyspaces
SELECT * FROM system_schema.keyspaces;

USE ebank_data;
INSERT INTO emp(emp_id, emp_city,emp_name,emp_phone,emp_sal) VALUES (1, 'LA','Tom',8889996666,1234);
CREATE TABLE emp(
   emp_id int PRIMARY KEY,
   emp_name text,
   emp_city text,
   emp_sal varint,
   emp_phone varint
   );
select * from emp;


#tunning cassandra to use less memory in dev machine 
sudo nano /etc/cassandra/cassandra-env.sh
MAX_HEAP_SIZE="256M" #1G
HEAP_NEWSIZE="128M"


# Redis (Cache, in-memory Key-Value Store)

sudo apt install redis-server
sudo systemctl restart redis.service
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


# Apache Kafka (Queue)
# kafka install doc: https://kafka.apache.org/quickstart
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



# Elasticsearch (full-text Search Engine)
# Install ELK Stack: Elasticsearch + Kibana + Logstash.
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
sudo apt-get install apt-transport-https
echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee /etc/apt/sources.list.d/elastic-7.x.list
sudo apt-get update && sudo apt-get install elasticsearch

sudo systemctl restart elasticsearch.service
sudo systemctl status elasticsearch
http://localhost:9200

### (+Bonus) Tunning Elasticsearch for use less memory (JVM Options & Heap size)

IMPORTANT NOTE: This tunning is for your developer machine, DON't do that in production
sudo vim /etc/elasticsearch/jvm.options
-Xmx2g
docs: https://www.elastic.co/guide/en/elasticsearch/reference/6.8/jvm-options.html



# List all the index
curl -XGET 'http://localhost:9200/_cat/indices'
docs: https://www.elastic.co/guide/en/elasticsearch/reference/current/deb.html


# Kibana GUI
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
sudo apt-get install apt-transport-https
echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee -a /etc/apt/sources.list.d/elastic-7.x.list
sudo apt-get update && sudo apt-get install kibana

sudo systemctl restart kibana.service
sudo systemctl status kibana
http://localhost:5601
docs: https://www.elastic.co/guide/en/kibana/current/deb.html



# Neo4J (Graph Database)
sudo wget -O - https://debian.neo4j.org/neotechnology.gpg.key | sudo apt-key add -
sudo echo 'deb https://debian.neo4j.org/repo stable/' | sudo tee -a /etc/apt/sources.list.d/neo4j.list
sudo apt-get update
sudo apt install neo4j
neo4j --version
sudo nano /etc/neo4j/neo4j.conf
dbms.security.auth_enabled=false

sudo nano /etc/neo4j/neo4j.conf
dbms.connectors.default_listen_address=0.0.0.0

# The default password for the neo4j user is neo4j
sudo systemctl restart neo4j
sudo systemctl disable neo4j

Open: http://localhost:7474/
docs: https://aster.cloud/2019/07/16/how-to-neo4j-installation-and-configuration-in-ubuntu/

```


DEVOPS-AND-CLOUD-ADMIN-AND-LINUX-SYSADMIN
-----

```
# CLI for Cloud provider:
#firebase
curl -sL https://firebase.tools | bash
# $ firebase login
# $ firebase projects:list

#GCP (Google Cloud platoform)
https://cloud.google.com/sdk/docs/install#deb

#AWS (Amazon Web Services)
sudo apt install awscli -y

#Azure (Microsoft Cloud)
curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash

#Kubernetes: Open-source container-orchestration system for automating computer application deployment, scaling, and management. 
#https://kubernetes.io/docs/tasks/tools/install-kubectl/
sudo apt install kubectl -y
kubectl version

# Console
$ kubectl get po -A
$ kubectl get node
# create a pod
$ kubectl run app1 --images=tomj0101/app1:latest -l visualize=true,run=app1
# scale the pod
$ kubectl scale rc app1 --replicas=2
rc= replication controller
$ kubectl get pods
#enter into the pods, each pods have different ip address
kubectl exec -it app-xytrse /bin/bash
hostname -i

#create a service in-front.
$ kubectl expose rc app1 --port=8080 --type=LoadBalancer
$ kubectl describe service app1
$ kubectl delete app1

#rolling update
$ kubectl rolling-update app1 --images=tomj0101/app1:v3 --update-period=5s
sudo s

#restart and check the memory and active process, is important don't have any service running, remember is your developer laptop, NOT A PROD SERVER.
reboot

# Web GUI for the server 
sudo apt install cockpit -y
```


### Credits
Author: Tom