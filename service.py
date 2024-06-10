from flask import *
from database import *
import uuid
service=Blueprint('service',__name__)

@service.route('/servicehome')
def servicehome():
	return render_template('servicehome.html')


@service.route('/service_view_request',methods=['get','post'])
def service_view_request():

	data={}
	q="select * from request inner join vehicle using(vehicle_id)"
	data['req']=select(q)

	if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
			
	else:
		action=None

	if action=="accept":
		q="update  request set status='accepted'  where request_id='%s'"%(id)
		update(q)
		flash('accepted successfully')
		return redirect(url_for('service.service_add_detail',id=id))


	if action=="reject":
		q="update  request set status='rejected'  where request_id='%s'"%(id)
		update(q)
		flash('rejected successfully')
		return redirect(url_for('service.service_view_request'))

	return render_template('service_view_request.html',data=data)


@service.route('/service_view_detail',methods=['get','post'])
def service_view_detail():

	data={}
	id=request.args['id']
	q="select * from request inner join vehicle using(vehicle_id) where vehicle_id='%s'"%(id)
	data['req']=select(q)
	return render_template('service_view_detail.html',data=data)


@service.route('/service_add_detail',methods=['get','post'])
def service_add_detail():

	data={}
	id=request.args['id']
	q="select * from service_detail  where request_id='%s'"%(id)
	data['req']=select(q)

	if 'submit' in request.form:
	
		email=request.form['detail']
		q="insert into service_detail values(null,'%s','%s',curdate())"%(id,email)
		insert(q)
		flash('inserted successfully')
		return redirect(url_for('service.service_view_request'))

	return render_template('service_add_detail.html',data=data)


