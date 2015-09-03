package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import util.FileUtil;

/**
 * 绘制层 抽象类
 * 
 * @author MOVELIGHTS
 * 
 */
public abstract class Layer extends JPanel{
	/**
	 * 窗口左上角x坐标
	 */
	protected int x;
	/**
	 * 窗口左上角y坐标
	 */
	protected int y;
	/**
	 * 窗口宽度
	 */
	protected int w;
	/**
	 * 窗口高度
	 */
	protected int h;

	protected static final int PADDING = 5;
	protected static final int SIZE = 2;
	protected static Image WINDOW_IMG = new ImageIcon("images/window/window.png").getImage();
	protected static int WINDOW_W = WINDOW_IMG.getWidth(null);
	protected static int WINDOW_H = WINDOW_IMG.getHeight(null);

	protected Layer(int x, int y, int w, int h) {
		this.setBounds(x, y, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setLocation (int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	/**
	 * 
	 *  边框绘制
	 * 
	 */
	public void createWindow(Graphics g) {
		g.drawImage(WINDOW_IMG, 0, 0, 0 + SIZE, 0 + SIZE, 0, 0, SIZE, SIZE,
				null);// 左上固定
		g.drawImage(WINDOW_IMG, 0 + SIZE, 0, 0 + w - SIZE, 0 + SIZE, SIZE, 0,
				WINDOW_W - SIZE, SIZE, null);// 中间
		g.drawImage(WINDOW_IMG, 0 + w - SIZE, 0, 0 + w, 0 + SIZE, WINDOW_W
				- SIZE, 0, WINDOW_W, SIZE, null);// 右上固定
		g.drawImage(WINDOW_IMG, 0, 0 + SIZE, 0 + SIZE, 0 + h - SIZE, 0, SIZE,
				SIZE, WINDOW_H - SIZE, null);// 中右
		g.drawImage(WINDOW_IMG, 0 + SIZE, 0 + SIZE, 0 + w - SIZE, 0 + h - SIZE,
				SIZE, SIZE, WINDOW_W - SIZE, WINDOW_H - SIZE, null);// 中中
		g.drawImage(WINDOW_IMG, 0 + w - SIZE, 0 + SIZE, 0 + w, 0 + h - SIZE,
				WINDOW_W - SIZE, SIZE, WINDOW_W, WINDOW_H - SIZE, null);// 中右
		g.drawImage(WINDOW_IMG, 0, 0 + h - SIZE, 0 + SIZE, 0 + h, 0, WINDOW_H
				- SIZE, SIZE, WINDOW_H, null);// 下左
		g.drawImage(WINDOW_IMG, 0 + SIZE, 0 + h - SIZE, 0 + w - SIZE, 0 + h,
				SIZE, 50 - SIZE, WINDOW_W - SIZE, WINDOW_H, null);// 下中
		g.drawImage(WINDOW_IMG, 0 + w - SIZE, 0 + h - SIZE, 0 + w, 0 + h,
				WINDOW_W - SIZE, WINDOW_H - SIZE, WINDOW_W, WINDOW_H, null);// 下右
	}
	
	abstract public void paint(Graphics g);
	/**
	 * 开始游戏panel设置
	 */
	abstract public void startPanel();
}
