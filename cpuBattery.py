# returns battery as a percent
import psutil
cpuBattery = psutil.sensors_battery().percent
print(cpuBattery)