package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import control.Control;


import model.DiceModel;


/**
 * 
 * 骰子掷点层
 * 
 * @author MOVELIGHTS
 * 
 */
@SuppressWarnings("serial")
public class Dice extends Layer {

	private Control control;
	private DiceModel dice;
	private DiceButton diceButton;
	

	protected Dice(int x, int y, int w, int h, Control control) {
		super(x, y, w, h);
		setLayout(null);
		this.control = control;
		this.dice = control.getDice();
		this.diceButton = new DiceButton(control, 105, 32);
		add(diceButton);
	}

	@Override
	public void paint(Graphics g) {
		//窗口绘制
		this.createWindow(g);
		//骰子绘制
		this.paintDice(g, -12, -15);
		//骰子按钮显示
		this.showDice();
		// 骰子按钮刷新
		diceButton.update(g);
	}

	/**
	 * 
	 * 骰子绘制
	 * 
	 */
	private void paintDice(Graphics g, int i, int j) {
		// 设置骰子运动
		if (dice.getStartTick() < dice.getNowTick()
				&& dice.getNextTick() >= dice.getNowTick()) {
			dice.setDiceState(DiceModel.DICE_RUNNING);
		} else {
			dice.setDiceState(DiceModel.DICE_POINT);
		}

		if (dice.getDiceState() == DiceModel.DICE_POINT) {
			this.paintPoint(g, i, j);
		} else if (dice.getDiceState() == DiceModel.DICE_RUNNING) {
			this.paintRunning(g, i, j, dice.getNowTick() % 4 == 0);
		}
		g.setColor(Color.black);
		g.drawString(dice.getRunning().getNowPlayer().getName() + ":", i + 120,
				j + 45);
	}

	public DiceButton getDiceButton() {
		return diceButton;
	}

	/**
	 * 
	 * 骰子运动状态绘制
	 * 
	 */
	public void paintRunning(Graphics g, int x, int y, boolean change) {
		if (change) {
			dice.addImgCount(1);
		}
		Image temp = dice.getNowImg();
		g.drawImage(temp, x, y, x + temp.getWidth(null),
				y + temp.getHeight(null), 0, 0, temp.getWidth(null),
				temp.getHeight(null), null);
	}

	/**
	 * 
	 * 骰子产生点数绘制
	 * 
	 */
	public void paintPoint(Graphics g, int x, int y) {
		Image temp = dice.getDicePoints()[dice.getPoint()];
		g.drawImage(temp, x, y, x + temp.getWidth(null),
				y + temp.getHeight(null), 0, 0, temp.getWidth(null),
				temp.getHeight(null), null);
	}

	/**
	 * 
	 * 骰子按钮显示
	 * 
	 */
	private void showDice() {
		diceButton.setEnabled(dice.isShowDiceLabel());
	}

	@Override
	public void startPanel() {
	}

}
