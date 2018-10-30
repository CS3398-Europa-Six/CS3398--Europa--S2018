from flask import Flask, jsonify, request
from flask_cors import CORS, cross_origin

from .entities.entity import Session, engine, Base
from .entities.car import Car, CarSchema

print("hi")
app = Flask(__name__, instance_relative_config=True)
CORS(app)

    # a simple page that says hello
@app.route('/hello')
def hello():
	return 'Hello, World!!!'

@app.route('/cars')
def get_cars():

   session = Session()
   print(session)
   dump = session.query('year from carTable').all()
   car_objects = session.query(Car).all()

   schema = CarSchema(many=True)
   cars = schema.dump(car_objects)

   session.close()
   print("Getting cars")
   return jsonify(cars.data)