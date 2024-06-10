from flask import *
from database import *
public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template('home.html')

@public.route('/login',methods=['get','post'])
def login():
	session.clear()
	if "submit" in request.form:
		u=request.form['username']
		p=request.form['password']
		q="select * from login where username='%s' and password='%s'"%(u,p)
		print(q)
		res=select(q)
		if res:
			session['lid']=res[0]['login_id']
			if res[0]['usertype']=="admin":
				flash("Logging in")			
				return redirect(url_for("admin.adminhome"))

			elif res[0]['usertype']=="service":
				q="select * from service where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['sid']=res1[0]['service_id']
					print(session['sid'])
					flash("Logging in")
					return redirect(url_for("service.servicehome"))

			elif res[0]['usertype']=="staff":
				q="select * from staff where login_id='%s'"%(res[0]['login_id'])
				res1=select(q)
				if res1:
					session['st_id']=res1[0]['staff_id']
					print(session['st_id'])
					flash("Logging in")
					return redirect(url_for("staff.staffhome"))

			else:
				flash("LOGIN Under Process")
		flash("Incorrect password")

	return render_template('login.html')


