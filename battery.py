# returns battery as a percent
import psutil
battery = psutil.sensors_battery().percent
print(battery)
