# returns number of bytes of memory available
import psutil
available_mem = psutil.virtual_memory().available
print("available memory")
print(available_mem)
