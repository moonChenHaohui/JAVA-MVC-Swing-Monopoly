package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.GameRunning;

import model.TextTipModel;

/**
 * 
 * 信息提示框
 * 
 * @author MOVELIGHTS
 * 
 */
public class TextTip extends Layer {

	private TextTipModel textTip = null;
	private Image bg = new ImageIcon("images/window/tip_01.png").getImage();
	
	private Point pointWindow = null;
	
	public TextTip(int x, int y, int w, int h, TextTipModel textTip) {
		super(x, y, w, h);
		this.pointWindow = new Point((x + w) / 2, (y + h) / 2);
		this.textTip = textTip;

	}


	@Override
	public void paint(Graphics g) {
		// 绘制信息面板
		paintTextTip(g, this);
	}

	/**
	 * 
	 * 绘制信息面板
	 * 
	 */
	private void paintTextTip(Graphics g, TextTip textTip2) {
		if (textTip.getStartTick() < textTip.getNowTick()
				&& textTip.getNextTick() >= textTip.getNowTick()) {
			this.pointWindow.x = textTip.getPlayer().getX() + 45;
			this.pointWindow.y =textTip.getPlayer().getY() + 10;
			g.drawImage(bg, pointWindow.x, pointWindow.y, pointWindow.x + bg.getWidth(null),
					pointWindow.y + bg.getHeight(null), 0, 0, bg.getWidth(null),
					bg.getHeight(null), null);
			// 绘制文字
			drawSting(g);
		}

	}

	/**
	 * 
	 * 绘制文字
	 * 
	 */
	private void drawSting(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		String str = this.textTip.getTipString();
		int maxSize = 13;
		int posY = 32;
		int front = 0;
		int rear = maxSize;
		while (front < str.length() - 1) {
			if (rear >= str.length()) {
				rear = str.length() - 1;
			}
			char[] temp = new char[maxSize];
			str.getChars(front, rear, temp, 0);
			// Char[] 转换成string
			String s = new String(temp);
			g.drawString(s, pointWindow.x + 20, pointWindow.y + posY);
			front = rear;
			rear += maxSize;
			posY += 20;
		}
	}

	@Override
	public void startPanel() {
	}
}
