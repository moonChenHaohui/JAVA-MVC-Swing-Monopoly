package ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.PlayerModel;


public class GameShop implements MouseListener, MouseMotionListener {

	/**
	 * 游戏主窗口
	 */
	private JFrameGame mainFrame;

	/**
	 * 进入商店的玩家对象
	 */
	private PlayerModel player;
	private JFrame frame;
	private JPanel panel;
	/**
	 * 
	 * 全局的位置变量，用于表示鼠标在窗口上的位置
	 * 
	 */
	static Point origin = new Point();

	public GameShop(JFrameGame mainFrame, PlayerModel player) {
		this.mainFrame = mainFrame;
		this.player = player;
		// 创建面板
		//createWindow();
	}
	public static void main(String[] args) {
		new GameShop(null, null);
	}
	private void createWindow() {
		frame = new JFrame();
		panel = new JPanel();
		JLabel label = new JLabel("测试");
		panel.add(label);

		frame.add(new Background(0, 0, 0, 0, null,null));
		frame.setSize(300, 300);
		// 设置窗口中央。
		frame.setLocation(
				this.mainFrame.getX()
						+ ((this.mainFrame.getWidth() + this.frame.getWidth()) / 2),
				this.mainFrame.getY()
						+ ((this.mainFrame.getHeight() + this.frame.getHeight()) / 2));
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.addMouseMotionListener(this);
		frame.addMouseListener(this);
		frame.setVisible(true);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int w = this.mainFrame.getX() + this.mainFrame.getWidth();
		int h = this.mainFrame.getY() + this.mainFrame.getHeight();

		int x = e.getX() - this.frame.getWidth() / 2;
		int y = e.getY() - this.frame.getHeight() / 2;

		if (x + this.frame.getWidth() > w)
			x = w - this.frame.getWidth();
		if (y + this.frame.getHeight() > h)
			y = h - this.frame.getHeight();
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		frame.setLocation(x, y);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		origin.x = e.getX(); // 当鼠标按下的时候获得窗口当前的位置
		origin.y = e.getY();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
