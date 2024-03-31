from flask import Blueprint, jsonify, request
import json

from src.model.post import Post, PostSchema
from ..config.mysql import Session

post_api_bp = Blueprint('post_api', __name__)

@post_api_bp.route('')
def get_incomes():
    session = Session()
    try:
        posts = session.query(Post).all()
        session.close()
        all_posts = [post.serialize_post() for post in posts]
        result = json.dumps(all_posts, sort_keys=False)
        return result, 200, {'Content-Type': 'application/json'}
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500

@post_api_bp.route('', methods=['POST'])
def create_incomes():
    post_data = PostSchema().load(request.get_json())
    post = Post(**post_data)
    session = Session()
    try:
        session.add(post)
        session.commit()
        return jsonify({'message': 'User created successfully'}), 201
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()