# fractal_dimension.py
import sys
import cv2
import numpy as np

def differential_box_counting(image, c=10):
    # 获取图像尺寸
    h, w = image.shape

    # 初始网格尺寸
    s_min = 2
    s_max = min(h, w) // 2
    sizes = np.arange(s_min, s_max, step=2)

    counts = []

    for s in sizes:
        N_s = 0
        for i in range(0, h, s):
            for j in range(0, w, s):
                box = image[i:i + s, j:j + s]
                g_min = box.min()
                g_max = box.max()
                delta_g = (g_max - g_min) / c
                N_s += int(delta_g) + 1

        counts.append(N_s)

    # 线性回归计算分形维数
    log_sizes = np.log(1 / sizes)
    log_counts = np.log(counts)
    coeffs = np.polyfit(log_sizes, log_counts, 1)
    fractal_dimension = coeffs[0]
    return fractal_dimension

# 获取图像路径
img_path = sys.argv[1]
image = cv2.imread(img_path, cv2.IMREAD_GRAYSCALE)

# 检查图像是否成功加载
if image is None:
    print("Error: Unable to load image. Please check the file path.")
else:
    # 计算并输出分形维数
    fd = differential_box_counting(image)
    print(fd)
