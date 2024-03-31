import datetime as dt

from marshmallow import Schema, fields


class Comment(object):
    def __init__(self, id, name, email, body):
        self.id = id
        self.name = name
        self.email = email
        self.body = body

    def __repr__(self):
        return '<Comment(name={self.description!r})>'.format(self=self)


class CommentSchema(Schema):
    id = fields.Number()
    name = fields.Str()
    email = fields.Str()
    body = fields.Str()