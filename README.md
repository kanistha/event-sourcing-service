###### Install Kafka on MAC

``brew install kafka``

###### Start Zookeeper
 - To have launched start zookeeper now and restart at login:
 
     ``brew services start zookeeper``

 - Or, if you don't want/need a background service you can just run:
     
     ``zkServer start``

###### Start Kafka server

 - To have launched start kafka now and restart at login:
     
     ``brew services start kafka``

 - Or, if you don't want/need a background service you can just run:
  
     ``zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties``