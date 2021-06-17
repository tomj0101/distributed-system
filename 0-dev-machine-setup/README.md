# Dev Machine setup (GNU/Linux Ubuntu 20.04)
The setup of this script is for run in debian version of Linux, GNU/Linux Ubuntu 20.04, if you are macOS User or Windows User you can found the equivalence software in .dmg or .exe with a wizard options.

###  Included: tools, script, library, Framework, Programming Language 
```

#check OS Update
sudo apt update -y:
apt list --upgradable

# desktop environment LXDE / LXQt for old laptop
sudo apt install lxqt -y

# cloc is Count, or compute differences of, lines of source code and comments.
sudo apt install cloc -y
# cloc .

#VIM editor
sudo apt install vim -y

#Chromium web browser
sudo snap install chromium

# Pomodoro for Work Session, Training, Conferences, Practice my Algo skill (pomotroid 0.12.0 from Dan Ryan (techalchemy))
sudo snap install pomotroid

# Audio Recording and Audio edition software
sudo apt install audacity


# Some Chrome issue can happens after update Ubuntu is display a really bad quality fonts / big bold fonts, if this happen just do this.
sudo apt install gnome-tweak-tool -y
# open the tweaks application > click on Fonts > Antialiasing > choose Standard (grayscale)

#spotify
sudo snap install spotify

#LaTex in Linux Ubuntu
sudo apt-get install texlive-full -y  #compiler
sudo apt install texmaker -y #editor

#Google Chrome web browser
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install ./google-chrome-stable_current_amd64.deb
rm google-chrome-stable_current_amd64.deb

# Improve chrome web browser performace (Chrome Running Slow)
# chrome://settings/?search=hardware 
# Disabling "Use hardware acceleration when available" in chrome://settings/?search=hardware fixed the problem.

# Terminal with SuperPowers: # this one is good But I'm trying to used the default terminal
sudo apt install terminator -y

#Copy text to clipboard
sudo apt install xclip -y

# Create Diagrams
sudo snap install drawio

#Exec Parallel command
sudo apt install parallel -y

#Parsing JSON on CLI 
sudo snap install jq

# if you want to use ```ifconfig``` and not ```ip add show```
sudo apt install net-tools

#ngrok provides public URL for you locally running a server
sudo snap install ngrok

# Enabling SSH on Ubuntu (if you thinking to access remote to this computer)
sudo apt update
sudo apt install openssh-server -y
sudo systemctl status ssh
sudo ufw allow ssh

#config git
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
###------------------------------------------

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


#Java 11 (default is 11 on 2021.01.01)
sudo apt install default-jre -y && sudo apt install default-jdk -y

#set different java version
: '
$ update-java-alternatives --lis
   java-1.14.0-openjdk-amd64      1411       /usr/lib/jvm/java-1.14.0-openjdk-amd64
   java-1.8.0-openjdk-amd64       1081       /usr/lib/jvm/java-1.8.0-openjdk-amd64
$ sudo update-java-alternatives --set /usr/lib/jvm/java-1.8.0-openjdk-amd64
   update-alternatives: error: no alternatives for jaotc
   update-alternatives: error: no alternatives for jdeprscan
   update-alternatives: error: no alternatives for jhsdb
   update-alternatives: error: no alternatives for jimage
   update-alternatives: error: no alternatives for jlink
   update-alternatives: error: no alternatives for jmod
   update-alternatives: error: no alternatives for jshell
   update-alternatives: error: no alternatives for mozilla-javaplugin.so
   update-java-alternatives: plugin alternative does not exist: /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/amd64/IcedTeaPlugin.so
'

#maven
sudo apt install maven -y

#eclipse
sudo snap install eclipse --classic

#pycharm-community
sudo snap install pycharm-community --classic

#IntelliJ IDEA Community (Java IDE)
sudo snap install intellij-idea-community --classic

# Geany is a lightweight GUI text editor, including basic IDE features. 
sudo apt install geany -y

#Install ab - Load Test tool with Apache util
sudo apt install apache2-utils -y

# htop
sudo apt install htop -y

# Install video and AudiLib utils
sudo apt install ffmpeg -y &&
sudo apt install lame -y



# Video Editor (Like iMovie for apple)
sudo snap install shotcut --classic

#utility and tools
sudo apt install curl -y 

#font manager for design
sudo apt update && sudo apt -y install font-manager 

#Images Manipulator(like photoshop), Vector Images(like Adobe Illustrator, Freehand, CoreDraw), 3D tool like 3D max or Maya
sudo apt install gimp -y && sudo apt install inkscape -y && sudo apt install blender -y

# Developer tools *******
# note: GCC is install by default in the system!
# 2)  install development tools (like Make,C, C++ other lib)
# To install development tools, run:
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

#some opensource project use this platform 
#like hotspot http://openjdk.java.net/groups/hotspot/
sudo apt install mercurial -y

# Enabling SSH
sudo apt install openssh-server
sudo systemctl status ssh
sudo ufw allow ssh


# ==================== Full-stack Devloper ====================
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

#Jhipster - Java Framework
#Install JHipster :
sudo npm install -g generator-jhipster

#Create a new directory and go into it mkdir myApp && cd myApp
#Run JHipster and follow instructions on screen jhipster
#Model your entities with JDL Studio and download the resulting jhipster-jdl.jh file
#Generate your entities with jhipster import-jdl jhipster-jdl.jh


# React Native with Expo.io
sudo npm install expo-cli --global
#expo init my-new-project
#cd my-new-project
#expo start


#Google Fluter Mobile develoment with Dart
sudo snap install flutter --classic

#Install Android Studio
sudo snap install android-studio --classic

#Erlang Lang
sudo snap install erlang --classic


#php 7.4 Lang (just in case you want to use, HHVM  virtual machine designed for executing programs written in Hack. )
sudo apt install php-cli -y

#Scala Lang
sudo apt install scala -y

#Swift Lang
sudo apt install swift -y

#Kotlin Lang
sudo snap install kotlin --classic

#Haskell Lang
sudo apt install haskell-platform -y
# $ ghci


# fix any broken packages after install node and npm: very important
#########################################################################
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


# Install R-lang
sudo apt install r-base -y
# $ R

# Install RStudio (check the last version before instsll)

wget https://download1.rstudio.org/desktop/bionic/amd64/rstudio-1.3.1093-amd64.deb
sudo dpkg -i rstudio-1.3.1093-amd64.deb
sudo apt --fix-broken install -y #the first try don' work
sudo dpkg -i rstudio-1.3.1093-amd64.deb
rm rstudio-1.3.1093-amd64.deb


# Communitation tools
# zoom
wget https://zoom.us/client/latest/zoom_amd64.deb
sudo apt install ./zoom_amd64.deb -y
sudo apt --fix-broken install -y #the first try don' work
sudo apt install ./zoom_amd64.deb -y
rm zoom_amd64.deb

sudo snap install skype --classic && \
sudo snap install slack --classic && \
sudo snap install teams-for-linux

#Microsoft Visual Studio Code
sudo snap install code --classic

#postman
sudo snap install postman
#$ postman


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
# Note that this will expect a postgres user with the same username
# as your app, you may need to edit config/database.yml to match the
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


# Python
sudo apt install python python3 -y && \
sudo apt install python3-pip -y && \
sudo apt install pypy -y && \
pip3 install Flask
python3 -m http.server 8080



# Install Golang
sudo apt install golang -y && \
sudo apt install gccgo -y

# rest api
https://www.codementor.io/codehakase/building-a-restful-api-with-golang-a6yivzqdo


# Database tools:
sudo snap install dbeaver-ce && \
sudo snap install mysql-workbench-community && \
sudo snap install robomongo && \
sudo snap install redis-desktop-manager && \  
sudo snap install cass && \ # GUI for cassandra
pip3 install pgadmin4



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


#Utils
#download youtube video
sudo snap install ktube-media-downloader

#financial
sudo snap install gnucash-jz

# know the weather
sudo snap install wethr

# send desktop notification
#notify-send <title> <`command`>
notify-send "Weather" "`wethr`"

# remove the weather city for privacity
echo wethr | grep -Po '(?<=Linden, \.).*'

# Security
sudo snap install nmap



#restart and check the memory, is important don't have any service running, remember is your developer laptop, NOT A PROD SERVER.
reboot




#######################################################################################################

#Kubernetes
##########################  DevOps, make sure you have a least 16GB of RAM, better if you have 32GB of RAM and SSD
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

######################DON'T INSTALL THIS PACKAGES, JUST FOR REFERENCES################################


#version control (lubuntu 18.10 come with git include)
#apt-get install git -y


#utility and tools
#sudo apt install wget -y  (lubuntu 18.10 come with wget include)

# Install video and AudiLib
#sudo apt install vlc -y (lubuntu 18.10 come with wget include)

#Run the other DB and tools like services using docker.

#C/C++ (lubuntu 18.10 come with gcc and make include)
#apt-get install build-essential -y

# mongo db
mkdir /data &&
mkdir /data/db &&
chmod 777 -R /data
apt install mongodb -y
#by default comes start
#sudo systemctl stop mongodb
#sudo systemctl start mongodb
systemctl disable mongodb
systemctl status mongodb
#GUI: download last version of MongoDB Compass

#postgresql
#https://medium.com/coding-blocks/creating-user-database-and-adding-access-on-postgresql-8bfcd2f4a91e
apt install postgresql -y
#/usr/lib/postgresql/10/bin/pg_ctl -D /var/lib/postgresql/10/main -l logfile start
systemctl disable postgresql
systemctl status postgresql
systemctl restart postgresql
#GUI tool: install pgadmin3
#apt install pgadmin3 
sudo su postgres

#mysql
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
root
ServerPasswd123J$1  #just sample password
service mysql restart
service mysql status
sudo mysql -u root
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'ServerPasswd123J$1'; 

#cassandra
echo "deb http://www.apache.org/dist/cassandra/debian 311x main" | sudo tee -a /etc/apt/sources.list.d/cassandra.sources.list
curl https://www.apache.org/dist/cassandra/KEYS | sudo apt-key add -
sudo apt-get update	
apt install cassandra
#service cassandra start
#service cassandra stop
systemctl disable cassandra
systemctl status cassandra
#cqlsh localhost

#sql server

apt install curl -y
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




#IDE & Text Editor (lubuntu 18.10 come with gcc and make include)
#apt install nano -y 


#libreoffice (is included)
#sudo apt install libreoffice -y (lubuntu 18.10 come with libreoffice include)



#after install Eclipse, install the Java, C++ pluggin 
#and install Visual Studio Code pluggin like react, angular, node, javascript, java, python, ruby, go, c/c++, linux, git
#google webdesigner
#https://www.google.com/webdesigner/

#create some shortcut for eclipse and other app
# lxshortcut -o ~/Desktop/eclipse-jee
#  lxshortcut -o ~/Desktop/eclipse-java
#  lxshortcut -o ~/Desktop/eclipse-cpp
#  lxshortcut -o ~/Desktop/postman
#  lxshortcut -o ~/Desktop/dbeaver-ce



# Install ELK Stack
#https://www.howtoforge.com/tutorial/ubuntu-elastic-stack/

: '
Tools no install in this script
dbeaver-ce
   excellent when you need to management different RDBMS with just one IDE or even NoSQL(note: CE/Community edition have some limitation for NoSQL DB)
webdesigner
'



# How-do-i-set-profile-picture-on-lubuntu
: '
Start
Under System Tools > select LightDM GTK + Greeting Settings
Click on the default avartar icon at the bottom of the popup window to choose:
change icon, or
select file
I got my icons from flaticon.com (not in anyway affiliated, just a happy user).
'


# Ubuntu Server Configuration 
# ===================================================================
# How to disable/enable GUI on boot in Ubuntu 20.04
# https://linuxconfig.org/how-to-disable-enable-gui-on-boot-in-ubuntu-20-04-focal-fossa-linux-desktop
sudo systemctl set-default multi-user
gnome-session-quit
reboot
startx

# server web ui
sudo apt install cockpit
```


### Credits
Autthor: Tom