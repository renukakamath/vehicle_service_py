from flask import *
from database import *
import uuid

import json
from web3 import Web3, HTTPProvider

# truffle development blockchain address
blockchain_address = ' http://127.0.0.1:9545'
# Client instance to interact with the blockchain
web3 = Web3(HTTPProvider(blockchain_address))
# Set the default account (so we don't need to set the "from" for every transaction call)
web3.eth.defaultAccount = web3.eth.accounts[0]
compiled_contract_path = 'C:/Users/Parvana/OneDrive/Desktop/New folder/vehicle/vehicle/vehicle/node_modules/.bin/build/contracts/vehicle.json'
# compiled_contract_path = 'F:/NGO/node_modules/.bin/build/contracts/medicines.json'
# Deployed contract address (see `migrate` command output: `contract address`)
deployed_contract_address = '0x48b42C884eB59ef1820e17f0c6D5557f50d2Da2f'
staff=Blueprint('staff',__name__)

@staff.route('/staffhome')
def staffhome():
	return render_template('staffhome.html')

@staff.route('/staff_manage_vehicle',methods=['get','post'])
def staff_manage_vehicle():
	if not session.get("lid") is None:
		data={}
		q="SELECT * FROM vehicle inner join user using(user_id)  "
		res=select(q)
		data['staff']=res

		q="select * from user"
		data['us']=select(q)

		if 'submit' in request.form:
			fname=request.form['vehicle']
			lname=request.form['user']
			email=request.form['detail']
			q="insert into vehicle values(null,'%s','%s','%s')"%(lname,fname,email)
			insert(q)
			flash('inserted successfully')
			return redirect(url_for('staff.staff_manage_vehicle'))

		if 'action' in request.args:
			action=request.args['action']
			id=request.args['id']
			
		else:
			action=None

		if action=="delete":
			q="delete from vehicle  where vehicle_id='%s'"%(id)
			update(q)
			flash('deleted successfully')
			return redirect(url_for('staff.staff_manage_vehicle'))

		if action=="update":
			q="select * from vehicle where vehicle_id='%s'"%(id)
			print(q)
			res=select(q)
			data['updater']=res

		if 'update' in request.form:
			
		
			fname=request.form['fname']
			lname=request.form['detail']
			
			q="update vehicle set vehicle='%s',detail='%s' where vehicle_id='%s'"%(fname,lname,id)
			update(q)
			flash('updated successfully')
			return redirect(url_for('staff.staff_manage_vehicle'))
	
		return render_template('staff_manage_vehicle.html',data=data)
	else:
		return redirect(url_for("public.login"))



@staff.route('/staff_view_requested',methods=['get','post'])
def staff_view_requested():

	data={}
	q="select * from request inner join vehicle using(vehicle_id) where status='accepted' or status='approved' or status='rejected'"
	data['staff']=select(q)	

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']
			
	else:
		action=None

	if action=="accept":
		q="update  request set status='approved'  where request_id='%s'"%(id)
		update(q)
		flash('approved successfully')
		return redirect(url_for('staff.staff_view_requested',id=id))


	if action=="reject":
		q="update  request set status='rejected'  where request_id='%s'"%(id)
		update(q)
		flash('rejected successfully')
		return redirect(url_for('staff.staff_view_requested'))
	return render_template('staff_view_requested.html',data=data)

@staff.route('/staff_upload',methods=['get','post'])
def staff_upload():
	data={}
	id=request.args['id']
	q="select * from upload where vehicle_id='%s' "%(id)
	res=select(q)
	data['imagesss']=res
 
	if 'submit' in request.form:
		ty=request.form['tys']
		noti=request.files['img']
		path='static/upload/'+str(uuid.uuid4())+noti.filename
		noti.save(path)
		q="insert into upload values(null,'%s','%s','%s')"%(id,ty,path)
		insert(q)

		import datetime
		d=datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")
		with open(compiled_contract_path) as file:
			contract_json = json.load(file)  # load contract info as JSON
			contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
			contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
			id=web3.eth.get_block_number()
		message = contract.functions.add_upload(id,int(id),ty,path).transact()
		flash('uploaded successfully')
		return redirect(url_for('staff.staff_upload',id=id))

	if 'action' in request.args:
		action=request.args['action']
		id=request.args['id']        

	else:
		action=None

	if action=="delete":
		q="delete from upload where upload_id='%s'"%(id)
		delete(q)
		return redirect(url_for('staff.staff_upload'))


	return render_template("staff_upload.html",data=data)
