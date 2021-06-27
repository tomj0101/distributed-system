# Dev Machine setup (GNU/Linux Ubuntu 20.04) All-in-One
The setup of this script is for run in debian version of Linux, GNU/Linux Ubuntu 20.04, if you are macOS User or Windows User you can found the equivalence software in .dmg or .exe with a wizard options.

###  Included: User software, tools, script, library, Framework, Programming Language 
```
#check OS Update
sudo apt update -y
apt list --upgradable

===========================================================
=============== USER & POWER USER SOFTWARE ===============  

#Chromium web browser
sudo snap install chromium

#Google Chrome web browser
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install ./google-chrome-stable_current_amd64.deb
rm google-chrome-stable_current_amd64.deb

# Some Chrome issue can happens after update Ubuntu is display a really bad quality fonts / big bold fonts, if this happen just do this.
sudo apt install gnome-tweak-tool -y
# open the tweaks application > click on Fonts > Antialiasing > choose Standard (grayscale)

# Improve chrome web browser performace (Chrome Running Slow)
# chrome://settings/?search=hardware 
# Disabling "Use hardware acceleration when available" in chrome://settings/?search=hardware fixed the problem.

# Microsoft Team: chat and video conferences
sudo snap install teams-for-linux

# zoom video conferences
wget https://zoom.us/client/latest/zoom_amd64.deb
sudo apt install ./zoom_amd64.deb -y
sudo apt --fix-broken install -y #if the first try don' work
sudo apt install ./zoom_amd64.deb -y
rm zoom_amd64.deb

#skype video conferences
sudo snap install skype --classic

# slack video conferences
sudo snap install slack --classic

#IDE & Text Editor (lubuntu 18.10 come with gcc and make include)
#apt install nano -y 

#libreoffice (is included)
#sudo apt install libreoffice -y (lubuntu 18.10 come with libreoffice include)

#spotify
sudo snap install spotify

#Video Player
sudo apt install vlc -y 

#Utils
#download youtube video
sudo snap install ktube-media-downloader

#financial application
sudo snap install gnucash-jz

# know the weather
sudo snap install wethr

# send desktop notification
#notify-send <title> <`command`>
notify-send "Weather" "`wethr`"

#create some shortcut for eclipse and other app
# lxshortcut -o ~/Desktop/eclipse-jee
#  lxshortcut -o ~/Desktop/eclipse-java
#  lxshortcut -o ~/Desktop/eclipse-cpp
#  lxshortcut -o ~/Desktop/postman
#  lxshortcut -o ~/Desktop/dbeaver-ce

# desktop environment LXDE / LXQt for old laptop or just get better performace removing GUI effects.
sudo apt install lxqt -y

==============================================================================
=============== WEB & GRAPHIC DESIGNER + VIDEO EDITORS + AUDIO ==============
#Images Manipulator(like photoshop), Vector Images(like Adobe Illustrator, Freehand, CoreDraw), 3D tool like 3D max or Maya
sudo apt install gimp -y && sudo apt install inkscape -y && sudo apt install blender -y

# Video Editor (Like iMovie for apple)
sudo snap install shotcut --classic

#font manager for design
sudo apt update && sudo apt -y install font-manager 

# Audio Recording and Audio edition software
sudo apt install audacity

# Install video and AudiLib utils (command line different video format conversion)
sudo apt install ffmpeg -y &&
sudo apt install lame -y

==============================================================================
=============== DEV TOOLS ===============  
#README, Live Markdown Editor for github documentation
sudo apt install retext


#postman
sudo snap install postman
#$ postman

#sudo apt install wget -y  (lubuntu 18.10 come with wget include)

# htop
sudo apt install htop -y

#utility and tools
sudo apt install curl -y 

# Terminal with SuperPowers: 
sudo apt install terminator -y

#Copy text to clipboard
sudo apt install xclip -y

# Pomodoro for Work Session, Training, Conferences, Practice my Algo skill (pomotroid 0.12.0 from Dan Ryan (techalchemy))
sudo snap install pomotroid

# Create Diagrams
sudo snap install drawio

# cloc is Count, or compute differences of, lines of source code and comments.
sudo apt install cloc -y
# cloc .

#Exec Parallel command
sudo apt install parallel -y

#Parsing JSON on CLI 
sudo snap install jq


#Install ab - Command Load Test tool with Apache util
sudo apt install apache2-utils -y

# if you want to use ```ifconfig``` and not ```ip add show```
sudo apt install net-tools

#ngrok provides public URL for you locally running a server (Good for quick customers demo.)
sudo snap install ngrok
# docs: https://ngrok.com/docs

# Enabling SSH on Ubuntu (if you thinking to access remote to this computer)
sudo apt update
sudo apt install openssh-server -y
sudo systemctl status ssh
sudo ufw allow ssh

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

==============================================================================
=============== PROGRAMMING LANGUAGES =============== 

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

#VIM editor
sudo apt install vim -y

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

#maven
sudo apt install maven -y

#IntelliJ IDEA Community (Java IDE)
sudo snap install intellij-idea-community --classic

#eclipse
sudo snap install eclipse --classic

#some opensource project use this platform 
#like hotspot http://openjdk.java.net/groups/hotspot/
sudo apt install mercurial -y

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
sudo apt install pypy -y  # for Python 2.7
sudo apt install pypy3 -y # for Python 3
# pypy3 test.py

#IDE pycharm-community
sudo snap install pycharm-community --classic

=============== FULLSTACK: NodeJS Backend & ReactJS Frontend, Web & Mobile =============== 

#IDE: Microsoft Visual Studio Code (Make sure install the extension for your languages)
sudo snap install code --classic

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

#google webdesigner
#https://www.google.com/webdesigner/

# CLI for firebase developer:
curl -sL https://firebase.tools | bash
# $ firebase login
# $ firebase projects:list

#GCP (Google Cloud platoform)
https://cloud.google.com/sdk/docs/install#deb

#Google Fluter Mobile develoment with Dart
sudo snap install flutter --classic

#Install Android Studio
sudo snap install android-studio --classic

#php 7.4 Lang (just in case you want to use, HHVM  virtual machine designed for executing programs written in Hack. )
sudo apt install php-cli -y
# this is part of the facebook world
#doc: https://hhvm.com/

#Swift Lang
sudo apt install swift -y

#Kotlin Lang
sudo snap install kotlin --classic

=============== R for Maths and Statistical analysis =============== 
#LaTex in Linux Ubuntu 
# (You can create from scientists papers to your resume, 
# also when you need to create PDF output in RStudio you will need this compiler)
sudo apt-get install texlive-full -y  #compiler
sudo apt install texmaker -y #editor

# Install R-lang
sudo apt install r-base -y
# $ R

# Install RStudio (check the last version before install, also install Latex before, also the RStudio version change a lot, make sure to install the last version)

wget https://download1.rstudio.org/desktop/bionic/amd64/rstudio-1.3.1093-amd64.deb
sudo dpkg -i rstudio-1.3.1093-amd64.deb
sudo apt --fix-broken install -y #the first try don' work
sudo dpkg -i rstudio-1.3.1093-amd64.deb
rm rstudio-1.3.1093-amd64.deb

=============== RUBY & RUBY ON RAILS  =============== 
# Note: If you will work with Ruby on Rails in a greenfield project, check the last version for use the best features

# Ruby with snap
# sudo snap install ruby --classic

#RubyOnRails (Manual) 
#############
https://gorails.com/setup/ubuntu/20.10
Ruby 2.7.2
Rails 6.0.3.4

#Pre-package before install Ruby
:'
sudo apt install curl
curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
echo "deb https://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list
sudo apt-get update
sudo apt-get install git-core zlib1g-dev build-essential libssl-dev libreadline-dev libyaml-dev libsqlite3-dev sqlite3 libxml2-dev libxslt1-dev libcurl4-openssl-dev software-properties-common libffi-dev nodejs yarn
'

#Installing Ruby
:'
cd
git clone https://github.com/rbenv/rbenv.git ~/.rbenv
echo 'export PATH="$HOME/.rbenv/bin:$PATH"' >> ~/.bashrc
echo 'eval "$(rbenv init -)"' >> ~/.bashrc
exec $SHELL
git clone https://github.com/rbenv/ruby-build.git ~/.rbenv/plugins/ruby-build
echo 'export PATH="$HOME/.rbenv/plugins/ruby-build/bin:$PATH"' >> ~/.bashrc
exec $SHELL
rbenv install 2.7.2
rbenv global 2.7.2
ruby -v
'

#install the bundler
:'
gem install bundler
'

#installing rails
:'
gem install rails -v 6.0.3.4
rbenv rehash
rails -v
'

#setting database for Rails mysql and postgres
:'
sudo apt-get install mysql-server mysql-client libmysqlclient-dev
sudo apt install postgresql-11 libpq-dev
'

#final step created application
:'
#### If you want to use SQLite (not recommended)
rails new myapp
#### If you want to use MySQL
rails new myapp -d mysql
#### If you want to use Postgres
# Note that this will expect a postgres ushttp://127.0.0.1:5050 /database.yml to match the
# user you created earlier
rails new myapp -d postgresql
# Move into the application directory
cd myapp
# If you setup MySQL or Postgres with a username/password, modify the
# config/database.yml file to contain the username/password that you specified
# Create the database
rake db:create
rails server
'

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

==============================================================================
=============== DATABASE SYSTEMS (SQL, NOSQL, CACHE, QUEUE, GRAPH) =============== 
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


==============================================================================
=============== DEVOPS + CLOUD ADMIN + LINUX SYSADMIN =============== 

# fix any broken packages after install node and npm: very important
sudo apt --fix-broken install

# Docker
# instruction: https://docs.docker.com/install/linux/docker-ce/ubuntu/#install-docker-ce-1
sudo apt-get update && \
sudo mkdir "/home/developer/.docker" && \
sudo apt-get install \
    apt-transport-https \
    ca-certificates -y \
    curl
    software-properties-common
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
sudo apt-get update -y && \
sudo apt install docker.io -y

# add permission to your user
sudo groupadd docker && \
sudo usermod -aG docker developer  && \
sudo usermod -aG docker developer  && \
sudo chown "$developer":"developer" /home/developer/.docker -R  && \
sudo chmod g+rwx "/home/developer/.docker" -R  && \
sudo chown developer:developer /home/developer/.docker -R  && \
sudo chmod g+rwx "/home/developer/.docker" -R

#start
sudo systemctl enable docker &&
sudo chkconfig docker on &&
sudo systemctl restart docker

#by default comes start
#sudo systemctl stop docker
#sudo systemctl disable docker

# How-to-fix-docker-got-permission-denied-while-trying-to-connect-to-the-docker-daemon-socket
# Create the docker group.
sudo groupadd docker
# Add your user to the docker group.
sudo usermod -aG docker ${USER}
# You would need to loog out and log back in so that your group membership is re-evaluated or type the following command:
su -s ${USER}
# Verify that you can run docker commands without sudo.
docker run hello-world

# fix
sudo chown "$USER":"$USER" /home/"$USER"/.docker -R
sudo chmod g+rwx "$HOME/.docker" -R
sudo chmod 666 /var/run/docker.sock
sudo systemctl restart docker
docker ps #should return good info.


#docker compose
sudo apt install docker-compose -y

# VirtualBox
# Open-source hosted hypervisor for x86 virtualization
sudo apt install virtualbox -y

# Vagrant 
# building and maintaining portable virtual software development environments
sudo apt install vagrant -y

# Infrastructure as code for AWS, GCP and Azure
#sudo snap install terraform
snap install --candidate terraform

# ansible: IT Automation
sudo apt install ansible -y

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

# Security
sudo snap install nmap

#Kubernetes: Open-source container-orchestration system for automating computer application deployment, scaling, and management. 
#https://kubernetes.io/docs/tasks/tools/install-kubectl/
sudo snap install kubectl --classic
kubectl version

#helm: The package manager for Kubernetes
sudo snap install helm --classic

#https://minikube.sigs.k8s.io/docs/start/
minikube status

# Web UI (Dashboard)
# https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/
$ kubectl create -f https://raw.githubusercontent.com/kubernetes/dashboard/master/aio/deploy/recommended/kubernetes-dashboard.yaml
#$ kubectl proxy -p 8080
kubectl proxy

#Create a user and get tokens for the UI Dashboard
https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md

# Deploying the Dashboard U
# https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/
# URL For you local UI
http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/


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

# Ubuntu Server Configuration 
# ===================================================================
# How to disable/enable GUI on boot in Ubuntu 20.04
# https://linuxconfig.org/how-to-disable-enable-gui-on-boot-in-ubuntu-20-04-focal-fossa-linux-desktop
sudo systemctl set-default multi-user
gnome-session-quit
reboot
startx

# Web GUI for the server 
sudo apt install cockpit -y
```


### Credits
Author: Tom
