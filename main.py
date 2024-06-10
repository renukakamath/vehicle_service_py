from flask import Flask
from public import public
from admin import admin
from service import service
from staff import staff
from api import api


app=Flask(__name__)
app.secret_key="vehicle"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(service,url_prefix='/service')
app.register_blueprint(staff,url_prefix='/staff')
app.register_blueprint(api,url_prefix='/api')
app.run(debug=True,port=5435,host="0.0.0.0")     