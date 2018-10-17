import time
import psutil

options = ['timestamp()','cpuBattery()', 'cpuTemps()']

class Diagnostics():

	def options():
		global options
		return options

	# returns time in epoch format
	def timestamp():
		timestamp = time.time()
		print("timestamp")
		print(timestamp)
		return timestamp

	# returns current, high, and critical temps in CELCIUS => float
	def cpuTemps():
		cpuTemps = psutil.temperatures()
		print("cpuTemps")
		print(cpuTemps)
		return cpuTemps

	# returns percent => int, secsleft => float, power_plugged => boolean
	def cpuBattery():
		cpuBattery = psutil.sensors_battery()
		print("cpuBattery")
		print(cpuBattery)
		return cpuBattery