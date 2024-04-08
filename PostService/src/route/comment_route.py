from flask import Blueprint, jsonify, request
import json

from src.model.post import Comment, CommentSchema
# from src.model.post import Post, PostSchema
from ..config.mysql import Session
from ..secure.JWT_token_provider import token_required

comment_api_bp = Blueprint('comment_api', __name__)

@comment_api_bp.route('/my')
@token_required
def get_incomes_by_username(current_user):
    session = Session()
    try:
        comments = session.query(Comment).filter(Comment.account_number == current_user).all()
        # all_post_ids = [comment.post_id for comment in comments]
        # posts = session.query(Post).filter(Post.id.in_(all_post_ids)).all()
        # all_posts = [post.serialize_post() for post in posts]
        all_comments = [comment.serialize_comment() for comment in comments]
        result = json.dumps(all_comments, sort_keys=False)
        return result, 200, {'Content-Type': 'application/json'}
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()

@comment_api_bp.route('', methods=['POST'])
@token_required
def create_post(current_user):
    comment_data = CommentSchema().load(request.get_json())
    comment = Comment(**comment_data)
    comment.account_number = current_user
    session = Session()
    try:
        session.add(comment)
        session.commit()
        return jsonify({'message': f'{current_user} creates comment successfully'}), 201
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()

@comment_api_bp.route('<int:comment_id>', methods=['PUT'])
@token_required
def update_post(current_user, comment_id):
    try:
        update_comment_data = CommentSchema().load(request.get_json())
    except Exception as e:
        return jsonify({'message': 'Validation error', 'errors': e.messages}), 400
    
    session = Session()
    try:
        cooment = session.query(Comment).filter_by(id=comment_id).filter_by(account_number=current_user).first()
        if cooment is None:
            return jsonify({'message': 'comment not found'}), 404
        
        for key, value in update_comment_data.items():
            if hasattr(cooment, key):
                setattr(cooment, key, value)
            else:
                return jsonify({'message': f'Invalid field provided: {key}'}), 400

        session.commit()
        return jsonify({'message': 'Comment updated successfully', 'post': CommentSchema().dump(cooment)}), 200
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()

@comment_api_bp.route('<int:comment_id>', methods=['DELETE'])
@token_required
def delete_post(current_user, comment_id):
    session = Session()
    try:
        comment = session.query(Comment).filter_by(id=comment_id).filter_by(account_number=current_user).first()
        if comment is None:
            return jsonify({'message': 'Comment not found'}), 404
        
        session.delete(comment)
        session.commit()
        
        return jsonify({'message': 'Comment deleted successfully'}), 200
    except Exception as e:
        return jsonify({'message': 'An error occurred', 'error': str(e)}), 500