package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import control.Control;
import control.GameRunning;


import ui.JPanelGame;
import util.FileUtil;

/**
 * 
 * 骰子
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class DiceModel extends Tick implements Port {

	/**
	 * 
	 * 骰子运动
	 * 
	 */
	private int point;
	private Image[] img = new Image[] {
			new ImageIcon("images/dice/dice_play_01.png").getImage(),
			new ImageIcon("images/dice/dice_play_02.png").getImage(),
			new ImageIcon("images/dice/dice_play_03.png").getImage(),
			new ImageIcon("images/dice/dice_play_04.png").getImage(),
			new ImageIcon("images/dice/dice_play_05.png").getImage() };
 	/**
	 * 
	 * 骰子点数图像
	 * 
	 */
	private Image[] dicePoints = new Image[] {
			new ImageIcon("images/dice/point/1.png").getImage(),
			new ImageIcon("images/dice/point/2.png").getImage(),
			new ImageIcon("images/dice/point/3.png").getImage(),
			new ImageIcon("images/dice/point/4.png").getImage(),
			new ImageIcon("images/dice/point/5.png").getImage(),
			new ImageIcon("images/dice/point/6.png").getImage() };

	/**
	 * 骰子按钮图片
	 */
	public  ImageIcon[] diceIMG = new ImageIcon[] {
			new ImageIcon("images/string/dice.png"),
			new ImageIcon("images/string/diceEnter.png"),
			new ImageIcon("images/string/dicePress.png"),
			new ImageIcon("images/string/diceBan.png")
	};
	/**
	 * 
	 * 游戏运行
	 * 
	 */
	private GameRunning running = null;

	/**
	 * 
	 * 图片滚动次数记载（用于图片的滚动显示）
	 * 
	 */
	private int imgCount;
	/**
	 * 
	 * 骰子正在滚动状态
	 * 
	 */
	public static int DICE_RUNNING = 1;
	/**
	 * 骰子产生点数状态
	 */
	public static int DICE_POINT = 2;
	/**
	 * 骰子当前状态
	 */
	private int diceState;
	/**
	 * 按钮显示控制
	 */
	boolean showButton;

	public DiceModel(GameRunning running) {
		this.running = running;
	}




	public void addImgCount(int add) {
		this.imgCount+=add;
	}




	public ImageIcon[] getDiceIMG() {
		return diceIMG;
	}




	public Image[] getDicePoints() {
		return dicePoints;
	}




	public int getImgCount() {
		return imgCount;
	}
	
	/**
	 * 
	 * 获取当前显示图片
	 * 
	 */
	public Image getNowImg(){
		this.imgCount = this.imgCount % this.img.length;
		return this.img[this.imgCount];
	}


	public void setDiceState(int diceState) {
		this.diceState = diceState;
	}

	public int getDiceState() {
		return diceState;
	}




	public void setShowButton(boolean showButton) {
		this.showButton = showButton;
	}




	@Override
	public void updata(long tick) {
		this.nowTick = tick;
		// 确认按钮状态
		this.checkButton();
	}

	/**
	 * 
	 * 确认按钮状态
	 * 
	 */
	private void checkButton() {
		if (this.running.getNowPlayerState() == GameRunning.STATE_THROWDICE) {// "掷点状态"
			this.showButton = true;
		} else {
			this.showButton = false;
		}
	}
	
	public GameRunning getRunning() {
		return running;
	}



	public boolean isShowDiceLabel() {
		return showButton;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	@Override
	public void startGameInit() {
		
		// 初始化骰子状态为“产生点数状态”
		this.diceState = DiceModel.DICE_POINT;
		// 初始化按钮可以点击
		this.showButton = true;
		// 骰子运动持续时间设定
		this.lastTime = Control.rate * 1;
	}

}
