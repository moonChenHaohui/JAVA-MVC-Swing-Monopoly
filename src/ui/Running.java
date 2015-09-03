package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import control.GameRunning;


public class Running extends Layer{
	
	private Image bg = new ImageIcon("images/string/days/bg.png").getImage();
	private Image[] numberIMG = {
			new ImageIcon("images/string/days/0.png").getImage(),
			new ImageIcon("images/string/days/1.png").getImage(),
			new ImageIcon("images/string/days/2.png").getImage(),
			new ImageIcon("images/string/days/3.png").getImage(),
			new ImageIcon("images/string/days/4.png").getImage(),
			new ImageIcon("images/string/days/5.png").getImage(),
			new ImageIcon("images/string/days/6.png").getImage(),
			new ImageIcon("images/string/days/7.png").getImage(),
			new ImageIcon("images/string/days/8.png").getImage(),
			new ImageIcon("images/string/days/9.png").getImage()
	};
	private Image bgDay = new ImageIcon("images/string/days/bg_day.png").getImage();
	private Image[] numberR = {
			new ImageIcon("images/string/days/r/0.png").getImage(),
			new ImageIcon("images/string/days/r/1.png").getImage(),
			new ImageIcon("images/string/days/r/2.png").getImage(),
			new ImageIcon("images/string/days/r/3.png").getImage(),
			new ImageIcon("images/string/days/r/4.png").getImage(),
			new ImageIcon("images/string/days/r/5.png").getImage(),
			new ImageIcon("images/string/days/r/6.png").getImage(),
			new ImageIcon("images/string/days/r/7.png").getImage(),
			new ImageIcon("images/string/days/r/8.png").getImage(),
			new ImageIcon("images/string/days/r/9.png").getImage()
	};
	private Image rule = new ImageIcon("images/string/days/rule.png").getImage();
	
	private GameRunning running = null;
	private JPanelGame panel;
	
	protected Running(int x, int y, int w, int h,GameRunning running,JPanelGame panel) {
		super(x, y, w, h);
		this.running = running;
		this.panel = panel;
	}

	@Override
	public void paint(Graphics g) {
		// 显示游戏天数
		g.drawImage(bg, 0, 0, bg.getWidth(null), bg.getHeight(null), 0, 0, bg.getWidth(null), bg.getHeight(null), null);
		// 显示数字
		int day = running.getDay();
		int posX = 100;
		int posY = 16;
		while ((int )day >0){
			int num = day % 10;
			g.drawImage(numberIMG[num], posX,posY,posX + numberIMG[num].getWidth(null),posY + numberIMG[num].getHeight(null),0,0,numberIMG[num].getWidth(null),numberIMG[num].getHeight(null),null);
			day /= 10;
			posX -= 26;
		}
		if (GameRunning.GAME_DAY > 0 ) {
			posY +=14;
			posX = 100;
			g.drawImage(bgDay, 0, posY, bgDay.getWidth(null),posY + bgDay.getHeight(null), 0, 0, bgDay.getWidth(null), bgDay.getHeight(null), null);
			int day_m = GameRunning.GAME_DAY ;
			posY +=16;
			while ((int )day_m >0){
				int num = day_m % 10;
				g.drawImage(numberR[num], posX,posY,posX + numberR[num].getWidth(null),posY + numberR[num].getHeight(null),0,0,numberR[num].getWidth(null),numberIMG[num].getHeight(null),null);
				day_m /= 10;
				posX -= 26;
			}
		}
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
	@Override
	public void startPanel() {
		
	}

}
