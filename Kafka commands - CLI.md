## Kafka commands - CLI

### Kafka Command - kafka-topics

The first command on the command line interface is to know how to create a kafka-topic. This can be achieved using the kafka utility `kafka-topics`. The `kafka-topic` command --help gives an extensive list of parameters that can be used to create a topic in the cluster. However the bare minumum configuration required to create a topic in a cluster is

##### Add topics.

```bash
sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --topic first_topic --create --partitions 3 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic first_topic.
```

Here the replication factor is set to 1. This is because our cluster for development has only 1 broker. Kafka doesn't allow to set a replication factor more than the number of available brokers.

##### List topics.

Now its time to verify that topic created in the previous step. To get the list of topics in the kafka-cluster use the command

```bash
sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --list|grep "first"
first_topic
sh-4.4$
```

##### Describe a topic.

Use the `describe` flag to get details on the topic.

```bash
sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --topic first_topic --describe
Topic: first_topic      PartitionCount: 3       ReplicationFactor: 1    Configs:
        Topic: first_topic      Partition: 0    Leader: 1       Replicas: 1     Isr: 1  Offline:
        Topic: first_topic      Partition: 1    Leader: 1       Replicas: 1     Isr: 1  Offline:
        Topic: first_topic      Partition: 2    Leader: 1       Replicas: 1     Isr: 1  Offline:
sh-4.4$
```

Leader: 1 means that the leader partition belongs to broker 1.
Replicas: 1 means that the replicas partition belongs to broker 1.
Isr: 1 means the `in sync replicas` also belong to broker 1.

##### Delete topics.

To delete a topic via `cli` follow the command
```bash
sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --topic first_topic --delete
Topic first_topic is marked for deletion.
Note: This will have no impact if delete.topic.enable is not set to true.
sh-4.4$
```

Upon issuing the command the topic is set for deletion and we can verfiy that whether the topic deletion is completed using the command.
```bash
sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --list|grep first
sh-4.4$
```

### Kafka Command - kafka-console-producer

The first step to test kafka-producer-console utility in kafka is ensuring that we have a topic created. To do that lets use the `kafka-topics` utility from the previous section.

```bash
sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --topic first_topic --create --partitions 3 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic first_topic.

sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --list|grep first
first_topic
sh-4.4$
```

Add a message to the topic using the `kafka-console-producer` utility
```bash
sh-4.4$ kafka-console-producer --broker-list 127.0.0.1:9092 --topic first_topic
>Hello Varad
>This is my second message
>Lets see whats going on.
>Closing note.
>^Csh-4.4$
```

kafka-console-producer offers a range of valid options. The first one for now is `acks` known as acknowledgements. There are 3 valid values for acknowledgement categories in kafka they are
1. acks=0 (No acknowledgement is required. This can attribute to potential dataloss.)
1. acks=1 (Acknowledged when atleast one  partition has the message but not necessary that it is replicated across.)
1. acks=all (Acknowledged when the message is replicated across all ISR's. In this way there is no potential data loss.)

```bash
sh-4.4$ kafka-console-producer --broker-list localhost:9092 --topic first_topic --producer-property acks=all
>Hello with acks
>This is my test message
>All good. I should be getting acks when I consumer messages
>^Csh-4.4$
```

What happens upon publishing message to a topic which doesn't exist?
When message is publised to a topic which doesn't exists then kafka creates a new topic with default partition[s] and replication.

```bash
>^Csh-4.4$ kafka-console-producer --broker-list localhost:9092 --topic new_topic --producer-property acks=all
>Hello this is a new topic which doesnt exist.
[2021-06-05 09:09:23,044] WARN [Producer clientId=console-producer] Error while fetching metadata with correlation id 3 : {new_topic=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
>The topic is created and leader election must be over now. This means no more warning.
>:)
>^Csh-4.4$
```

Verify the new topic.
```bash
sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --list|grep "new"
new_topic
sh-4.4$
```

Describe the topic to get details
```bash
sh-4.4$ kafka-topics --zookeeper zookeeper:2181 --topic new_topic --describe
Topic: new_topic        PartitionCount: 1       ReplicationFactor: 1    Configs:
        Topic: new_topic        Partition: 0    Leader: 1       Replicas: 1     Isr: 1  Offline:
sh-4.4$
```

However it is not a good practice to create a topic dynamically, unless the situation demands the same.


### Kafka Command - kafka-console-consumer

To read the message from the kafka topic `first_topic` we can use the utility `kafka-console-consumer`. The mandatory flag[s] for the consumer command are `--bootstrap-server` and `--topic`.
The *bootstrap-server* points to kafka cluster url.

```bash
sh-4.4$ kafka-console-consumer --bootstrap-server localhost:9092  --topic first_topic

```
Note: We would not see the messages loaded before starting the consumer to do so we need to have additiona flag `--from-beginning`. However all messages once the consumer is up will be visible with the above command. Try publishing messages from another termnial using the `kafka-console-producer` utility discussed in the previous section.

```bash
# From the producer terminal
sh-4.4$ kafka-console-producer --broker-list localhost:9092 --topic first_topic
>Hello Varad
>All is well
>
```

```bash
# From the consumer terminal
sh-4.4$ kafka-console-consumer --bootstrap-server localhost:9092  --topic first_topic
Hello Varad
All is well
```

To get all messages from the beginning 
```bash
sh-4.4$ kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --from-beginning
Hello Varad
Lets see whats going on.
This is my test message
Hello Varad
Closing note.
All is well
This is my second message
Hello with acks
All good. I should be getting acks when I consumer messages
```

In production it is to be noted that messages from the topics should be thorough a consumer group. Consumer group takes care of load balancing of the messages sent to each partition by the producer. In a way it allows to resume the service and start consuming the message from the commit when the application left off when it restarted or brought down for maintenance.

The other commonly used command is `kafka-consumer-groups`. They are typically used to get the status, add, delete and reset the offset for consumer groups.