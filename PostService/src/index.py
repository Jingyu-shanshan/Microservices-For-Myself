from flask import Flask, request

from src.route.post_route import post_api_bp
from src.config.mysql import engine, Session, Base, DB_URL

app = Flask(__name__)

app.config['SQLALCHEMY_DATABASE_URI'] = DB_URL
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

Base.metadata.bind = engine

Session.configure(bind=engine)

app.register_blueprint(post_api_bp, url_prefix='/api/posts')

if __name__ == '__main__':
    app.run(debug=True)