import json

from flask import (
    Blueprint, flash, g, redirect, render_template, request, url_for, jsonify
)
from werkzeug.exceptions import abort

from client.db import get_db
from flask_cors import CORS, cross_origin

bp = Blueprint('handlebars', __name__)

@bp.route('/get_cars', methods=['GET'])
def get():
	print('omg*************')
	cars = {}
	db = get_db()
	db_cars = db.execute(
		'SELECT model_id, model_make_id, model_name'
		' FROM cqa'
                ' ORDER BY RANDOM()'
                ' LIMIT 10'
                 )
	desc = db_cars.description
	column_names = [col[0] for col in desc]
	data = [dict(zip(column_names, row))  
        	for row in db_cars.fetchall()]
	print(data)
	return json.dumps(data)

@bp.route('/diceroll', methods=['GET'])
def diceroll():
	print('omg*************')
	cars = {}
	db = get_db()
	db_cars = db.execute(
		'SELECT model_id, model_make_id, model_name'
		' model_trim, model_year, model_body, model_engine_position,'
		' model_engine_cc, model_engine_cyl, model_engine_type,'
		' model_engine_valves_per_cyl, model_engine_power_ps,'
		' model_engine_power_rpm, model_engine_torque_nm,'
		' model_engine_torque_rpm, model_engine_bore_mm, model_engine_stroke_mm'
		' model_engine_compression, model_engine_fuel, model_top_speed_kph'
		' model_0_to_100_kph, model_drive, model_transmission_type, model_seats'
		' model_doors, model_weight_kg, model_length_mm, model_width_mm'
		' model_height_mm, model_wheelbase_mm, model_lkm_hwy, model_lkm_mixed'
		' model_lkm_city, model_fuel_cap_l, model_sold_in_us, model_co2, model_make_display'
		' FROM cqa'
		' ORDER BY RANDOM()'
                ' LIMIT 1'

                 )
	desc = db_cars.description
	column_names = [col[0] for col in desc]
	data = [dict(zip(column_names, row))  
        	for row in db_cars.fetchall()]
	print(data)
	return json.dumps(data)


@bp.route('/get_car', methods=['POST'])
def post_single():
	id = '2'
	print('getting single')
	car = {}
	db = get_db()
	db_car = db.execute(
		'SELECT model_id, model_make_id, model_name,'
		' model_trim, model_year, model_body, model_engine_position,'
		' model_engine_cc, model_engine_cyl, model_engine_type,'
		' model_engine_valves_per_cyl, model_engine_power_ps,'
		' model_engine_power_rpm, model_engine_torque_nm,'
		' model_engine_torque_rpm, model_engine_bore_mm, model_engine_stroke_mm'
		' model_engine_compression, model_engine_fuel, model_top_speed_kph'
		' model_0_to_100_kph, model_drive, model_transmission_type, model_seats'
		' model_doors, model_weight_kg, model_length_mm, model_width_mm'
		' model_height_mm, model_wheelbase_mm, model_lkm_hwy, model_lkm_mixed'
		' model_lkm_city, model_fuel_cap_l, model_sold_in_us, model_co2, model_make_display'
		' FROM cqa'
		' WHERE model_id = ?'
		' LIMIT 1', (id)
		)
	desc = db_car.description
	column_names = [col[0] for col in desc]
	data = [dict(zip(column_names, row))  
        	for row in db_car.fetchall()]
	print(data)
	return redirect('http://45.33.13.200:4200')


