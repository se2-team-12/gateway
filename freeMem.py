# returns number of bytes of free memory
import psutil
free_mem = psutil.virtual_memory().free
print("free memory")
print(free_mem)
