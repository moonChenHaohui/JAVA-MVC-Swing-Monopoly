package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.PlayerModel;
import model.buildings.Building;

/**
 * 
 * 降级卡,当前房屋降低一级（对手）
 * OK
 *
 */
public class ReduceLevelCard extends Card{

	public ReduceLevelCard(PlayerModel owner) {
		super(owner);
		this.name = "ReduceLevelCard";
		this.cName = "降级卡";
		this.price = 30;
	}

	@Override
	public int useCard() {
		return GameState.CARD_REDUCELEVEL;
		}

}
