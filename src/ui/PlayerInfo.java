package ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import control.GameRunning;

import model.PlayerModel;

/**
 * 
 * 记载一个玩家面板 用于游戏结束时显示
 * 
 * @author MOVELIGHTS
 * 
 */
@SuppressWarnings("serial")
public class PlayerInfo extends JPanel {

	private Image bg = new ImageIcon("images/end/bg.png").getImage();
	private Image str = new ImageIcon("images/end/str.png").getImage();
	private Image none = new ImageIcon("images/end/none.png").getImage();
	private Image win = new ImageIcon("images/end/win.png").getImage();
	private Image lose = new ImageIcon("images/end/lose.png").getImage();
	private Image[] numberIMG = {
			new ImageIcon("images/end/number/0.png").getImage(),
			new ImageIcon("images/end/number/1.png").getImage(),
			new ImageIcon("images/end/number/2.png").getImage(),
			new ImageIcon("images/end/number/3.png").getImage(),
			new ImageIcon("images/end/number/4.png").getImage(),
			new ImageIcon("images/end/number/5.png").getImage(),
			new ImageIcon("images/end/number/6.png").getImage(),
			new ImageIcon("images/end/number/7.png").getImage(),
			new ImageIcon("images/end/number/8.png").getImage(),
			new ImageIcon("images/end/number/9.png").getImage()
	};
	
	private PlayerInfoButton button;
	
	private int x, y, w, h;

	private Point origin = new Point();

	private List<PlayerModel> players;
	private JPanelGame panel;

	public PlayerInfo(List<PlayerModel> players, JPanelGame panel) {
		this.players = players;
		this.panel = panel;
		setLayout(null);
		initBounds();
		button = new PlayerInfoButton("", 200 - 58, 430);
		add(button);
		addListener();
	}

	private void initBounds() {
		this.x = (950 - bg.getWidth(null)) / 2;
		this.y = (650 - bg.getHeight(null)) / 2;
		this.w = bg.getWidth(null);
		this.h = bg.getHeight(null);
		setBounds(x, y, w, h);
	}

	/**
	 * 
	 * 将窗体隐藏
	 * 
	 */
	public void moveToBack() {
		this.panel.getLayeredPane().moveToBack(this);
	}

	/**
	 * 
	 * 将窗体显现
	 * 
	 */
	public void moveToFront() {
		this.panel.getLayeredPane().moveToFront(this);
	}

	public void paint(Graphics g) {
		this.setOpaque(false); // 背景透明
		g.drawImage(bg, 0, 0, w, h, this);
		drawPlayers(g);
		button.update(g);
	}

	private void drawPlayers(Graphics g) {
		int y = 92;
		for (PlayerModel a : players) {
			drawPlayer(g,a,y);
			y += 180;
		}
		String str = "";
		if (GameRunning.day >= GameRunning.GAME_DAY) {
			str ="达到游戏天数 "+GameRunning.GAME_DAY+" 天.";
		}
		//最大金钱
		PlayerModel p1 = players.get(0);
		PlayerModel p2 = players.get(1);
		if (GameRunning.MONEY_MAX > 0 && p1.getCash() >= GameRunning.MONEY_MAX) {
			str ="\"" + p1.getName() +"\" 金钱达到游戏金钱上限.";
		} else if (GameRunning.MONEY_MAX > 0 && p2.getCash() >= GameRunning.MONEY_MAX) {
			str ="\"" + p2.getName() +"\" 金钱达到游戏金钱上限.";
		}
		// 破产
		if (p1.getCash() < 0 ){
			str ="\"" + p1.getName() +"\"破产.";
		} else if (p2.getCash() < 0 ){
			str ="\"" + p2.getName() +"\"破产.";
		}
		FontMetrics fm = g.getFontMetrics();
		g.drawString("结束原因："+str, 200 - fm.stringWidth(str)/2, 86);
	}

