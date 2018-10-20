# returns battery as a percent
import psutil
cpu_battery = psutil.sensors_battery().percent
print(cpu_battery)
