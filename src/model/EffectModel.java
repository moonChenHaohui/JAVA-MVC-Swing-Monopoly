package model;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import control.Control;

/**
 * 
 * 事件信息
 * 
 * @author MOVELIGHTS
 * 
 */
public class EffectModel extends Tick implements Port {

	Point loction = new Point(0,0);
	
	/**
	 * 显示图片源
	 */
	private Image[] img = null;
	/**
	 * 
	 * 火眼效果开始
	 * 
	 */
	private Image[] imgStart1 = {
			 new ImageIcon("images/effect/start1/0.png").getImage(),
			 new ImageIcon("images/effect/start1/1.png").getImage(),
			 new ImageIcon("images/effect/start1/2.png").getImage(),
			 new ImageIcon("images/effect/start1/3.png").getImage(),
			 new ImageIcon("images/effect/start1/4.png").getImage(),
			 new ImageIcon("images/effect/start1/5.png").getImage(),
			 new ImageIcon("images/effect/start1/6.png").getImage(),
			 new ImageIcon("images/effect/start1/7.png").getImage(),
			 new ImageIcon("images/effect/start1/8.png").getImage(),
			 new ImageIcon("images/effect/start1/9.png").getImage(),
			 new ImageIcon("images/effect/start1/10.png").getImage(),
			 new ImageIcon("images/effect/start1/11.png").getImage(),
			 new ImageIcon("images/effect/start1/12.png").getImage(),
			 new ImageIcon("images/effect/start1/13.png").getImage(),
			 new ImageIcon("images/effect/start1/14.png").getImage()
	};
	
	/**
	 * 
	 * WIN效果
	 * 
	 */
	private Image[] win = {
			 new ImageIcon("images/effect/win/0.png").getImage(),
			 new ImageIcon("images/effect/win/1.png").getImage(),
			 new ImageIcon("images/effect/win/2.png").getImage(),
			 new ImageIcon("images/effect/win/3.png").getImage(),
			 new ImageIcon("images/effect/win/4.png").getImage(),
			 new ImageIcon("images/effect/win/5.png").getImage(),
			 new ImageIcon("images/effect/win/6.png").getImage(),
			 new ImageIcon("images/effect/win/7.png").getImage(),
			 new ImageIcon("images/effect/win/8.png").getImage(),
			 new ImageIcon("images/effect/win/9.png").getImage(),
			 new ImageIcon("images/effect/win/10.png").getImage(),
			 new ImageIcon("images/effect/win/11.png").getImage(),
			 new ImageIcon("images/effect/win/12.png").getImage(),
			 new ImageIcon("images/effect/win/13.png").getImage()
	};
	/**
	 * 
	 * 圆滑效果开始
	 * 
	 */
	private Image[] imgStart2 = {
			 new ImageIcon("images/effect/start2/0.png").getImage(),
			 new ImageIcon("images/effect/start2/1.png").getImage(),
			 new ImageIcon("images/effect/start2/2.png").getImage(),
			 new ImageIcon("images/effect/start2/3.png").getImage(),
			 new ImageIcon("images/effect/start2/4.png").getImage(),
			 new ImageIcon("images/effect/start2/5.png").getImage(),
			 new ImageIcon("images/effect/start2/6.png").getImage(),
			 new ImageIcon("images/effect/start2/7.png").getImage(),
			 new ImageIcon("images/effect/start2/8.png").getImage(),
			 new ImageIcon("images/effect/start2/9.png").getImage(),
			 new ImageIcon("images/effect/start2/10.png").getImage()
	};
	/**
	 * 
	 * 游戏结束效果开始
	 * 
	 */
	private Image[] timeover = {
			 new ImageIcon("images/effect/timeover/0.png").getImage(),
			 new ImageIcon("images/effect/timeover/1.png").getImage(),
			 new ImageIcon("images/effect/timeover/2.png").getImage(),
			 new ImageIcon("images/effect/timeover/3.png").getImage(),
			 new ImageIcon("images/effect/timeover/4.png").getImage(),
			 new ImageIcon("images/effect/timeover/5.png").getImage(),
			 new ImageIcon("images/effect/timeover/6.png").getImage(),
			 new ImageIcon("images/effect/timeover/7.png").getImage(),
			 new ImageIcon("images/effect/timeover/8.png").getImage(),
			 new ImageIcon("images/effect/timeover/9.png").getImage(),
			 new ImageIcon("images/effect/timeover/10.png").getImage(),
			 new ImageIcon("images/effect/timeover/11.png").getImage(),
			 new ImageIcon("images/effect/timeover/12.png").getImage(),
			 new ImageIcon("images/effect/timeover/13.png").getImage(),
			 new ImageIcon("images/effect/timeover/14.png").getImage(),
			 new ImageIcon("images/effect/timeover/15.png").getImage(),
			 new ImageIcon("images/effect/timeover/16.png").getImage()
			 
	};
	
