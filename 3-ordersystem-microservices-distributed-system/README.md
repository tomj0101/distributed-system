### Start & stop the pre-install requirements
```
# start
sudo systemctl start redis-server
sudo systemctl start elasticsearch  # Make sure to tunning for less memory consumming (+Bonus)
sudo systemctl start kibana
sudo systemctl start postgresql
sudo systemctl start cassandra
# kafka
cd ~/Documents/Software/kafka_2.13-2.8.0
bin/zookeeper-server-start.sh config/zookeeper.properties #start zookeeper
bin/kafka-server-start.sh config/server.properties #start kafka

#status
sudo systemctl status redis-server
sudo systemctl status elasticsearch  
sudo systemctl status kibana
sudo systemctl status postgresql
sudo systemctl status cassandra

#stop
sudo systemctl stop redis-server
sudo systemctl stop elasticsearch  
sudo systemctl stop kibana
sudo systemctl stop postgresql
sudo systemctl stop cassandra

```