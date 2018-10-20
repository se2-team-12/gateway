# returns number of physical CPU cores (so NOT the number of logical CPUs)
import psutil
cpu_count = psutil.cpu_count(False)
print(cpu_count)
