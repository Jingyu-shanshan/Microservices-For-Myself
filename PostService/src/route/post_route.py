from flask import Blueprint, jsonify, request
import json

from src.model.post import Post, PostSchema
from ..config.mysql import Session
from ..secure.JWT_token_provider import token_required
from sqlalchemy.orm import joinedload

post_api_bp = Blueprint('post_api', __name__)

@post_api_bp.route('')
def get_incomes():
    session = Session()
    try:
        posts = session.query(Post).options(joinedload(Post.comments)).all()
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
        posts = session.query(Post).options(joinedload(Post.comments)).filter(Post.account_number == current_user).all()
        all_posts = [post.serialize_post() for post in posts]
        result = json.dumps(all_posts, sort_keys=False)
        return result, 200, {'Content-Type': 'application/json'}
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()

@post_api_bp.route('<int:post_id>', methods=['GET'])
def get_incomes_by_id(post_id):
    session = Session()
    try:
        post = session.query(Post).filter_by(id=post_id).first()
        if post is None:
            return jsonify({'message': 'Post not found'}), 404

        result = json.dumps(post.serialize_post())
        return result, 200, {'Content-Type': 'application/json'}
    except Exception as e:
        session.rollback()
        return jsonify({'message': 'An error occurred', 'error': str(e)}), 500
    finally:
        session.close()

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
            if hasattr(post, key):
                setattr(post, key, value)
            else:
                return jsonify({'message': f'Invalid field provided: {key}'}), 400

        session.commit()
        return jsonify({'message': 'Post updated successfully', 'post': PostSchema().dump(post)}), 200
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()

@post_api_bp.route('<int:post_id>', methods=['DELETE'])
@token_required
def delete_post(current_user, post_id):
    session = Session()
    try:
        post = session.query(Post).filter_by(id=post_id).filter_by(account_number=current_user).first()
        if post is None:
            return jsonify({'message': 'Post not found'}), 404
        
        session.delete(post)
        session.commit()
        
        return jsonify({'message': 'Post deleted successfully'}), 200
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()