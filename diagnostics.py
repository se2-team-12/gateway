import time

options = ['timestamp()']

class Diagnostics():

	def options():
		global options
		return options

	def timestamp():
		timestamp = time.time()
		print("timestamp")
		print(timestamp)
		return timestamp