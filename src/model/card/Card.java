package model.card;

import java.awt.Image;

import model.PlayerModel;


public abstract class Card {

	/**
	 * 
	 * 卡片英文名字
	 * 
	 */
	protected String name;
	/**
	 * 
	 * 卡片中文名字
	 * 
	 */
	protected String cName;
	
	/**
	 * 
	 * 卡片图片
	 * 
	 */
	protected Image img;
	
	/**
	 * 
	 * 拥有者
	 * 
	 * 
	 */
	protected PlayerModel owner;
	
	/**
	 * 
	 * 作用对象
	 * 
	 */
	protected PlayerModel eOwner;
	
	/**
	 * 
	 * 卡片价格
	 * 
	 */
	protected int price = 100;
	
	protected Card (PlayerModel owner) {
		this.owner =owner;
	}
	
	/**
	 * 
	 * 使用卡片效果
	 * 
	 * 
	 */
	public abstract int useCard ();
	/**
	 * 
	 *  卡片持续效果
	 * 
	 */
	public int cardBuff(){ return 0;}


	public String getName() {
		return name;
	}

	public Image getImg() {
		return img;
	}

	public PlayerModel getOwner() {
		return owner;
	}

	public void setOwner(PlayerModel owner) {
		this.owner = owner;
	}

	public int getPrice() {
		return price;
	}

	public String getcName() {
		return cName;
	}
	

	public PlayerModel geteOwner() {
		return eOwner;
	}

	public void seteOwner(PlayerModel eOwner) {
		this.eOwner = eOwner;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}
	
	
}
