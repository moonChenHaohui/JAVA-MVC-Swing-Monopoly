package model.buildings;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.PlayerModel;

import context.GameState;
import control.Control;

import util.FileUtil;
import util.MyThread;

/**
 * 
 * 居民房 1-5级 平房 店铺 商场 商业大楼 摩天大楼
 * 
 * @author MOVELIGHTS
 * 
 */
public class House extends Building {

	private int upPrice;
	private String[] nameString = { "空地", "平房", "店铺", "商场", "商业大楼", "摩天大楼" };

	private PlayerModel player;

	public House(int posX, int posY) {
		super(posX, posY);
		this.maxLevel = 5;
	}

	public int getUpLevelPrice() {
		if (this.level == 0) {
			this.upPrice = 500;
		} else {
			this.upPrice = 1000 * this.level;
		}
		return upPrice;
	}
	
	/**
	 * 
	 * 获取房屋总价值
	 * 
	 * @return
	 */
	public int getAllPrice() {
		int price = 0;
		for (int i = 0; i <= level; i++) {
			if (this.level == 0) {
				price +=500;
			} else {
				price += 1000 * i;
			}
		}
		return price;
	}

	/**
	 * 税率
	 * 
	 * @return
	 */
	public int getRevenue() {
		/**
		 * 税率计算方法
		 */
		this.revenue = this.level * (int) (Math.random() * 1000)
				+ (this.level * 300);
		return revenue;
	}

	public String getName() {
		return this.nameString[this.level];
	}

	/**
	 * 获得上一级名称
	 * 
	 * @return
	 */
	public String getUpName() {
		if (this.level >= this.nameString.length - 1) {
			return "null";
		}
		return this.nameString[this.level + 1];
	}

	@Override
	public int getEvent() {
		return GameState.HUOSE_EVENT;
	}
}
