package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import util.FrameUtil;

public class WaitFrame extends JFrame {

	public WaitFrame() {
		// 设置名字
		this.setTitle("迷你大富翁-Java版");
		// 设置默认关闭属性（程序结束）
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小
		this.setSize(380, 370);
		// 不允许用户改变窗口大小
		this.setResizable(false);
		// 居中
		FrameUtil.setFrameCenter(this);
		add(new JLabel("加载中，请稍后...",JLabel.CENTER));
		setVisible(true);
	}

}
