package model.buildings;


import model.LandModel;
import model.PlayerModel;
import context.GameState;
import control.Control;

/**
 * 
 * 医院 角色到达此地点，可以让病患出院；角色住院完毕后，会从这里继续游戏 住院时间：1 - 4天
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class Hospital extends Building {
	
	private String[] events = {
			"在医院检查出流感。",
			"碰上神经病患者，上前一起研究病情。",
			"在医院门口遇上打劫，被群殴，需要住院观察。",
			"在医院看望住院好友。"
	};
	
	public Hospital(int posX, int posY) {
		super(posX, posY);
		this.name = "医院";
	}
	public String[] getEvents() {
		return events;
	}
	@Override
	public int getEvent() {
		/*
		this.player = player;
		int days = (int) (Math.random() * 4) + 2;
		player.setInHospital(days);
		int random = (int) (Math.random() * events.length);
		String text = events[random];
		player.showTextTip(player.getName() + text + "停留" + (days - 1) + "天.", 3);
		new Thread(this).start();
		*/
		return GameState.HOSPITAL_EVENT;
	}
}
