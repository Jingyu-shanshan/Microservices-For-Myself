from flask import Blueprint, jsonify, request
import json

from src.model.post import Post, PostSchema
from ..config.mysql import Session
from ..secure.JWT_token_provider import token_required

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
@token_required
def create_incomes(current_user):
    post_data = PostSchema().load(request.get_json())
    post = Post(**post_data)
    session = Session()
    try:
        session.add(post)
        session.commit()
        return jsonify({'message': f'{current_user} creates post successfully'}), 201
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()