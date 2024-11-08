import sys
import tkinter as tk
from tkinter import ttk
from PIL import Image, ImageTk

class ImageViewer:
    def __init__(self, master, image_path):
        self.master = master
        self.master.title("Image Viewer")

        # 将 image_path 存储为实例属性
        self.image_path = image_path

        # 创建一个框架用于图片和滚动条
        self.image_frame = tk.Frame(master)
        self.image_frame.pack(fill=tk.BOTH, expand=True)

        # 创建 Canvas 用于显示图片
        self.canvas = tk.Canvas(self.image_frame)
        self.canvas.grid(row=0, column=0, sticky=tk.NSEW)

        # 添加滚动条
        self.h_scrollbar = ttk.Scrollbar(self.image_frame, orient=tk.HORIZONTAL, command=self.canvas.xview)
        self.h_scrollbar.grid(row=1, column=0, sticky=tk.EW)
        self.v_scrollbar = ttk.Scrollbar(self.image_frame, orient=tk.VERTICAL, command=self.canvas.yview)
        self.v_scrollbar.grid(row=0, column=1, sticky=tk.NS)

        # 配置 Canvas 的滚动区域
        self.canvas.config(xscrollcommand=self.h_scrollbar.set, yscrollcommand=self.v_scrollbar.set)
        self.image_frame.grid_rowconfigure(0, weight=1)
        self.image_frame.grid_columnconfigure(0, weight=1)

        # 控件区放在独立的框架中，放在窗口底部
        self.control_frame = tk.Frame(master)
        self.control_frame.pack(side=tk.BOTTOM, fill=tk.X)

        # 添加滑块用于调整图片大小
        self.scale = tk.Scale(self.control_frame, from_=10, to=200, orient='horizontal', command=self.resize_image)
        self.scale.set(100)
        self.scale.pack(side=tk.LEFT)

        # 添加按钮
        self.restore_button = tk.Button(self.control_frame, text="初始图片", command=self.restore_original_size)
        self.restore_button.pack(side=tk.LEFT)

        self.original_image = None
        self.display_image = None

        # 加载指定图片
        self.load_image()

        # 键盘事件绑定
        self.master.bind("<plus>", self.on_zoom_in)    # + 键 -> 放大
        self.master.bind("<minus>", self.on_zoom_out)  # - 键 -> 缩小
        self.master.bind("r", self.on_restore_key)     # r 键 -> 恢复原大小

    def load_image(self):
        # 加载图片
        self.original_image = Image.open(self.image_path)
        self.display_image = self.original_image
        self.resize_to_fit()
        self.update_image()

    def resize_image(self, scale_value):
        # 调整图片大小
        scale_factor = int(scale_value) / 100.0
        if self.original_image:
            new_size = (int(self.original_image.width * scale_factor),
                        int(self.original_image.height * scale_factor))
            if new_size[0] > 0 and new_size[1] > 0:  # 防止无效大小
                self.display_image = self.original_image.resize(new_size, Image.Resampling.LANCZOS)
                self.update_image()

    def resize_to_fit(self):
        # 根据窗口大小自动缩放图片以适应窗口
        max_width, max_height = self.master.winfo_width(), self.master.winfo_height() - 100  # 减去底部控件高度
        if max_width == 0 or max_height == 0:
            return
        if self.original_image.width > max_width or self.original_image.height > max_height:
            scale_factor = min(max_width / self.original_image.width, max_height / self.original_image.height)
            new_size = (int(self.original_image.width * scale_factor),
                        int(self.original_image.height * scale_factor))
            if new_size[0] > 0 and new_size[1] > 0:
                self.display_image = self.original_image.resize(new_size, Image.Resampling.LANCZOS)
        else:
            self.display_image = self.original_image

    def restore_original_size(self):
        # 恢复图片到原始大小
        if self.original_image:
            self.display_image = self.original_image
            self.scale.set(100)
            self.update_image()

    def update_image(self):
        # 更新显示的图片并在 Canvas 上显示
        tk_image = ImageTk.PhotoImage(self.display_image)
        self.canvas.create_image(0, 0, anchor=tk.NW, image=tk_image)
        self.canvas.image = tk_image  # 防止图片被垃圾回收

        # 更新 Canvas 的滚动区域以适应新的图片大小
        self.canvas.config(scrollregion=self.canvas.bbox(tk.ALL))


    def on_zoom_in(self, event):
        # 增大缩放比例
        current_scale = self.scale.get()
        if current_scale < 200:  # 最大缩放200%
            self.scale.set(current_scale + 10)

    def on_zoom_out(self, event):
        # 缩小缩放比例
        current_scale = self.scale.get()
        if current_scale > 10:  # 最小缩放10%
            self.scale.set(current_scale - 10)

    def on_restore_key(self, event):
        self.restore_original_size()

if __name__ == "__main__":
    root = tk.Tk()
    root.geometry("800x600")  # 设置窗口大小
    # image_path = "D:\\Work\\OCR_Demo\\8.jpg"  # 在这里设置图片路径
    image_path = sys.argv[1]  # 在这里设置你的图片路径
    app = ImageViewer(root, image_path)
    root.mainloop()
