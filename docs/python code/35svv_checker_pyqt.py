import sys
import os
import math
from PyQt5.QtWidgets import (
    QApplication, QMainWindow, QWidget, QPushButton,
    QHBoxLayout, QVBoxLayout, QDialog,
    QLabel, QLineEdit, QFormLayout, QDialogButtonBox, QMessageBox
)
from PyQt5.QtCore import Qt, QTimer, QPointF
from PyQt5.QtGui import QPainter, QPen, QColor, QBrush, QLinearGradient


class SVVWidget(QWidget):
    """
    用于绘制 SVV（Subjective Visual Vertical）检查的自定义 QWidget：
    - 保留原始刻度（时针式短刻度）。
    - 抗锯齿 + 3D 圆柱管样式线条（穿过圆心、双向延长）。
    - 支持鼠标滚轮微调 ±0.1°。
    - 不再单独响应方向键（由 MainWindow 统一处理）。
    """

    def __init__(self, parent=None):
        super().__init__(parent)
        self.setMinimumSize(800, 800)

        # 不让此 widget 拦截方向键焦点，让 MainWindow 接管
        self.setFocusPolicy(Qt.NoFocus)

        # 当前角度（0°~360° 周期）
        self.angle = 0.0

        # 双击后固定，不再旋转
        self.fixed = False

        # 自动旋转状态与方向 ("CW" 或 "CCW" 或 None)
        self.auto_rotate = False
        self.rotate_direction = None

        # 速度设置
        self.base_speed = 50.0   # 50°/秒
        self.fine_speed = 0.1    # 0.1°/步

        # 定时器：用于匀速自动旋转
        self.timer = QTimer(self)
        self.timer_interval = 10  # 毫秒
        self.timer.setInterval(self.timer_interval)
        self.timer.timeout.connect(self.on_auto_rotate)

        # 是否显示参考圆盘（刻度 + 圆环）
        self.show_circle = True

    def paintEvent(self, event):
        """
        重写 paintEvent：开启抗锯齿，然后绘制参考圆盘与刻度，
        再绘制一条“3D 圆柱管”风格的线段，穿过圆心、支持双向延长。
        """
        painter = QPainter(self)
        painter.setRenderHint(QPainter.Antialiasing)

        w = self.width()
        h = self.height()
        cx = w / 2
        cy = h / 2
        radius = min(w, h) * 0.4

        # ——— 绘制参考圆盘与刻度 ———
        if self.show_circle:
            pen_circle = QPen(Qt.black, 2)
            painter.setPen(pen_circle)
            painter.drawEllipse(QPointF(cx, cy), radius, radius)

            for deg in range(0, 360):
                rad = math.radians(deg)
                x1 = cx + radius * math.cos(rad)
                y1 = cy + radius * math.sin(rad)
                inner = radius - 30
                x2 = cx + inner * math.cos(rad)
                y2 = cy + inner * math.sin(rad)

                if deg % 2 == 0:
                    pen_tick = QPen(QColor("green"), 2)
                else:
                    pen_tick = QPen(Qt.black, 1)
                painter.setPen(pen_tick)
                painter.drawLine(QPointF(x1, y1), QPointF(x2, y2))

        # ——— 绘制 3D 圆柱管风格的线段 ———
        angle_rad = math.radians(self.angle)
        length = radius

        x_end1 = cx + length * math.cos(angle_rad)
        y_end1 = cy + length * math.sin(angle_rad)
        x_end2 = cx - length * math.cos(angle_rad)
        y_end2 = cy - length * math.sin(angle_rad)

        gradient = QLinearGradient(QPointF(x_end1, y_end1), QPointF(x_end2, y_end2))
        gradient.setColorAt(0.0, QColor(200, 200, 200))
        gradient.setColorAt(0.5, QColor(80, 80, 80))
        gradient.setColorAt(1.0, QColor(200, 200, 200))

        brush = QBrush(gradient)
        pen_line = QPen(brush, 20)
        pen_line.setCapStyle(Qt.RoundCap)
        painter.setPen(pen_line)
        painter.drawLine(QPointF(x_end1, y_end1), QPointF(x_end2, y_end2))

        painter.end()

    def wheelEvent(self, event):
        """
        鼠标滚轮：向上滚动 Δ>0 → 顺时针微调 0.1°；
                 向下滚动 Δ<0 → 逆时针微调 0.1°。
        """
        if self.fixed:
            return
        delta = event.angleDelta().y() / 120
        if delta > 0:
            self.rotate_by(self.fine_speed)
        elif delta < 0:
            self.rotate_by(-self.fine_speed)

    def mousePressEvent(self, event):
        """
        单击左键：顺时针极小增量 0.001°；
        双击左键：固定线段，禁止后续任何旋转（父窗口的“停止旋转”也会被调用）。
        """
        if event.button() == Qt.LeftButton and not self.fixed:
            self.rotate_by(0.001)

    def mouseDoubleClickEvent(self, event):
        if event.button() == Qt.LeftButton:
            self.fixed = True
            self.stop_auto_rotate()

    # ——— 自动旋转相关 ———

    def toggle_auto_cw(self):
        if self.fixed:
            return
        if self.auto_rotate and self.rotate_direction == "CW":
            self.stop_auto_rotate()
        else:
            self.start_auto_rotate("CW")

    def toggle_auto_ccw(self):
        if self.fixed:
            return
        if self.auto_rotate and self.rotate_direction == "CCW":
            self.stop_auto_rotate()
        else:
            self.start_auto_rotate("CCW")

    def start_auto_rotate(self, direction):
        self.auto_rotate = True
        self.rotate_direction = direction
        self.timer.start()

    def stop_auto_rotate(self):
        self.auto_rotate = False
        self.rotate_direction = None
        self.timer.stop()

    def on_auto_rotate(self):
        if self.fixed:
            self.stop_auto_rotate()
            return
        delta_deg = self.base_speed * (self.timer_interval / 1000.0)
        if self.rotate_direction == "CW":
            self.rotate_by(delta_deg)
        else:
            self.rotate_by(-delta_deg)

    def rotate_by(self, delta):
        self.angle = (self.angle + delta) % 360
        self.update()

    # ——— 供外部调用：获取与 X/Y 轴夹角 ———

    def get_angle_with_horizontal(self):
        raw_h = abs(self.angle % 180)
        return min(raw_h, 180 - raw_h)

    def get_angle_with_vertical(self):
        raw_v = abs((self.angle - 90) % 180)
        return min(raw_v, 180 - raw_v)

    def toggle_circle(self):
        self.show_circle = not self.show_circle
        self.update()


