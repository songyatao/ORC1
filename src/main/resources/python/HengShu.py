# -*- coding: utf-8 -*-
import sys

import cv2
import imageio
import numpy as np
import matplotlib.pyplot as plt
import os
import glob



def mark_strokes(image_path):
    abs_image_path = os.path.abspath(image_path)

    with open(abs_image_path, 'rb') as f:
        image_array = np.asarray(bytearray(f.read()), dtype=np.uint8)
        image = cv2.imdecode(image_array, cv2.IMREAD_COLOR)

    if image is None:
        print(f"无法加载图像: {image_path}")
        return

    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    edges = cv2.Canny(gray, 50, 150)
    kernel = np.ones((5, 5), np.uint8)
    edges_dilated = cv2.dilate(edges, kernel, iterations=1)
    lines = cv2.HoughLinesP(edges_dilated, 1, np.pi / 180, threshold=60, minLineLength=5, maxLineGap=5)
    line_image = np.zeros_like(image)

    if lines is not None:
        for line in lines:
            x1, y1, x2, y2 = line[0]
            angle = np.degrees(np.arctan2(y2 - y1, x2 - x1))
            if abs(angle) < 30:
                cv2.line(line_image, (x1, y1), (x2, y2), (0, 0, 255), 3)
            elif abs(angle) > 80:
                cv2.line(line_image, (x1, y1), (x2, y2), (255, 0, 0), 3)
            elif 30 <= angle <= 80:
                cv2.line(line_image, (x1, y1), (x2, y2), (0, 255, 0), 3)
            elif -80 <= angle <= -30:
                cv2.line(line_image, (x1, y1), (x2, y2), (0, 255, 255), 3)

    result_image = cv2.addWeighted(image, 0.8, line_image, 1, 0)

    result_folder = os.path.dirname(image_path)
    stroke_folder = os.path.join(result_folder, 'stroke')
    os.makedirs(stroke_folder, exist_ok=True)

    base_name = os.path.basename(image_path)
    name, ext = os.path.splitext(base_name)

    # 处理可能包含汉字的文件名
    safe_name = name
    result_image_path = os.path.join(stroke_folder, f"{safe_name}_stroke{ext}")

    print(f"保存到: {result_image_path}")  # 打印保存路径

    # 处理保存时的潜在错误
    try:
        imageio.imwrite(result_image_path, result_image)
        print(f"图像已成功保存: {result_image_path}")
    except Exception as e:
        print(f"保存图像失败: {result_image_path}, 错误信息: {e}")

    result_image_rgb = cv2.cvtColor(result_image, cv2.COLOR_BGR2RGB)
    plt.imshow(result_image_rgb)
    plt.axis('off')
    plt.show()


def read(base_folder):
    image_files = []
    for folder_name in os.listdir(base_folder):
        folder_path = os.path.join(base_folder, folder_name)
        if os.path.isdir(folder_path):
            print(f"处理文件夹: {folder_name}")
            # 读取所有常见图片格式
            image_files.extend(glob.glob(os.path.join(folder_path, '*.[jp]*g')))
            image_files.extend(glob.glob(os.path.join(folder_path, '*.png')))
            image_files.extend(glob.glob(os.path.join(folder_path, '*.bmp')))
            image_files.extend(glob.glob(os.path.join(folder_path, '*.tiff')))
    return image_files


def main():
    base_folder = sys.argv[1]

    print("开始处理文件夹...")
    image_files = read(base_folder)

    print(image_files)

    if not image_files:
        print("没有找到任何图像文件.")
        return

    for image_file in image_files:
        print(f"正在处理图像文件: {image_file}")
        if not os.path.isfile(image_file):
            print(f"文件不存在: {image_file}")
        else:
            mark_strokes(image_file)

    print("处理完成.")


if __name__ == "__main__":
    main()
