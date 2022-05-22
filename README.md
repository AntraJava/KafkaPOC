# KafkaPOC

# Docker Compose
Use Docker Compose (https://docs.docker.com/compose/install/) to start a 3 nodes Kafka Cluster with 1 zookeeper and 1 kafka-ui.
* under the project folder, run `docker-compose up -d` to start the cluster. Without the -d flag, the containers will start in foreground(logs can be seen but will exit after closing the terminal).
* run `docker-compose stop` to stop the cluster but keep the containers.
* run `docker-compose start` to start the cluster again if already exists.
* run `docker-compose down` to stop the cluster and remove all containers, if you decide to delete the cluster.



# `localhost:8888` is the kafka-ui

## You can also check out the Offset Explorer - https://www.kafkatool.com/download.html
