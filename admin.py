from flask import *
from database import *
import uuid
admin=Blueprint('admin',__name__)

@admin.route('/adminhome')
def adminhome():
	return render_template('adminhome.html')


@admin.route('/admin_view_user',methods=['get','post'])
def admin_view_user():

	data={}
	q="select * from user "
	data['staff']=select(q)	
	return render_template('admin_view_user.html',data=data)

@admin.route('/admin_manage_service',methods=['get','post'])
def admin_manage_service():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM service "
		res=select(q)
		data['service']=res

		if 'submit' in request.form:
			fname=request.form['fname']
			email=request.form['email']
			place=request.form['place']
			phone=request.form['phone']
			uname=request.form['uname']
			pas=request.form['pas']
			q="insert into login values (null,'%s','%s','service')"%(uname,pas)
			id=insert(q)
			q="insert into service values(null,'%s','%s','%s','%s','%s')"%(id,fname,place,phone,email)
			insert(q)
			flash('inserted successfully')
			return redirect(url_for('admin.admin_manage_service'))

		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
			
		else:
			action=None

		if action=="delete":
			q="delete from service  where service_id='%s'"%(id)
			update(q)
			flash('deleted successfully')
			return redirect(url_for('admin.admin_manage_service'))

		if action=="update":
			q="select * from service where service_id='%s'"%(id)
			print(q)
			res=select(q)
			data['updater']=res

		if 'update' in request.form:
			
		
			fname=request.form['fname']
			
			email=request.form['email']
			place=request.form['place']
			phone=request.form['phone']
			q="update service set name='%s',place='%s',phone='%s',email='%s' where service_id='%s'"%(fname,place,phone,email,id)
			update(q)
			flash('updated successfully')
			return redirect(url_for('admin.admin_manage_service'))

		return render_template('admin_manage_service.html',data=data)
	else:
		return redirect(url_for("public.login"))

	


@admin.route('/admin_manage_staff',methods=['get','post'])
def admin_manage_staff():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM staff "
		res=select(q)
		data['staff']=res

		if 'submit' in request.form:
			fname=request.form['fname']
			lname=request.form['lname']
			email=request.form['email']
			place=request.form['place']
			phone=request.form['phone']
			uname=request.form['uname']
			pas=request.form['pas']
			q="insert into login values (null,'%s','%s','staff')"%(uname,pas)
			id=insert(q)
			q="insert into staff values(null,'%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email)
			insert(q)
			flash('inserted successfully')
			return redirect(url_for('admin.admin_manage_staff'))

		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
			
		else:
			action=None

		if action=="delete":
			q="delete from staff  where staff_id='%s'"%(id)
			update(q)
			flash('deleted successfully')
			return redirect(url_for('admin.admin_manage_staff'))

		if action=="update":
			q="select * from staff where staff_id='%s'"%(id)
			print(q)
			res=select(q)
			data['updater']=res

		if 'update' in request.form:
			
		
			fname=request.form['fname']
			lname=request.form['lname']
			email=request.form['email']
			place=request.form['place']
			phone=request.form['phone']
			q="update staff set fname='%s',lname='%s',place='%s',phone='%s',email='%s' where staff_id='%s'"%(fname,lname,place,phone,email,id)
			update(q)
			flash('updated successfully')
			return redirect(url_for('admin.admin_manage_staff'))
	else:
		return redirect(url_for("public.login"))

	return render_template('admin_manage_staff.html',data=data)


