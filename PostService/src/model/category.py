import datetime as dt

from marshmallow import Schema, fields


class Category(object):
    def __init__(self, id, name, description):
        self.id = id
        self.name = name
        self.description = description

    def __repr__(self):
        return '<Comment(name={self.description!r})>'.format(self=self)


class Comment(Schema):
    id = fields.Number()
    name = fields.Str()
    description = fields.Str()