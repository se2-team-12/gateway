import datetime

def diagnostics():
	diagnostic = []
	gwId = 1
	diagnostic.append(gwId)
	timestamp = datetime.datetime.now()
	diagnostic.append(str(timestamp))


	print("gwId")
	print(gwId)
	print("timestamp")
	print(str(timestamp))

diagnostics()
