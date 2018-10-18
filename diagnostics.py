import time
import psutil

options = ['timestamp()', 'cpuBattery()', 'cpuCount()', 'availableMem()']

class Diagnostics():

	@staticmethod
	# returns diagnostics options
	def options():
		global options
		return options

	# returns time in epoch format
	@staticmethod
	def timestamp():
		timestamp = time.time()
		print('timestamp')
		print(timestamp)
		return timestamp

	# returns number of physical CPU cores (so NOT the number of logical CPUs)
	@staticmethod
	def cpuCount():
		cpuCount = psutil.cpu_count(False)
		print('cpuCount')
		print(cpuCount)
		return cpuCount

	# returns battery as a percent
	@staticmethod
	def cpuBattery():
		cpuBattery = psutil.sensors_battery().percent
		print('cpuBattery')
		print(cpuBattery)
		return cpuBattery

	# returns number of bytes of memory available
	@staticmethod
	def availableMem():
		availableMem = psutil.virtual_memory().available
		print('availableMem')
		print(availableMem)
		return availableMem


Diagnostics.options()