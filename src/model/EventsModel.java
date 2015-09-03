package model;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import util.FileUtil;

import model.buildings.Point;

import control.Control;

/**
 * 
 * 事件信息
 * 
 * @author MOVELIGHTS
 * 
 */
public class EventsModel extends Tick implements Port {

	/**
	 * 显示图片源
	 */
	private Image img = null;
	/**
	 * 
	 * 半透明背景覆盖图 黑色
	 * 
	 */
	private Image BG_BRACK = new ImageIcon("images/event/bg_brack.png").getImage();
	/**
	 * 图片显示位置
	 */
	private Point imgPoint = null;
	private boolean imgShow = false;

	
	public Image getBG_BRACK() {
		return BG_BRACK;
	}

	public Image getImg() {
		return img;
	}

	public Point getImgPoint() {
		return imgPoint;
	}

	public boolean isImgShow() {
		return imgShow;
	}

	/**
	 * 
	 * 显示图片
	 * 
	 */
	public void showImg(Image img, int time, Point point) {
		this.img = img;
		this.imgPoint = point;
		this.imgShow = true;
		this.setStartTick(this.nowTick);
		this.setNextTick(this.nowTick + time * Control.rate);
	}

	@Override
	public void updata(long tick) {
		this.nowTick = tick;
	}

	@Override
	public void startGameInit() {
	}
}
