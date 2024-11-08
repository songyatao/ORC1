import sys

from PIL import Image, ImageDraw
import numpy as np
import os
from skimage import measure
from skimage.color import rgb2gray
from skimage.morphology import binary_erosion

# 图片文件夹路径
# folder_path = './result/Test'
folder_path = sys.argv[1]
# 获取文件夹中所有图片文件

# 定义不同的颜色
color_dict = {
    'jc': (0, 255, 0),  # 绿色
    'yb': (0, 0, 255)  # 红色
}

# 轮廓线条宽度
line_width = 10

for subfolder_name in os.listdir(folder_path):
    subfolder_path = os.path.join(folder_path, subfolder_name)

    if os.path.isdir(subfolder_path):
        # 获取子文件夹中所有图片文件
        image_files = [f for f in os.listdir(subfolder_path) if f.endswith(('jpg', 'jpeg', 'png'))]

        # 处理每个图片文件
        for image_file in image_files:
            # 读取图片
            image_path = os.path.join(subfolder_path, image_file)
            try:
                image = Image.open(image_path).convert('RGB')
            except Exception as e:
                print(f"无法打开图像文件 {image_file}: {e}")
                continue

            # 转换为numpy数组以便处理
            image_np = np.array(image)

            # 转换为灰度图
            gray = rgb2gray(image_np)

            # 使用阈值分割来找到轮廓
            binary = gray > 0.5
            binary = binary_erosion(binary)

            # 查找轮廓
            contours = measure.find_contours(binary, 0.5)

            # 创建一个空白图像来绘制轮廓
            contour_image = np.zeros_like(image_np)

            # 创建 PIL 图像的绘制对象
            contour_image_pil = Image.fromarray(contour_image.astype(np.uint8))
            draw = ImageDraw.Draw(contour_image_pil)

            # 根据文件名判断所用颜色
            for keyword, color in color_dict.items():
                if keyword in image_file:
                    # 绘制轮廓
                    for contour in contours:
                        for i in range(len(contour) - 1):
                            y1, x1 = contour[i]
                            y2, x2 = contour[i + 1]
                            # 使用线段绘制轮廓，线宽设为line_width
                            draw.line([(x1, y1), (x2, y2)], fill=color, width=line_width)

            # 合并原图像和轮廓图像
            result_image = Image.blend(image, contour_image_pil, alpha=0.3)

            # 确保结果子文件夹存在
            result_subfolder_path = os.path.join(subfolder_path, 'rcolor')
            if not os.path.exists(result_subfolder_path):
                os.makedirs(result_subfolder_path)

            # 组合结果子文件夹路径和文件名
            result_path = os.path.join(result_subfolder_path, f'result_color_{image_file}')

            # 保存图像
            result_image.save(result_path)

print('处理完成。')
