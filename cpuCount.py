# returns number of physical CPU cores (so NOT the number of logical CPUs)
import psutil
cpuCount = psutil.cpu_count(False)
print(cpuCount)