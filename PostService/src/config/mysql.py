from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from dotenv import load_dotenv
import os

# local test
# load_dotenv(dotenv_path="../.env")

# db_user = os.getenv("MYSQLDB_USER")
# db_password = os.getenv("MYSQLDB_ROOT_PASSWORD")
# db_database = os.getenv("MYSQLDB_DATABASE")

db_user = os.environ.get("MYSQLDB_USER")
db_password = os.environ.get("MYSQLDB_ROOT_PASSWORD")
db_database = os.environ.get("MYSQLDB_DATABASE")

# Define the database URL
DB_URL = f'mysql+mysqlconnector://{db_user}:{db_password}@localhost:3306/{db_database}'

# Create a SQLAlchemy engine
engine = create_engine(DB_URL)

# Create a sessionmaker
Session = sessionmaker(bind=engine)