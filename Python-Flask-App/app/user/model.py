from db import db


class User(db.Model):
    __tablename__ = "User"

    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(50), unique=True, nullable=False)
    firstname = db.Column(db.String(50))
    lastname = db.Column(db.String(50))
    message = db.Column(db.String(1000))

    def __init__(self, username, firstname, lastname, message):
        self.username = username
        self. firstname = firstname
        self.lastname = lastname
        self.message = message

    def save(self):
        db.session.add(self)

    def __repr__(self):
        return '<User %r>' % self.username