import datetime as dt
from marshmallow import Schema, fields
from sqlalchemy import Column, Integer, String, DateTime

from ..config.mysql import Base

class Account(Base):
    __tablename__ = 'account'

    id = Column(Integer, primary_key=True)
    name = Column(String(255), nullable=True)
    account_number = Column(String(255), nullable=True)
    email = Column(String(255), nullable=True)

    def __repr__(self):
        return f'<Account(id={self.id}, account_number={self.account_number})>'
    
    def serialize_post(self):
        return {
            "id": self.id,
            "name": self.name,
            "account_number": self.account_number,
            "email": self.email
        }


class AccountSchema(Schema):
    id = fields.Number()
    name = fields.Str()
    account_number = fields.Str()
    email = fields.Str()