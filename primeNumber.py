counter = 10
count = 0
import time
mersenne_exp = [2, 3, 5, 7, 13, 17, 19, 31, 61, 89, 107, 127, 521, 607, 1279, 2203, 2281, 3217, 4253, 4425]

start_time = time.time()
for i in mersenne_exp:
    mersenne = (2 ** i) - 1
    count += 1
    print('(2^'+str(i)+')-1: ', mersenne)
end_time = time.time()

print('It took', end_time-start_time, 'seconds to calculate', len(mersenne_exp), 'mersenne primes.')