	private void drawPlayer(Graphics g, PlayerModel player,int y) {
		Image h5 = (player.getCash() > player.getOtherPlayer().getCash())? player.getIMG("smile"):player.getIMG("sad");
		Image out = (player.getCash() > player.getOtherPlayer().getCash())? win:lose;
		g.drawImage(str, 44 + 130, y + 40, 44 + 130 + str.getWidth(null), y + 40 + str.getHeight(null), 0, 0, str.getWidth(null), str.getHeight(null), null);
		g.drawImage(h5, 44, y + 40 - 14, 44 + h5.getWidth(null), y + 40 - 14 + h5.getHeight(null), 0, 0, h5.getWidth(null), h5.getHeight(null), null);
		g.setFont(new Font(null,1,16));
		int posX = 44 + 130 + str.getWidth(null) + 10;
		int posY =  y + 40 + 16;
		g.drawString(player.getName(), posX,posY);
		int cash = player.getCash();
		int cPosX = posX + 70;
		int cPosY = posY + 14;
		if (cash <= 0 ){
			cPosX-=70;
			g.drawImage(none, cPosX, cPosY, cPosX +none.getWidth(null), cPosY + none.getHeight(null), 0,0,none.getWidth(null), none.getHeight(null),null);
		}
		while ((int )cash > 0){
			int num = cash % 10;
			g.drawImage(numberIMG[num], cPosX,cPosY,cPosX + numberIMG[num].getWidth(null),cPosY + numberIMG[num].getHeight(null),0,0,numberIMG[num].getWidth(null),numberIMG[num].getHeight(null),null);
			cash /= 10;
			cPosX -= 16;
		}
		int nx = player.getNx();
		int nPosX = posX + 70;
		int nPosY = posY + 14 + 29;
		if (nx <= 0 ){
			nPosX-=70;
			g.drawImage(none, nPosX, nPosY, nPosX +none.getWidth(null), nPosY + none.getHeight(null), 0,0,none.getWidth(null), none.getHeight(null),null);
		}
		while ((int )nx > 0){
			int num = nx % 10;
			g.drawImage(numberIMG[num], nPosX,nPosY,nPosX + numberIMG[num].getWidth(null),nPosY + numberIMG[num].getHeight(null),0,0,numberIMG[num].getWidth(null),numberIMG[num].getHeight(null),null);
			nx /= 10;
			nPosX -= 16;
		}
		int buildings = player.getBuildings().size();
		int bPosX = posX + 70;
		int bPosY = posY + 14 + 29 + 29;
		if (buildings <= 0 ){
			bPosX-=70;
			g.drawImage(none, bPosX, bPosY, bPosX +none.getWidth(null),bPosY + none.getHeight(null), 0,0,none.getWidth(null), none.getHeight(null),null);
		}
		while ((int )buildings > 0){
			int num = buildings % 10;
			g.drawImage(numberIMG[num], bPosX,bPosY,bPosX + numberIMG[num].getWidth(null),bPosY + numberIMG[num].getHeight(null),0,0,numberIMG[num].getWidth(null),numberIMG[num].getHeight(null),null);
			buildings /= 10;
			bPosX -= 16;
		}
		g.drawImage(out, 44 + 130 + 187, y + 40 - 28, 44 + 130 + 187 + out.getWidth(null), y + 40 - 28 + out.getHeight(null), 0, 0, out.getWidth(null), out.getHeight(null), null);
		
	}

	private void addListener() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { // 按下
				origin.x = e.getX(); // 当鼠标按下的时候获得窗口当前的位置
				origin.y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) { // 拖动
				x += e.getX() - origin.x;
				y += e.getY() - origin.y;
				if (x < 0) {
					x = 0;
				}
				if (x + w > 950) {
					x = 950 - w;
				}
				if (y < 0) {
					y = 0;
				}
				if (y + h > 650) {
					y = 650 - h;
				}
				setBounds(x, y, w, h);
			}
		});
	}
}
