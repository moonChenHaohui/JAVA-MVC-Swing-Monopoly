package ui;

import javax.swing.JFrame;

import util.FrameUtil;

@SuppressWarnings("serial")
public class JFrameGame extends JFrame {

	/**
	 * 
	 * 默认主panel
	 * 
	 * */
	private JPanelGame panelGame = null;
	
	public JFrameGame() {
		// 设置名字
		this.setTitle("迷你大富翁-Java版");
		// 设置默认关闭属性（程序结束）
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小
		this.setSize(750 + 200, 650);
		// 不允许用户改变窗口大小
		this.setResizable(false);
		// 居中
		FrameUtil.setFrameCenter(this);
		// 设置默认Panel
		this.panelGame = new JPanelGame();
		add(this.panelGame);
		
		// 设置边框显示
		this.setUndecorated(false);
	}

	public JPanelGame getPanelGame() {
		return panelGame;
	}
}