class SaveDialog(QDialog):
    """
    “输出结果”对话框：要求输入 姓名、检查日期、MRN
    - 窗口尺寸 400×250
    - 字体 12pt
    """
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setWindowTitle("填写患者信息")
        self.setFixedSize(400, 250)

        font = self.font()
        font.setPointSize(12)
        self.setFont(font)

        self.name_edit = QLineEdit(self)
        self.name_edit.setFont(font)
        self.date_edit = QLineEdit(self)
        self.date_edit.setFont(font)
        self.mrn_edit = QLineEdit(self)
        self.mrn_edit.setFont(font)

        name_label = QLabel("姓名：")
        name_label.setFont(font)
        date_label = QLabel("检查日期：")
        date_label.setFont(font)
        mrn_label = QLabel("MRN：")
        mrn_label.setFont(font)

        form_layout = QFormLayout()
        form_layout.addRow(name_label, self.name_edit)
        form_layout.addRow(date_label, self.date_edit)
        form_layout.addRow(mrn_label, self.mrn_edit)

        btn_box = QDialogButtonBox(QDialogButtonBox.Ok | QDialogButtonBox.Cancel, self)
        btn_box.setFont(font)
        btn_box.accepted.connect(self.accept)
        btn_box.rejected.connect(self.reject)

        v_layout = QVBoxLayout()
        v_layout.addLayout(form_layout)
        v_layout.addWidget(btn_box)
        self.setLayout(v_layout)

    def get_inputs(self):
        return (
            self.name_edit.text().strip(),
            self.date_edit.text().strip(),
            self.mrn_edit.text().strip()
        )


