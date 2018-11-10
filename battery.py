# returns battery as a percent
import psutil
try:
    battery = psutil.sensors_battery().percent
except:
    # if there is no battery to check the percent of, 200% will be returned
    battery = 200

print(battery)
