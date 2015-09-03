package model.buildings;

import model.PlayerModel;
import context.GameState;
import control.Control;

/**
 * 
 * 起点 玩家通过时奖励金钱
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class Origin extends Building {
	/**
	 * 通过时奖励的金钱
	 */
	private int passReward;
	/**
	 * 停留时奖励金钱
	 */
	private int reward;

	private PlayerModel player;

	public Origin(int posX, int posY) {
		super(posX, posY);
		this.name = "起点";
		this.reward = 500;
		this.passReward = 200;
	}
	@Override
	public int getEvent() {
		return GameState.ORIGIN_EVENT;
	}
	
	public int getPassReward() {
		return passReward;
	}
	public int getReward() {
		return reward;
	}
	@Override
	public int passEvent() {
		return GameState.ORIGIN_PASS_EVENT;
	}
}
