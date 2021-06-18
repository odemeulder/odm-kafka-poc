# Understanding Kafka

## Installing and running Kafka

Follow instructions here: https://kafka.apache.org/quickstart

Basically
1. download and unzip
2. in one tab run zookeeper
3. in another tab start kafka server

Create a topic:
```
bin/kafka-topics.sh --create --topic sub-events --bootstrap-server localhost:9092
```

## Running two applications

1. Producer
2. Consumer

### Producer application

```bash
cd producer
./gradlew bootRun
# other tab
curl -X POST -d '{"id": 2, "user_id": 23, "product_id": 3}' -H 'Content-Type: application/json' http://localhost:8080/sub
```

This will start a webserver with one endpoint. Every time a post is made to the endpoint, a message is dropped on the topic.

### Consumer application

```bash
cd consumer
./gradlew bootRun
```

The consumer application implements a listener that listens for messages on a topic and writes them to the console.
