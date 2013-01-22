import sys
import random

argv = sys.argv
#filename = argv[1] + ".graph"
n = int(argv[1])

print(n, int(random.uniform(14,120)))
for i in range(n):
  for j in range(i):
    print(int(random.uniform(1,1200)), end=" ")
  print()
for i in range(n):
  print(int(random.uniform(5,100)), end=" ")
print()
for i in range(n):
  print(int(random.uniform(2,48)), end=" ")
print()
for i in range(n):
  print(int(random.uniform(2,48)), end=" ")
