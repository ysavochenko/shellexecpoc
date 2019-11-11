import fnmatch
import time
import os

images = ['*.jpg', '*.jpeg', '*.png', '*.tif', '*.tiff']
matches = []

start = time.time()

for root, dirnames, filenames in os.walk("/opt"):
    for extensions in images:
        for filename in fnmatch.filter(filenames, extensions):
            print(os.path.join(root, filename) + '\n')

time.sleep(10)
print("execution_time:")
print("--- %s seconds ---" % (time.time() - start))
