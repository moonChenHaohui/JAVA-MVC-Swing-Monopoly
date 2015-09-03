package ui;

import java.awt.Graphics;
import java.awt.Image;

import util.FileUtil;

import model.BackgroundModel;


/**
 * 
 * 背景更新层
 * 
 * @author MOVELIGHTS
 * 
 */
public class Background extends Layer {

	/**
	 * 背景图片
	 */
	private Image bg = null;
	/**
	 * 
	 * 背景模型
	 * 
	 */
	private BackgroundModel background = null;
	private JPanelGame panel;

	protected Background(int x, int y, int w, int h,
			BackgroundModel background,JPanelGame panel) {
		super(x, y, w, h);
		this.background = background;
		this.panel = panel;
	}

	public void paint(Graphics g) {
		// 绘制背景
		this.paintBg(g);
	}
	/**
	 * 
	 * 将窗体隐藏
	 * 
	 */
	public void moveToBack() {
		this.panel.getLayeredPane().moveToBack(this);
	}

	/**
	 * 
	 * 将窗体显现
	 * 
	 */
	public void moveToFront() {
		this.panel.getLayeredPane().moveToFront(this);
	}
	
	/**
	 * 
	 * 背景绘制方法
	 * 
	 */
	public void paintBg(Graphics g){
		g.drawImage(this.bg, 0, 0, this.bg.getWidth(null),
				this.bg.getHeight(null), 0, 0, this.bg.getWidth(null),
				this.bg.getHeight(null), null);
	}
	

	@Override
	public void startPanel() {
		this.bg = background.getBg();
	}

}
