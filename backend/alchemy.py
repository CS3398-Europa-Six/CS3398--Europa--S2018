from flask import Flask
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://scott:tiger@localhost/home/nhh/Documents/automotive-model-year-data/'
db = SQLAlchemy(app)

class Example(db.Model):
    __tablename__ = 'data'
    year = db.Column('year', db.Integer, primary_key=True)
    make = db.Column('make', db.Unicode)
    model = db.Column('model', db.Unicode)
