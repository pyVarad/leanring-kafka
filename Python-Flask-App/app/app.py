import os
from flask import Flask
import toml
from flask_restful import Api

from user.userRequest import UserRequest

app = Flask(__name__)

def create_app(cfg):
    """ Application Properties are mapped here
    """
    app.config.from_file(cfg, load=toml.load)
    api = Api(app)
    api.add_resource(UserRequest, '/user')

    return app


@app.route("/")
def health():
    return {
        "status": "App is up and running."
    }