	/**
	 * 
	 * 游戏结束效果开始
	 * 
	 */
	private Image[] timeover2 = {
			 new ImageIcon("images/effect/timeover2/0.png").getImage(),
			 new ImageIcon("images/effect/timeover2/1.png").getImage(),
			 new ImageIcon("images/effect/timeover2/2.png").getImage(),
			 new ImageIcon("images/effect/timeover2/3.png").getImage(),
			 new ImageIcon("images/effect/timeover2/4.png").getImage(),
			 new ImageIcon("images/effect/timeover2/5.png").getImage(),
			 new ImageIcon("images/effect/timeover2/6.png").getImage(),
			 new ImageIcon("images/effect/timeover2/7.png").getImage(),
			 new ImageIcon("images/effect/timeover2/8.png").getImage(),
			 new ImageIcon("images/effect/timeover2/9.png").getImage(),
			 new ImageIcon("images/effect/timeover2/10.png").getImage(),
			 new ImageIcon("images/effect/timeover2/11.png").getImage(),
			 new ImageIcon("images/effect/timeover2/12.png").getImage(),
			 new ImageIcon("images/effect/timeover2/13.png").getImage()
	};
	/**
	 * 
	 * 游戏结束效果开始
	 * 
	 */
	private Image[] win_ = {
			 new ImageIcon("images/effect/win_/0.png").getImage(),
			 new ImageIcon("images/effect/win_/1.png").getImage(),
			 new ImageIcon("images/effect/win_/2.png").getImage(),
			 new ImageIcon("images/effect/win_/3.png").getImage(),
			 new ImageIcon("images/effect/win_/4.png").getImage(),
			 new ImageIcon("images/effect/win_/5.png").getImage(),
			 new ImageIcon("images/effect/win_/6.png").getImage(),
			 new ImageIcon("images/effect/win_/7.png").getImage(),
			 new ImageIcon("images/effect/win_/8.png").getImage(),
			 new ImageIcon("images/effect/win_/9.png").getImage(),
			 new ImageIcon("images/effect/win_/10.png").getImage(),
			 new ImageIcon("images/effect/win_/11.png").getImage(),
			 new ImageIcon("images/effect/win_/12.png").getImage(),
			 new ImageIcon("images/effect/win_/13.png").getImage()
	};
	/**
	 * 
	 * 游戏结束效果开始
	 * 
	 */
	private Image[] lose_ = {
			new ImageIcon("images/effect/lose_/0.png").getImage(),
			 new ImageIcon("images/effect/lose_/1.png").getImage(),
			 new ImageIcon("images/effect/lose_/2.png").getImage(),
			 new ImageIcon("images/effect/lose_/3.png").getImage(),
			 new ImageIcon("images/effect/lose_/4.png").getImage(),
			 new ImageIcon("images/effect/lose_/5.png").getImage(),
			 new ImageIcon("images/effect/lose_/6.png").getImage(),
			 new ImageIcon("images/effect/lose_/7.png").getImage(),
			 new ImageIcon("images/effect/lose_/8.png").getImage(),
			 new ImageIcon("images/effect/lose_/9.png").getImage(),
			 new ImageIcon("images/effect/lose_/10.png").getImage(),
			 new ImageIcon("images/effect/lose_/11.png").getImage(),
			 new ImageIcon("images/effect/lose_/12.png").getImage()
	};
	
	/**
	 * 每张图片显示间隔 （/帧数）
	 */
	private int imageShowGap = 3;
	
	/**
	 * 
	 * 显示图片
	 * 
	 */
	public void showImg(String effectName) {
		if (effectName.equals("start")){
			this.img = imgStart2;
		} else if (effectName.equals("win")) {
			this.img = win;
		} else if (effectName.equals("timeover")) {
			this.img = timeover;
		} else if (effectName.equals("timeover2")) {
			this.img = timeover2;
		} else if (effectName.equals("win_")) {
			this.img = win_;
		} else if (effectName.equals("lose_")) {
			this.img = lose_;
		}
		this.setStartTick(this.nowTick);
		this.setNextTick(this.nowTick + (img.length + 1) *(Control.rate / imageShowGap) - 1 );
	}

	public void showImg(String effectName,Point loction) {
		this.loction = loction;
		showImg(effectName);
	}
	public Image[] getImg() {
		return img;
	}

	public int getImageShowGap() {
		return imageShowGap;
	}

	@Override
	public void updata(long tick) {
		this.nowTick = tick;
	}

	@Override
	public void startGameInit() {
	}
}