class MainWindow(QMainWindow):
    """
    主窗口：
    - 顶部显示与 X/Y 轴的夹角标签
    - 中间为 SVVWidget（绘图区）
    - 底部一排按钮：
        ▶ 顺时针自动旋转
        ◀ 逆时针自动旋转
        ✦ 顺时针微调
        ✦ 逆时针微调
        ■ 停止旋转
        ◎ 显示/隐藏参考盘
        保存结果
        输出结果
        新病人
    - 同时支持快捷键：
        ← ：顺时针自动旋转
        → ：逆时针自动旋转
        ↑ ：保存结果
        ↓ ：停止旋转
        滚轮：微调 ±0.1°
    """

    def __init__(self):
        super().__init__()
        self.setWindowTitle("SVV Checker - PyQt5 高分辨率版")

        # 用于存储“保存结果”时记录的 (angle_h, angle_v)
        self.saved_angles = []

        # 绘图区
        self.svv_widget = SVVWidget(self)

        # —— 顶部标签：显示与 X/Y 轴夹角 —— 
        self.info_label = QLabel("X 轴夹角: 0.0°    Y 轴夹角: 0.0°")
        self.info_label.setAlignment(Qt.AlignCenter)
        self.info_label.setFixedHeight(30)
        info_font = self.info_label.font()
        info_font.setPointSize(12)
        self.info_label.setFont(info_font)

        # —— 底部按钮 —— 
        btn_cw = QPushButton("▶ 顺时针旋转")
        btn_cw.clicked.connect(self.svv_widget.toggle_auto_cw)

        btn_ccw = QPushButton("◀ 逆时针旋转")
        btn_ccw.clicked.connect(self.svv_widget.toggle_auto_ccw)

        btn_fine_cw = QPushButton("✦ 顺时针微调")
        btn_fine_cw.clicked.connect(lambda: self.svv_widget.rotate_by(self.svv_widget.fine_speed))

        btn_fine_ccw = QPushButton("✦ 逆时针微调")
        btn_fine_ccw.clicked.connect(lambda: self.svv_widget.rotate_by(-self.svv_widget.fine_speed))

        btn_stop = QPushButton("■ 停止旋转")
        btn_stop.clicked.connect(self.svv_widget.stop_auto_rotate)

        btn_toggle_circle = QPushButton("◎ 显示/隐藏参考盘")
        btn_toggle_circle.clicked.connect(self.on_toggle_circle)

        btn_save = QPushButton("保存结果")
        btn_save.clicked.connect(self.on_save_result)

        btn_export = QPushButton("输出结果")
        btn_export.clicked.connect(self.on_export_result)

        btn_newpatient = QPushButton("新病人")
        btn_newpatient.clicked.connect(self.on_new_patient)

        # 将按钮水平排列
        h_layout = QHBoxLayout()
        h_layout.addWidget(btn_cw)
        h_layout.addWidget(btn_ccw)
        h_layout.addWidget(btn_fine_cw)
        h_layout.addWidget(btn_fine_ccw)
        h_layout.addWidget(btn_stop)
        h_layout.addWidget(btn_toggle_circle)
        h_layout.addWidget(btn_save)
        h_layout.addWidget(btn_export)
        h_layout.addWidget(btn_newpatient)

        # 主布局：垂直堆叠——(标签 + 绘图区 + 按钮面板)
        v_layout = QVBoxLayout()
        v_layout.addWidget(self.info_label)
        v_layout.addWidget(self.svv_widget)
        v_layout.addLayout(h_layout)

        container = QWidget()
        container.setLayout(v_layout)
        self.setCentralWidget(container)

        # 启动时先更新一次顶部夹角标签
        self.update_info_label()

        # 让 MainWindow 本身获得焦点，以便捕获方向键等快捷操作
        self.setFocusPolicy(Qt.StrongFocus)

    def update_info_label(self):
        """
        从 svv_widget 获得当前角度，与 X/Y 轴夹角，更新顶部 info_label 文本。
        """
        ah = self.svv_widget.get_angle_with_horizontal()
        av = self.svv_widget.get_angle_with_vertical()
        self.info_label.setText(f"X 轴夹角: {ah:.1f}°    Y 轴夹角: {av:.1f}°")

    def keyPressEvent(self, event):
        """
        统一在主窗口层面捕获快捷键：
        ← (Key_Left)   ：切换顺时针自动旋转
        → (Key_Right)  ：切换逆时针自动旋转
        ↑ (Key_Up)     ：保存结果
        ↓ (Key_Down)   ：停止旋转
        其它按键交由父类处理
        """
        key = event.key()
        if key == Qt.Key_Left:
            self.svv_widget.toggle_auto_cw()
        elif key == Qt.Key_Right:
            self.svv_widget.toggle_auto_ccw()
        elif key == Qt.Key_Up:
            self.on_save_result()
        elif key == Qt.Key_Down:
            self.svv_widget.stop_auto_rotate()
        else:
            super().keyPressEvent(event)

    def on_toggle_circle(self):
        """
        点击“显示/隐藏参考盘”或快捷键触发后：切换显示状态，并刷新顶部夹角标签。
        """
        self.svv_widget.toggle_circle()
        self.update_info_label()

    def on_save_result(self):
        """
        保存当前与 X/Y 轴的夹角到列表，并弹出提示。
        """
        ah = self.svv_widget.get_angle_with_horizontal()
        av = self.svv_widget.get_angle_with_vertical()
        self.saved_angles.append((ah, av))
        QMessageBox.information(self, "已保存", f"已保存：X 轴 {ah:.1f}°，Y 轴 {av:.1f}°")

    def on_export_result(self):
        """
        输出结果：弹出输入对话框，填写 姓名/检查日期/MRN，然后把 saved_angles 写到桌面 TXT 文件中。
        """
        dlg = SaveDialog(self)
        if dlg.exec_() != QDialog.Accepted:
            return

        name, exam_date, mrn = dlg.get_inputs()
        if not name or not exam_date or not mrn:
            QMessageBox.warning(self, "输入不完整", "请填写 姓名、检查日期 和 MRN。")
            return

        if not self.saved_angles:
            QMessageBox.warning(self, "没有保存数据", "未发现任何已保存的角度数据，请先点击“保存结果”。")
            return

        desktop = os.path.join(os.path.expanduser("~"), "Desktop")
        safe_name = "".join(c for c in name if c not in r'\/:*?"<>|')
        safe_date = "".join(c for c in exam_date if c not in r'\/:*?"<>|')
        filename = f"{safe_name}_{safe_date}.txt"
        fullpath = os.path.join(desktop, filename)

        try:
            with open(fullpath, "w", encoding="utf-8") as f:
                f.write(f"姓名: {name}\n")
                f.write(f"检查日期: {exam_date}\n")
                f.write(f"MRN: {mrn}\n\n")
                f.write("已保存的角度数据 (X 轴, Y 轴, 单位: 度)：\n")
                for idx, (ah, av) in enumerate(self.saved_angles, start=1):
                    f.write(f"{idx}. X: {ah:.1f}°, Y: {av:.1f}°\n")

            QMessageBox.information(self, "导出成功", f"已将结果保存到文件：\n{fullpath}")
        except Exception as e:
            QMessageBox.critical(self, "导出失败", f"写入文件时出错：\n{e}")

    def on_new_patient(self):
        """
        点击“新病人”：清空 saved_angles 列表，并弹出提示告知用户。
        """
        self.saved_angles.clear()
        QMessageBox.information(self, "新病人", "已清除所有已保存的夹角数据。")


