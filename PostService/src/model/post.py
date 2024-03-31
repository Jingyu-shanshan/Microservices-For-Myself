import datetime as dt
from marshmallow import Schema, fields
from sqlalchemy import Column, Integer, String, DateTime

from ..config.mysql import Base

class Post(Base):
    __tablename__ = 'post'

    id = Column(Integer, primary_key=True)
    title = Column(String(255), nullable=True)
    description = Column(String(255), nullable=True)
    content = Column(String(255), nullable=True)
    type = Column(String(255), nullable=True)
    created_at = Column(DateTime, default=dt.datetime.utcnow)

    def __repr__(self):
        return f'<Post(id={self.id}, title={self.title})>'
    
    def serialize_post(self):
        return {
            "id": self.id,
            "title": self.title,
            "description": self.description,
            "content": self.content,
            "type": self.type,
            "created_at": self.created_at.strftime("%Y-%m-%d")
        }


class PostSchema(Schema):
    id = fields.Number()
    title = fields.Str()
    description = fields.Str()
    content = fields.Str()
    type = fields.Str()
    created_at = fields.Date()