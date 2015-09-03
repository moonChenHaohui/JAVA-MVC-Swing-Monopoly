package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;

import model.PlayerModel;

/**
 * 
 * 玩家信息面板刷新
 * 
 * @author MOVELIGHTS
 * 
 */
public class PlayersPanel extends Layer {

	private List<PlayerModel> players = null;

	protected PlayersPanel(int x, int y, int w, int h, List<PlayerModel> players) {
		super(x, y, w, h);
		this.players = players;
	}

	/**
	 * 
	 * 玩家信息显示面板绘制
	 * 
	 */
	public void paintPlayerInformation(Graphics g) {
		int tempX = 0;
		tempX += 30;
		for (PlayerModel temp : players) {
			// 玩家信息面板绘制
			paintPlayerPanel(temp, g, tempX, 15);
			tempX += 80;
		}
	}

	/**
	 * 
	 * 玩家信息面板绘制
	 * 
	 */
	private void paintPlayerPanel(PlayerModel player, Graphics g, int x,
			int y) {
		// 玩家信息字符串
		String[] information = { player.getName(),
				Integer.toString(player.getCash()) + " 金币",
				Integer.toString(player.getNx()) + " 点卷",
				Integer.toString(player.getBuildings().size()) + " 房屋",
				Integer.toString(player.getCards().size()) + "卡片" };
		// 头像(y += 60) + 20
		g.drawImage(player.getIMG("mini_02"), x -26 + 15 , y - 10, x -26 + 15 +player.getIMG("mini_02").getWidth(null) ,
				 y - 10 +player
					.getIMG("mini_02").getHeight(null) , 0, 0, player.getIMG("mini_02").getWidth(null), player
						.getIMG("mini_02").getHeight(null), null);
		y += 48;
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font(null,0,14));
		// 信息重绘
		FontMetrics fm = g.getFontMetrics();
		for (int k = 0; k < information.length; g.drawString(information[k], x
				+ (45 - fm.stringWidth(information[k])), y += 30), k++)
			;

	}

	@Override
	public void paint(Graphics g) {
		this.createWindow(g);
		// 玩家信息显示面板重绘
		this.paintPlayerInformation(g);
		
	}

	@Override
	public void startPanel() {
	}

}