class SaveDialog(QDialog):
    """
    “输出结果”对话框：要求输入 姓名、检查日期、MRN
    - 窗口尺寸 400×250
    - 字体 12pt
    """
    def __init__(self, parent=None):
        super().__init__(parent)
        self.setWindowTitle("填写患者信息")
        self.setFixedSize(400, 250)

        font = self.font()
        font.setPointSize(12)
        self.setFont(font)

        self.name_edit = QLineEdit(self)
        self.name_edit.setFont(font)
        self.date_edit = QLineEdit(self)
        self.date_edit.setFont(font)
        self.mrn_edit = QLineEdit(self)
        self.mrn_edit.setFont(font)

        name_label = QLabel("姓名：")
        name_label.setFont(font)
        date_label = QLabel("检查日期：")
        date_label.setFont(font)
        mrn_label = QLabel("MRN：")
        mrn_label.setFont(font)

        form_layout = QFormLayout()
        form_layout.addRow(name_label, self.name_edit)
        form_layout.addRow(date_label, self.date_edit)
        form_layout.addRow(mrn_label, self.mrn_edit)

        btn_box = QDialogButtonBox(QDialogButtonBox.Ok | QDialogButtonBox.Cancel, self)
        btn_box.setFont(font)
        btn_box.accepted.connect(self.accept)
        btn_box.rejected.connect(self.reject)

        v_layout = QVBoxLayout()
        v_layout.addLayout(form_layout)
        v_layout.addWidget(btn_box)
        self.setLayout(v_layout)

    def get_inputs(self):
        return (
            self.name_edit.text().strip(),
            self.date_edit.text().strip(),
            self.mrn_edit.text().strip()
        )


def main():
    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    # 让主窗口获取焦点，以便捕获方向键
    window.setFocus()
    sys.exit(app.exec_())


if __name__ == "__main__":
    main()
