import os
from app import create_app
from db import db
from confluent_kafka import Consumer
from messaging import consumer


def getConfigFile():
    """ Get configuration based on the environment variable 'env'
    """
    return os.path.join(os.path.join(os.path.dirname(__file__), 'config'), os.getenv('env', 'dev')) + '.toml'


config = getConfigFile()
application = create_app(config)
db.init_app(application)
conf = {
    'bootstrap.servers': "localhost:9092",
    'group.id': "test-first-topic",
    'auto.offset.reset': 'smallest'
}
consumerHandler = Consumer(conf)



@application.before_first_request
def loadDatabase():
    db.create_all()
    consumer.consume_loop(consumerHandler, ['first_topic'])


application.run(host = application.config.get('HOST'), port = application.config.get('PORT'))