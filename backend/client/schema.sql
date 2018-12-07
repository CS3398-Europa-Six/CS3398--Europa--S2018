DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS instructors;
DROP TABLE IF EXISTS instructor_courses;
DROP TABLE IF EXISTS assigned_courses;

CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT,
  password TEXT
);

CREATE TABLE cars (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  model_id  TEXT,
  model_make_id  TEXT,
  model_name  TEXT,
  model_trim  TEXT,
  model_year  TEXT,
  model_body  TEXT,
  model_engine_position  TEXT,
  model_engine_cc  TEXT,
  model_engine_cyl  TEXT,
  model_engine_type  TEXT,
  model_engine_valves_per_cyl  TEXT,
  model_engine_power_ps  TEXT,
  model_engine_power_rpm  TEXT,
  model_engine_torque_nm  TEXT,
  model_engine_torque_rpm  TEXT,
  model_engine_bore_mm  TEXT,
  model_engine_stroke_mm  TEXT,
  model_engine_compression  TEXT,
  model_engine_fuel  TEXT,
  model_top_speed_kph  TEXT,
  model_0_to_100_kph  TEXT,
  model_drive  TEXT,
  model_transmission_type  TEXT,
  model_seats  TEXT,
  model_doors  TEXT,
  model_weight_kg  TEXT,
  model_length_mm  TEXT,
  model_width_mm  TEXT,
  model_height_mm  TEXT,
  model_wheelbase_mm  TEXT,
  model_lkm_hwy  TEXT,
  model_lkm_mixed  TEXT,
  model_lkm_city  TEXT,
  model_fuel_cap_l  TEXT,
  model_sold_in_us  TEXT,
  model_co2  TEXT,
  model_make_display  TEXT,
  );

CREATE TABLE assigned_cars (
  username TEXT,
  model_id TEXT,
  PRIMARY KEY(instructor_id, model_id)
);