# returns number of bytes of memory available
import psutil
availableMem = psutil.virtual_memory().available
print(availableMem)