import psutil
import socket
import time


def main():
    # Hostname Info
    hostname = socket.gethostname()
    print("Hostname:", hostname)

    # CPU Info
    cpu_count = psutil.cpu_count()
    cpu_usage = psutil.cpu_percent(interval=1)
    print("CPU:Count:", cpu_count, "Usage:", cpu_usage)

    # Memory Info
    memory_stats = psutil.virtual_memory()
    memory_total = memory_stats.total
    memory_used = memory_stats.used
    memory_used_percent = memory_stats.percent
    print(
        "Memory:Percent:", memory_used_percent, "Total:", memory_total / 1e+6, "MB", "Used:", memory_used / 1e+6, "MB")


while True:
    main()
    print("-----------------------------------------------------------------")
    time.sleep(5)
