from flask import *
from database import*
import uuid
import base64
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
deployed_contract_address = '0x9409923E1d8C3E4f85B41B35d9Bc2671981011ff'


api=Blueprint('api',__name__)



@api.route('/Login',methods=['post','get'])
def Login():
	data={}
	u=request.form['username']
	pw=request.form['password']
	image=request.files['image']
	path="static/upload"+str(uuid.uuid4())+image.filename
	image.save(path)
	
	with open(path, "rb") as imageFile:
		stri = base64.b64encode(imageFile.read())
		print(len(stri))
	q="select * from login inner join user using (login_id)  where username='%s' and password='%s' and image='%s'  "%(u,pw,len(stri))
	print(q)
	res=select(q)
	print(res)
	if res:
		data['status']="success"
		data['uname']=res[0]['fname']+ " "+res[0]['lname']
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/Registartion',methods=['get','post'])	
def Registartion():
	data={}
	f=request.form['fname']
	l=request.form['lname']
	
	pl=request.form['street']
	

	ph=request.form['phone']
	e=request.form['email']
	u=request.form['username']
	p=request.form['password']

	image=request.files['image']
	path="static/upload"+str(uuid.uuid4())+image.filename
	image.save(path)
	with open(path, "rb") as imageFile:
		stri = base64.b64encode(imageFile.read())
		print(len(stri))

	
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['status']='already'
	else:
		q="insert into login values(NULL,'%s','%s','user')"%(u,p)
		lid=insert(q)
		print(q)
		r="insert into user values(NULL,'%s','%s','%s','%s','%s','%s','%s')"%(lid,f,l,pl,ph,e,len(stri))
		insert(r)
		print(r)
		data['status']="success"
	return str(data)


@api.route('/Viewvehicle')
def Viewvehicle():
	data={}
	uid=request.args['login_id']
	q="select *,vehicle.image as image from vehicle inner join user using (user_id) where user_id=(select user_id from user where login_id='%s')"%(uid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewvehicle"
	return str(data)

@api.route('/sendrequest')
def sendrequest():
	data={}
	vid=request.args['vid']

	q="insert into request values(null,'%s',curdate(),'pending',now())"%(vid)
	insert(q)
	print(q)
	
	

	data['status']="success"
	data['method']="user_otp"


	return str(data)


@api.route('/Sendservice')
def Sendservice():
	data={}
	rid=request.args['rid']
	details=request.args['details']

	q="insert into service_detail values(null,'%s','%s',curdate())"%(rid,details)
	insert(q)
	print(q)
	
	

	data['status']="success"
	data['method']="Sendservice"


	return str(data)


@api.route('/sell')
def sell():
	data={}
	vid=request.args['vid']
	amt=request.args['amount']

	q="insert into buy values(null,'%s','%s',curdate(),'sell')"%(vid,amt)
	insert(q)
	print(q)
	
	

	data['status']="success"
	data['method']="sell"
	return str(data)


@api.route('/Buy')
def Buy():
	data={}
	login_id=request.args['login_id']
	vid=request.args['vid']
	amt=request.args['amt']

	q="insert into buy values(null,(select user_id from user where login_id='%s'),'%s','%s',curdate(),'buy')"%(login_id,vid,amt)
	insert(q)
	print(q)
	
	

	data['status']="success"
	data['method']="sell"


	return str(data)


	return str(data)


@api.route('/Viewrequest')
def Viewrequest():
	data={}
	q="select * from request inner join vehicle using (vehicle_id)  where status='approved'"
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewrequest"
	return str(data)


@api.route('/Insurence')
def Insurence():
	data={}

	vid=request.args['vid']
	q="select * from upload inner join vehicle using (vehicle_id)  where vehicle_id='%s'"%(vid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Insurence"
	return str(data)


@api.route('/vehicle')
def vehicle():
	data={}

	uid=request.args['uid']
	q="select * from upload   where upload_id='%s'"%(uid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="vehicles"
	return str(data)




@api.route('/Viewothervehicle')
def Viewothervehicle():
	data={}
	uid=request.args['login_id']
	# q="select *,vehicle.image as vimage from buy inner join vehicle using (vehicle_id) inner join user using (user_id) where user_id <> (select user_id from user where login_id='%s')"%(uid)
	q="select *,vehicle.image as vimage from vehicle inner join user using (user_id) where user_id <> (select user_id from user where login_id='%s')"%(uid)
	
	res=select(q)

	data['data']=res
	data['status']="success"
	data['method']="Viewvehicle"
	return str(data)

@api.route('/Viewbuyrequrst')
def Viewbuyrequrst():
	data={}
	uid=request.args['login_id']
	q="select *,vehicle.image as image from buy inner join vehicle using (vehicle_id) inner join user using (user_id) where user_id=(select user_id from user where login_id='%s') "%(uid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewvehicle"
	return str(data)

@api.route('/Accept')
def Accept():
	data={}
	uid=request.args['login_id']
	bid=request.args['bid']
	q="update buy set status='Accept' where buy_id='%s'"%(bid)
	update(q)
	q="select * from buy where buy_id='%s'" %(bid)
	res=select(q)
	q="update vehicle set vstatus='nb',user_id='%s' where vehicle_id='%s'"%(res[0]['users_id'],res[0]['vehicle_id'])
	print(q)
	update(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewvehicle"
	return str(data)

@api.route('/Reject')
def Reject():
	data={}
	uid=request.args['login_id']
	bid=request.args['bid']
	q="update buy set status='Reject' where buy_id='%s'"%(bid)
	update(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewvehicle"
	return str(data)




@api.route('/Addvehicle',methods=['get','post'])
def Addvehicle():
	data={}
	fname=request.form['vehicle']
	login_id=request.form['login_id']
	detail=request.form['details']
	img=request.files['image']
	path="static/upload/"+str(uuid.uuid4())+img.filename
	img.save(path)
	q="insert into vehicle values(null,(select user_id from user where login_id='%s'),'%s','%s','%s','nb')"%(login_id,fname,detail,path)
	insert(q)
	import datetime
	d=datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")
	with open(compiled_contract_path) as file:
		contract_json = json.load(file)  # load contract info as JSON
		contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
		contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
		id=web3.eth.get_block_number()
	message = contract.functions.add_vehicle(id,int(login_id),fname,detail,path).transact()
	data['status']="success"
	return str(data)

@api.route('/sendsalerequest')
def sendsalerequest():
	data={}
	# uid=request.args['login_id']
	vid=request.args['vid']
	q="update vehicle set status='b' where vehicle_id='%s'"%(vid)
	update(q)
	# data['data']=res
	data['status']="success"
	data['method']="sendsalerequest"
	return str(data)


			


			