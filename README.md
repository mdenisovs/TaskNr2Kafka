# **READ ME**

Generate customer class:
mvn clean package

Install kafka:
`docker-compose up -d`

**Execute KafkaAvroConsumer then KafkaAvroProducer and see the log terminal.**

#### Test kafka commands:

##### Create a topic

`docker-compose exec kafka kafka-topics.sh --create --topic my-topic-2 --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092`

##### Receive:

`docker-compose exec kafka kafka-console-consumer.sh --topic my-topic-2 --from-beginning --bootstrap-server localhost:9092`

##### Send:

`docker-compose exec kafka kafka-console-producer.sh --topic my-topic-2 --broker-list localhost:9092`