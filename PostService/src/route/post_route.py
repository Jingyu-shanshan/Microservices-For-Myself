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
    
@post_api_bp.route('/my')
@token_required
def get_incomes_by_username(current_user):
    session = Session()
    try:
        posts = session.query(Post).filter(Post.account_number == current_user).all()
        session.close()
        all_posts = [post.serialize_post() for post in posts]
        result = json.dumps(all_posts, sort_keys=False)
        return result, 200, {'Content-Type': 'application/json'}
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500

@post_api_bp.route('', methods=['POST'])
@token_required
def create_post(current_user):
    post_data = PostSchema().load(request.get_json())
    post = Post(**post_data)
    post.account_number = current_user
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

@post_api_bp.route('<int:post_id>', methods=['PUT'])
@token_required
def update_post(current_user, post_id):
    try:
        update_post_data = PostSchema().load(request.get_json())
    except Exception as e:
        return jsonify({'message': 'Validation error', 'errors': e.messages}), 400
    
    session = Session()
    try:
        post = session.query(Post).filter_by(id=post_id).filter_by(account_number=current_user).first()
        if post is None:
            return jsonify({'message': 'Post not found'}), 404
        
        for key, value in update_post_data.items():
            setattr(post, key, value)

        session.commit()
        return jsonify({'message': 'Post updated successfully', 'post': PostSchema().dump(post)}), 200
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()