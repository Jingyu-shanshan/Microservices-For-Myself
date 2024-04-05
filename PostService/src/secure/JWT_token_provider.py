from flask import request, jsonify
import jwt
import base64
from functools import wraps

from ..config.jwt_token_const import SECRET_KEY

def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = request.headers.get('Authorization')
        if not token or not token.startswith("Bearer "):
            return jsonify({'message': 'Token is missing'}), 401
        token = token[7:]

        secret_key = decode_base64(SECRET_KEY)
        data = jwt.decode(token, secret_key, algorithms=["HS384"])

        try:
            data = jwt.decode(token, secret_key, algorithms=["HS384"])
            current_user = data['sub']
        except:
            return jsonify({'message': 'Token is invalid'}), 401

        return f(current_user, *args, **kwargs)

    return decorated

def decode_base64(encoded_string):
    decoded_string = base64.b64decode(encoded_string)
    return decoded_string