import os
import sys

import cv2
import numpy as np
import matplotlib.pyplot as plt

# 全局变量
dragging = False
ix, iy = -1, -1
tx, ty = 0, 0

def load_image(image_path):
    img = cv2.imread(image_path)
    if img is not None:
        return img
    else:
        print(f"Warning: Cannot read image {image_path}")
        return None

def preprocess_image(img):
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    _, binary = cv2.threshold(gray, 128, 255, cv2.THRESH_BINARY_INV)
    return binary

def resize_image(image, size):
    return cv2.resize(image, size, interpolation=cv2.INTER_AREA)

def manual_align(base_image, image_to_align):
    global dragging, ix, iy, tx, ty
    tx, ty = 0, 0  # 重置平移量
    scale_x, scale_y = 1.0, 1.0  # 初始化水平和垂直缩放比例
    angle = 0  # 初始化旋转角度

    def on_mouse(event, x, y, flags, param):
        global dragging, ix, iy, tx, ty
        if event == cv2.EVENT_LBUTTONDOWN:
            dragging = True
            ix, iy = x, y
        elif event == cv2.EVENT_MOUSEMOVE:
            if dragging:
                tx += x - ix
                ty += y - iy
                ix, iy = x, y
        elif event == cv2.EVENT_LBUTTONUP:
            dragging = False

    # 创建并调整窗口大小
    cv2.namedWindow('Align', cv2.WINDOW_NORMAL)
    cv2.resizeWindow('Align', 800, 800)  # 设置窗口尺寸为 800x800
    cv2.setMouseCallback('Align', on_mouse)

    while True:
        combined_image = base_image.copy()
        rows, cols = image_to_align.shape

        # 创建旋转矩阵
        center = (cols // 2, rows // 2)
        rotation_matrix = cv2.getRotationMatrix2D(center, angle, 1.0)  # 旋转角度 angle

        # 应用旋转变换到图像
        rotated_image = cv2.warpAffine(image_to_align, rotation_matrix, (cols, rows))

        # 应用平移和缩放
        M = np.float32([[scale_x, 0, tx], [0, scale_y, ty]])
        moved_image = cv2.warpAffine(rotated_image, M, (cols, rows))

        # 显示组合后的图像
        combined_image = cv2.addWeighted(combined_image, 0.5, moved_image, 0.5, 0)
        cv2.imshow('Align', combined_image)

        key = cv2.waitKey(1) & 0xFF
        if key == ord('q'):  # 按 'q' 键退出
            break
        elif key == ord('w'):  # 按 'w' 键增加垂直缩放比例
            scale_y += 0.01
        elif key == ord('s'):  # 按 's' 键减少垂直缩放比例
            scale_y = max(0.01, scale_y - 0.01)
        elif key == ord('a'):  # 按 'a' 键减少水平方向的缩放比例
            scale_x = max(0.01, scale_x - 0.01)
        elif key == ord('d'):  # 按 'd' 键增加水平方向的缩放比例
            scale_x += 0.01
        elif key == ord('e'):  # 按 'e' 键顺时针旋转
            angle += 1
        elif key == ord('r'):  # 按 'r' 键逆时针旋转
            angle -= 1

    cv2.destroyAllWindows()
    return moved_image

def generate_heatmap(image1, image2):
    base_image = preprocess_image(resize_image(image1, (500, 500)))
    heatmap = np.zeros_like(base_image, dtype=np.float32)

    # 对齐第二张图片
    preprocessed_img2 = preprocess_image(resize_image(image2, (500, 500)))
    aligned_image = manual_align(base_image, preprocessed_img2)

    # 将对齐后的第二张图片添加到热图
    heatmap += aligned_image.astype(np.float32)
    heatmap += base_image.astype(np.float32)

    heatmap /= 2  # 计算平均热图
    heatmap = (heatmap / heatmap.max() * 255).astype(np.uint8)
    return heatmap

def display_heatmap(heatmap):
    plt.imshow(heatmap, cmap='hot', interpolation='nearest')
    plt.colorbar()
    plt.show()

# 示例使用：指定两张图片的路径
image_path1 = sys.argv[1]  # 替换为第一张图片的实际路径
image_path2 = sys.argv[2]  # 替换为第二张图片的实际路径

# 加载图像
image1 = load_image(image_path1)
image2 = load_image(image_path2)

if image1 is not None and image2 is not None:
    heatmap = generate_heatmap(image1, image2)  # 仅使用前两张图片
    display_heatmap(heatmap)
else:
    print("加载图片失败。请检查路径。")
