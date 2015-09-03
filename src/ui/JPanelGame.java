package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import ui.massage.MassageOk;
import ui.massage.MassageSimple;
import ui.massage.MassageYesNo;
import control.Control;

@SuppressWarnings("serial")
public class JPanelGame extends JPanel{

	private JFrameGame gameFrame = null;
	private JLayeredPane layeredPane;

	private List<Layer> lays = null;
	private Background backgroundUI = null;
	private Lands landsUI = null;
	private Buildings buildingsUI = null;
	private Players playersUI = null;
	private TextTip textTip = null;
	private PlayersPanel layerPlayersPanel = null;
	private Dice dice = null;
	private Event event = null;
	private Shop shop = null;
	private Running running = null;
	private Effect effect = null;

	private PlayerInfo playerInfo = null;
	
	private MassageYesNo massageYesNo = null;
	private MassageOk massageOk = null;
	private MassageSimple massageSimple = null;

	private Control control = null;

	/**
	 * 全局左上角X
	 */
	public int posX = 100;
	/**
	 * 全局左上角Y
	 * */
	public int posY = 100;

	public JPanelGame() {
		setLayout(new BorderLayout());
		// 初始化游戏
		initGame();
	}

	/**
	 * 
	 * 初始化游戏
	 * 
	 */
	private void initGame() {
		// 添加控制器
		control = new Control();
		// 初始化UI
		initUI();
		// panel 传入控制器
		control.setPanel(this);
	}

	public Control getControl() {
		return control;
	}

	/**
	 * 
	 * 初始化UI
	 * 
	 */
	private void initUI() {
		// 创建背景UI
		this.backgroundUI = new Background(0, 0, 950, 650,
				control.getBackground(),this);
		// 创建土地UI
		this.landsUI = new Lands(posX, posY, 950, 650, control.getLand());
		// 创建房屋UI
		this.buildingsUI = new Buildings(posX, posY, 950, 650,
				control.getBuilding());
		// 创建玩家显示UI
		this.playersUI = new Players(posX, posY, 950, 650,control.getRunning(), control.getPlayers());
		// 玩家信息面板UI
		this.layerPlayersPanel = new PlayersPanel(posX + 64, posY + 66, 170,
				250, control.getPlayers());
		// 文字显示面板UI
		this.textTip = new TextTip(0,0,950,650,control.getTextTip());
		// 骰子事件UI
		this.dice = new Dice(posX + 64, posY + 320, 170, 90, control);
		// 事件显示UI
		this.event = new Event(0, 0, 950, 650, control.getEvents());
		// 商店界面UI
		this.shop = new Shop(0, 0, 750, 650, control, this);
		// 游戏运转界面UI
		this.running = new Running(780, 0, 200, 80, control.getRunning(),this);
		// 场景效果UI
		this.effect = new Effect(0, 0, 950, 650, control.getEffect(),this);
		// 玩家信息面板显示
		this.playerInfo = new PlayerInfo(control.getPlayers(),this);
		// 对话UI
		this.massageYesNo = new MassageYesNo("选择框", "创建一个对话框", this);
		// 对话UI
		this.massageOk = new MassageOk("确定框", "创建一个对话框", this);
		// 对话UI
		this.massageSimple = new MassageSimple("多选框", "创建一个对话框", this);

		// lays存放所有panel组件
		lays = new ArrayList<Layer>();
		lays.add(backgroundUI);
		lays.add(dice);
		lays.add(playersUI);
//		lays.add(textTip);
		lays.add(layerPlayersPanel);
		lays.add(buildingsUI);
		lays.add(landsUI);
		lays.add(backgroundUI);
		lays.add(running);
		lays.add(effect);
		// lays.add(shop);
		// lays.add(massageYesNo);

		layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);

		int add = 1;
		//layeredPane.add(this.massageOk, add++);
		layeredPane.add(this.event, add++);
		layeredPane.add(this.effect, add++);
		layeredPane.add(this.textTip, add++);
		layeredPane.add(this.dice, add++);
		layeredPane.add(this.playersUI, add++);
		layeredPane.add(this.layerPlayersPanel, add++);
		layeredPane.add(this.buildingsUI, add++);
		layeredPane.add(this.landsUI, add++);
		layeredPane.add(this.running, add++);
		layeredPane.add(this.backgroundUI, add++);
		layeredPane.add(this.shop, add++);
		layeredPane.add(this.playerInfo,add++);

		
		//layeredPane.add(this.massageYesNo, add++);
		//layeredPane.add(this.massageSimple, add++);
		
		add(layeredPane);
	}

	
	public MassageYesNo getMassageYesNo() {
		return massageYesNo;
	}

	public MassageOk getMassageOk() {
		return massageOk;
	}

	public MassageSimple getMassageSimple() {
		return massageSimple;
	}

	public Running getRunning() {
		return running;
	}

	public Dice getDice() {
		return dice;
	}

	public Shop getShop() {
		return this.shop;
	}

	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}

	public Background getBackgroundUI() {
		return backgroundUI;
	}

	public Effect getEffect() {
		return effect;
	}

	public JFrameGame getGameFrame() {
		return gameFrame;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setGameFrame(JFrameGame gameFrame) {
		this.gameFrame = gameFrame;
	}

	/**
	 * 
	 * 初始化游戏配置
	 * 
	 */
	public void startGamePanelInit() {
		for (Layer temp : this.lays) {
			// 刷新窗口UI
			temp.startPanel();
		}
	}

}
