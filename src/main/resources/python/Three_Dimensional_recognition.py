import sys

from PIL import Image, ImageDraw, ImageFont
import numpy as np
import cv2
import matplotlib.pyplot as plt
import os
import glob

# def read(base_folder):
#     image_files = []
#     for folder_name in os.listdir(base_folder):
#         folder_path = os.path.join(base_folder, folder_name)
#         if os.path.isdir(folder_path):
#             print(f"处理文件夹: {folder_name}")
#             image_files.extend(glob.glob(os.path.join(folder_path, '*.jpg')))
#     return image_files


def read(base_folder):
    image_files = []
    for folder_name in os.listdir(base_folder):
        folder_path = os.path.join(base_folder, folder_name)

        if os.path.isdir(folder_path):
            print(f"处理文件夹: {folder_name}")
            image_files.extend(glob.glob(os.path.join(folder_path, '*.jpg')))
    return image_files



base_folder = sys.argv[1] # 原图的地址
# 读取图片并转换为灰度图

color_thickness = (0, 255, 0)  # 绿色表示笔画粗细
color_depth = (255, 0, 0)      # 红色表示笔画深浅
color_std_dev = (0, 0, 255)    # 蓝色表示灰度变化
contour_color = (255, 255, 0)  # 黄色用于画轮廓

image_files = read(base_folder) # 返回的是所有图片文件
for image_file in image_files:
    img = Image.open(image_file).convert('RGB')
    gray = img.convert('L')  # 转换为灰度图

    # 将灰度图转换为numpy数组
    gray_np = np.array(gray)

    # 二值化
    _, binary = cv2.threshold(gray_np, 150, 255, cv2.THRESH_BINARY_INV)

    # 寻找轮廓
    contours, _ = cv2.findContours(binary, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # 将PIL图像转换为可编辑的绘图对象
    img_draw = ImageDraw.Draw(img)

    # 加载字体（确保字体路径正确）
    font = ImageFont.truetype("D:\\SWork\\SimHei.ttf", 20)

    # 遍历所有轮廓并计算笔画粗细、深浅和灰度变化
    for contour in contours:
        # 计算最小外接矩形
        x, y, w, h = cv2.boundingRect(contour)

        # 粗细估计为外接矩形的最小边长
        thickness = min(w, h)

        # 计算轮廓区域的平均灰度值和标准差
        mask = np.zeros(gray_np.shape, dtype=np.uint8)
        cv2.drawContours(mask, [contour], -1, 255, thickness=-1)
        mean_intensity = cv2.mean(gray_np, mask=mask)[0]
        std_dev = np.std(gray_np[mask == 255])

        # 将平均灰度值转换为深浅值（较低的灰度值表示较深的笔画）
        depth = 255 - mean_intensity

        # 在笔画中心位置标注粗细值、深浅值和灰度变化
        center_x, center_y = x + w // 2, y + h // 2

        # 标注笔画粗细（绿色）
        img_draw.text((center_x, center_y - 30), f'粗细: {thickness}', fill=color_thickness, font=font)

        # 标注笔画深浅（红色）
        img_draw.text((center_x, center_y), f'深度: {depth:.2f}', fill=color_depth, font=font)

        # 标注灰度变化（蓝色）
        img_draw.text((center_x, center_y + 30), f'灰度变化: {std_dev:.2f}', fill=color_std_dev, font=font)

        # 画轮廓（黄色）
        cv2.drawContours(gray_np, [contour], -1, contour_color, 2)

        # combined = Image.alpha_composite(image, overlay)

    # 获取原图所在的文件夹
    output_folder = os.path.dirname(image_file) + "\\three_dimensional\\"
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
        print("目录创建成功")
    else:
        print("目录已存在")
    output_filename = os.path.basename(image_file).replace('.jpg', '_three.jpg')  # 修改文件名
    output_path = os.path.join(output_folder, output_filename)

    img.save(output_path)  # 保存处理后的图像
    print(f"处理后的图像保存到: {output_path}")


    # output_file = os.path.join(base_folder, os.path.basename(image_file))
    # img.save(output_file)
    # print(f"已保存处理后的图片: {output_file}")

    # # 保存
    # directory = os.path.dirname(image_file) + "\\three_dimensional"
    # if not os.path.exists(directory):
    #     os.makedirs(directory)
    #     print("目录创建成功")
    # else:
    #     print("目录已存在")
    #
    # directory = os.path.dirname(image_file) + "\\three_dimensional\\" + os.path.basename(image_file)
    #
    # output_path = os.path.splitext(directory)[0] + '_annotated.png'
    # try:
    #     combined.save(output_path)
    # except Exception as e:
    #     print(e)
    #
    # print(f"已保存标注图像: {output_path}")



    # 显示标注后的图片
    # plt.imshow(img)
    # plt.axis('off')
    # plt.show()
