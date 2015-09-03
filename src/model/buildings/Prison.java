package model.buildings;

import java.util.List;

import model.LandModel;
import model.PlayerModel;
import model.TextTipModel;

import context.GameState;
import control.Control;

/**
 * 
 * 监狱 玩家到这里可以入狱或者发生其他事件
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class Prison extends Building {

	private String[] events = { "去监狱看望好友，", "被冤枉入狱，", "被监狱管理员抓去打扫卫生，" };

	private PlayerModel player;

	public Prison(int posX, int posY) {
		super(posX, posY);
		this.name = "监狱";
	}

	public String[] getEvents() {
		return events;
	}

	@Override
	public int getEvent() {
		return GameState.PRISON_EVENT;
	}
}
