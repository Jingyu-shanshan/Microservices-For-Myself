from flask import Flask, request

from src.route.post_route import post_api_bp
from src.config.mysql import engine, Session, Base, DB_URL
from src.config.jwt_token_const import SECRET_KEY

app = Flask(__name__)

app.config['SQLALCHEMY_DATABASE_URI'] = DB_URL
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

app.config['SECRET_KEY'] = SECRET_KEY

Base.metadata.bind = engine

Session.configure(bind=engine)

app.register_blueprint(post_api_bp, url_prefix='/api/posts')

if __name__ == '__main__':
    app.run(debug=True)