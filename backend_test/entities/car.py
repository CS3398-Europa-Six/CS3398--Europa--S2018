from sqlalchemy import Column, String, ARRAY
from marshmallow import Schema, fields
from .entity import Entity, Base

class Car(Entity, Base):
	__tablename__ = 'carTable'
	print("debugging")

	year = Column(String)
	make = Column(String)
	model = Column(String)


	def __init__(self, year, make, model):
		Entity.__init__(self, created_by)
		self.year = year
		self.make = make
		self.model = model

class CarSchema(Schema):
    id = fields.Number()
    year = fields.Str()
    make = fields.Str()
    model = fields.Str()
    #created_at = fields.DateTime()
    #updated_at = fields.DateTime()
    #last_updated_by = fields.Str()

