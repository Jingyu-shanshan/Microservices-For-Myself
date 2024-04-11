from flask import Flask, request

from src.route.post_route import post_api_bp
from src.route.comment_route import comment_api_bp
from src.config.mysql import engine, Session, DB_URL
from src.config.jwt_token_const import SECRET_KEY
from flask_sqlalchemy import SQLAlchemy

from src.model.post import Base

app = Flask(__name__)

app.config['SQLALCHEMY_DATABASE_URI'] = DB_URL
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SECRET_KEY'] = SECRET_KEY

db = SQLAlchemy()
db.init_app(app)

Session.configure(bind=engine)

Base.metadata.create_all(engine)

app.register_blueprint(post_api_bp, url_prefix='/api/posts')
app.register_blueprint(comment_api_bp, url_prefix='/api/comments')

@app.errorhandler(404)
def page_not_found(error):
    # Print the request URL causing the 404 error
    print("404 Error - Request URL:", request.url)
    return "404 Error - Page Not Found", 404

if __name__ == '__main__':
    app.run(debug=True)