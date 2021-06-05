import sys
from confluent_kafka import KafkaException, KafkaError


MIN_COMMIT_COUNT = 2

def consume_loop(consumer, topics):
    try:
        consumer.subscribe(topics)

        msg_count = 0
        while True:
            msg = consumer.poll(timeout=1.0)
            if msg is None: continue

            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    # End of partition event
                    sys.stderr.write('%% %s [%d] reached end at offset %d\n' %
                                     (msg.topic(), msg.partition(), msg.offset()))
                elif msg.error():
                    raise KafkaException(msg.error())
            else:
                print(msg.key)
                print(msg.value)
                print(msg)
                msg_count += 1
                if msg_count % MIN_COMMIT_COUNT == 0:
                    consumer.commit(asynchronous=True)
    finally:
        consumer.close()