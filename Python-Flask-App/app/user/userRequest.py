from flask_restful import Resource, reqparse


class UserRequest(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument('username', required=True, type=str, help="username is mandatory field.")
    parser.add_argument('firstname', required=True, type=str, help="lastname is mandatory field.")
    parser.add_argument('lastname', required=True, type=str, help="firstname is mandatory field.")
    parser.add_argument('message', required=True, type=str, help="message is mandatory field.")

    def get():
        """ Get all user information.
        """
        return "User Request.", 200

    def post():
        """ Add a new user.
        """
        return None
