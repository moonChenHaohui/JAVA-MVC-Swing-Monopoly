package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.PlayerModel;

/**
 * 
 * 
 * 遥控骰子,使用遙控骰子，可以自由控制下一次骰子點數。
 * OK
 *
 */
public class ControlDiceCard extends Card{

	int diceNumber;
	
	public ControlDiceCard(PlayerModel owner) {
		super(owner);
		this.name = "ControlDiceCard";
		this.cName = "遥控骰子卡";
		this.price = 30;
	}

	@Override
	public int useCard() {
		/*
		Object[] options = { "1点", "2点","3点","4点","5点","6点","重新选择" };
		int response = JOptionPane.showOptionDialog(null,
				"确认使用\"遥控骰子卡\"遥控骰子点数?", "卡片使用阶段.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == -1 || response == 6){
			// 重新选择
			this.owner.useCards();
		} else {
			// 使用
			this.owner.getRunning().setPoint(response + 1);
			// 增加文本提示
			this.owner.showTextTip(this.owner.getName() + " 使用了 \"遥控骰子卡\".", 2);
			//　减去卡片
			this.owner.getCards().remove(this);
		}
		this.owner.useCards();
		*/
		return GameState.CARD_CONTROLDICE;
	}
}
