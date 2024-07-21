import datetime as dt
from marshmallow import Schema, fields
from sqlalchemy import Column, Integer, String, DateTime, ForeignKey
from sqlalchemy.orm import relationship

from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class Post(Base):
    __tablename__ = 'post'

    id = Column(Integer, primary_key=True)
    title = Column(String(255), nullable=True)
    description = Column(String(255), nullable=True)
    content = Column(String, nullable=True)
    type = Column(String(255), nullable=True)
    created_at = Column(DateTime, default=dt.datetime.utcnow)
    account_number = Column(String(255), nullable=True)
    like_count = Column(Integer, nullable=False, default=0)

    comments = relationship("Comment", back_populates="post")

    def __repr__(self):
        return f'<Post(id={self.id}, title={self.title})>'
    
    def serialize_post(self):
        return {
            "id": self.id,
            "title": self.title,
            "description": self.description,
            "content": self.content,
            "type": self.type,
            "created_at": self.created_at.strftime("%Y-%m-%d"),
            "account_number": self.account_number,
            "like_count": self.like_count,
            "comments": [comment.serialize_comment() for comment in self.comments]
        }

class PostSchema(Schema):
    id = fields.Integer()
    title = fields.Str()
    description = fields.Str()
    content = fields.Str()
    type = fields.Str()
    created_at = fields.Date()
    like_count = fields.Integer()

class Comment(Base):
    __tablename__ = 'comment'

    id = Column(Integer, primary_key=True)
    body = Column(String, nullable=True)
    created_at = Column(DateTime, default=dt.datetime.utcnow)
    account_number = Column(String(255), nullable=True)
    reply_to = Column(String(255), nullable=True)
    post_id = Column(Integer, ForeignKey('post.id'))

    post = relationship("Post", back_populates="comments")

    def __repr__(self):
        return f'<Comment({self.body[:20]}...)>'
    
    def serialize_comment(self):
        return {
            "id": self.id,
            "body": self.body,
            "created_at": self.created_at.strftime("%Y-%m-%d"),
            "account_number": self.account_number,
            "reply_to": self.reply_to,
            "post_id": self.post_id
        }

class CommentSchema(Schema):
    id = fields.Integer()
    body = fields.Str()
    created_at = fields.Date()
    account_number = fields.Str()
    reply_to = fields.Str()
    post_id = fields.Integer()