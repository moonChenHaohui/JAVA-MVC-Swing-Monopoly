package model.buildings;

import java.awt.Graphics;

import model.Port;
import model.PlayerModel;

import control.Control;

/**
 * 
 * 房屋抽象类
 * 
 * @author MOVELIGHTS
 * 
 */
public class Building{
	/**
	 * 
	 * 房屋拥有者
	 * 
	 */
	protected PlayerModel owner = null;

	/**
	 * 房屋名称
	 */
	protected String name;

	/**
	 * 可购买性
	 */
	protected boolean purchasability = false;

	/**
	 * 购买空地的价格
	 */
	protected int price;
	/**
	 * 税
	 */
	protected int revenue;
	/**
	 * 当前房屋等级
	 */
	protected int level;

	/**
	 * 
	 * 坐标
	 * 
	 */
	protected int posX;
	protected int posY;
	/**
	 * 最大等级
	 */
	protected int maxLevel;

	
	public Building(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public boolean isPurchasability() {
		return purchasability;
	}

	public void setPurchasability(boolean purchasability) {
		this.purchasability = purchasability;
	}

	/**
	 * 是否可以升级
	 */
	public boolean canUpLevel() {
		return this.level < maxLevel;
	}

	public PlayerModel getOwner() {
		return owner;
	}

	public void setOwner(PlayerModel owner) {
		this.owner = owner;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}
	public String getUpName() {
		return name;
	}

	public int getUpLevelPrice() {
		return price;
	}
	/**
	 * 
	 * 获取房屋总价值
	 * 
	 */
	public int getAllPrice() {
		return 0;
	}
	public int getRevenue() {
		return revenue;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	/**
	 * 触发事件
	 */
	public int getEvent() { return 0;}
	
	/**
	 * 路过事件
	 */
	public int passEvent() { return 0;}
	
	public void paint(Graphics g){}

	
}	
