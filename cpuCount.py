# returns number of physical CPU cores (so NOT the number of logical CPUs)
import os

cpu_count = os.cpu_count()

print("cpu count")
print(cpu_count)
