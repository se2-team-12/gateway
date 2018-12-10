# GWD Documentation

## Install psutil
Be sure to install the Python module, psuitl. Instructions for installation of pip and psuitl, courtesy of giampolo (<https://github.com/giampaolo/>) on GitHub, are below.

### Install pip
On Linux or via wget:

    wget https://bootstrap.pypa.io/get-pip.py -O - | python

On macOS or via curl:

    python < <(curl -s https://bootstrap.pypa.io/get-pip.py)

On Windows, download pip (<https://pip.pypa.io/en/latest/installing/>), open
cmd.exe and install it:

    C:\Python27\python.exe get-pip.py

### Install psutil
    pip install psutil

## Diagnostics
availableMem.py --> **int** available memory

cpuCount.py --> **int** cpus

battery.py --> **int** battery (200% means the gateway hardware does not have a battery)

osTest.py --> **str** os - Windows, Linux, or MacOS

freeMem.py --> **int** free memory

additionFPU.py --> **bool** CPU in good health?

primeNumber.py --> **int** number of prime #s generated

timestamp.py --> **float** epoch
