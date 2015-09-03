package model.buildings;

import java.awt.Image;

import javax.swing.ImageIcon;

import model.EventsModel;
import model.PlayerModel;
import context.GameState;
import control.Control;

/**
 * 
 * 公园 与世无争的公园，角色到这里什么大事也不会发生
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class Park extends Building {
	/*
	 * 
	 * 事件图片
	 */
	private Image[] imgageEvents = { EVENT_PARK_1, EVENT_PARK_2, EVENT_PARK_3,
			EVENT_PARK_4 };
	/**
	 * 事件图片
	 */
	public static Image EVENT_PARK_1 = new ImageIcon("images/event/park01.jpg")
			.getImage();
	/**
	 * 事件图片
	 */
	public static Image EVENT_PARK_2 = new ImageIcon("images/event/park02.jpg")
			.getImage();
	/**
	 * 事件图片
	 */
	public static Image EVENT_PARK_3 = new ImageIcon("images/event/park03.jpg")
			.getImage();
	/**
	 * 事件图片
	 */
	public static Image EVENT_PARK_4 = new ImageIcon("images/event/park04.jpg")
			.getImage();


	public Park(int posX, int posY) {
		super(posX, posY);
		this.name = "公园";
	}
	
	public Image[] getImgageEvents() {
		return imgageEvents;
	}

	@Override
	public int getEvent() {
		return GameState.PARK_EVENT;
	}

}
