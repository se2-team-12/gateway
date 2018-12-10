# returns the OS type
import psutil

isMac = psutil.MACOS
isWindows = psutil.WINDOWS
isLinux = psutil.LINUX
os = 'Unknown'

if isMac:
    os = 'MacOS'
elif isLinux:
    os = 'Linux'
elif isWindows:
    os = 'Windows'
print("Operating system")
print(os)
