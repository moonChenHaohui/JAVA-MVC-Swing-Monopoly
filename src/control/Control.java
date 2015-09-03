package control;

import java.applet.AudioClip;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import model.BackgroundModel;
import model.BuildingsModel;
import model.DiceModel;
import model.EffectModel;
import model.EventsModel;
import model.LandModel;
import model.PlayerModel;
import model.Port;
import model.TextTipModel;
import model.buildings.Building;
import model.buildings.Hospital;
import model.buildings.News;
import model.buildings.Origin;
import model.buildings.Park;
import model.buildings.Point;
import model.buildings.Prison;
import model.buildings.Shop_;
import model.card.Card;
import model.card.TortoiseCard;
import music.Music;
import ui.JPanelGame;
import util.FileUtil;
import util.MyThread;
import context.GameState;

/**
 * 
 * 游戏总控制器
 * 
 * 
 * @author Administrator
 * 
 */
public class Control {
	/**
	 * 
	 * 游戏tick值
	 * 
	 */
	public static long tick;
	/**
	 * 
	 * 每秒画面刷新频率
	 * 
	 */
	public static int rate = 30;
	/**
	 * 
	 * 游戏主面板
	 * 
	 */
	private JPanelGame panel;
	/**
	 * 
	 * 游戏对象
	 * 
	 */
	private GameRunning run = null;

	private List<Port> models = new ArrayList<Port>();
	private List<PlayerModel> players = null;
	private BuildingsModel building = null;
	private BackgroundModel background = null;
	private LandModel land = null;
	private TextTipModel textTip = null;
	private DiceModel dice = null;
	private EventsModel events = null;
	private EffectModel effect = null;

	private Music music = null;
	
	/**
	 * 
	 * 游戏计时器
	 * 
	 */
	private Timer gameTimer = null;

	public Control() {
		// 创建一个游戏状态
		this.run = new GameRunning(this, players);
		// 初始化游戏对象
		this.initClass();
		// 向游戏状态中加入玩家模型
		this.run.setPlayers(players);
	}

	public void setPanel(JPanelGame panel) {
		this.panel = panel;
	}

	/**
	 * 
	 * 初始化游戏对象
	 * 
	 */
	private void initClass() {
		// 创建一个新的事件模型
		this.events = new EventsModel();
		this.models.add(events);
		// 创建一个新的场景效果模型
		this.effect = new EffectModel();
		this.models.add(effect);
		// 创建新的背景模型
		this.background = new BackgroundModel();
		this.models.add(background);
		// 创建新的土地模型
		this.land = new LandModel();
		this.models.add(land);
		// 创建新的文本显示模型
		this.textTip = new TextTipModel();
		this.models.add(textTip);
		// 创建一个新的建筑模型
		this.building = new BuildingsModel(land);
		this.models.add(building);
		// 创建一个新的玩家数组
		this.players = new ArrayList<PlayerModel>();
		this.players.add(new PlayerModel(1, this));
		this.players.add(new PlayerModel(2, this));
		this.models.add(players.get(0));
		this.models.add(players.get(1));
		// 创建一个新的骰子模型
		this.dice = new DiceModel(run);
		this.models.add(dice);
		
		// 创建一个播放器
		this.music = new Music();
	}

	/**
	 * 
	 * 游戏计时器
	 * 
	 */
	private void createGameTimer() {
		this.gameTimer = new Timer();
		this.gameTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				tick++;
				// 更新各对象
				for (Port temp : models) {
					temp.updata(tick);
				}
				// UI更新
				panel.repaint();
			}
		}, 0, (1000 / rate));
	}

	/**
	 * 
	 * 控制器启动
	 * 
	 */
	public void start() {
		// 创建一个计时器
		this.createGameTimer();
		// 刷新对象初始数据
		for (Port temp : this.models) {
			temp.startGameInit();
		}
		// 游戏环境开始
		this.run.startGameInit();
		// panel 初始化
		this.panel.startGamePanelInit();
		// 游戏背景音乐
		this.startMusic();
		// 游戏开始产生地图效果
		this.effect.showImg("start");
	}

	
	/**
	 * 
	 * 游戏背景音乐
	 * 
	 */
	private void startMusic() {
		music.start();
	}

	public List<PlayerModel> getPlayers() {
		return players;
	}

	public BuildingsModel getBuilding() {
		return building;
	}

	public BackgroundModel getBackground() {
		return background;
	}

	public LandModel getLand() {
		return land;
	}

	public EffectModel getEffect() {
		return effect;
	}

	public TextTipModel getTextTip() {
		return textTip;
	}

	public GameRunning getRunning() {
		return run;
	}

	public DiceModel getDice() {
		return dice;
	}

	public EventsModel getEvents() {
		return events;
	}

	public JPanelGame getPanel() {
		return panel;
	}

	/**
	 * 
	 * 
	 * 按下骰子
	 * 
	 * 
	 */
	public void pressButton() {
		PlayerModel player = this.run.getNowPlayer();
		if (player.getInHospital() > 0 || player.getInPrison() > 0) {
			this.run.nextState();
			if (player.getInHospital() > 0) {
				this.textTip.showTextTip(player, player.getName() + "住院中.", 3);
			} else if (player.getInPrison() > 0) {
				this.textTip.showTextTip(player, player.getName() + "在监狱.", 3);
			}
			this.run.nextState();
		} else {
			// 设置骰子对象开始转动时间
			this.dice.setStartTick(Control.tick);
			// 设置骰子对象结束转动时间
			this.dice.setNextTick(this.dice.getStartTick()
					+ this.dice.getLastTime());
			// 将运行对象点数传入骰子对象
			this.dice.setPoint(this.run.getPoint());
			// 转换状态至“移动状态”
			this.run.nextState();
			// 骰子转动完毕后玩家移动
			this.run.getNowPlayer().setStartTick(this.dice.getNextTick() + 10);
			this.run.getNowPlayer().setNextTick(
					this.run.getNowPlayer().getStartTick()
							+ this.run.getNowPlayer().getLastTime()
							* (this.run.getPoint() + 1));
		}
	}

	/**
	 * 
	 * 
	 * 玩家移动
	 * 
	 * 
	 */
	public void movePlayer() {
		// 人物运动
		for (int i = 0; i < (60 / this.run.getNowPlayer().getLastTime()); i++) {
			// 移动玩家
			if (GameRunning.MAP == 1){
				this.move01();
			} else if (GameRunning.MAP == 2){
				this.move02();
			} else if (GameRunning.MAP == 3) {
				this.move03();
			}
		}
	}

	/**
	 * 
	 * 玩家中途路过建筑
	 * 
	 */
	public void prassBuilding() {
		// 当前玩家
		PlayerModel player = this.run.getNowPlayer();
		// 该地点房屋
		Building building = this.building.getBuilding(player.getY() / 60,
				player.getX() / 60);
		if (building != null && player.getX() % 60 == 0
				&& player.getY() % 60 == 0) {
			// 经过房屋发生事件
			int event = building.passEvent();
			// 进入经过房屋事件处理
			disposePassEvent(building, event, player);
		}
	}

	/**
	 * 
	 * 经过房屋事件处理
	 * 
	 */
	private void disposePassEvent(Building b, int event, PlayerModel player) {
		switch (event) {
		case GameState.ORIGIN_PASS_EVENT:
			// 中途经过原点
			passOrigin(b, player);
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * 中途经过原点
	 * 
	 */
	private void passOrigin(Building b, PlayerModel player) {
		this.textTip.showTextTip(player, player.getName() + " 路过原点，奖励 "
				+ ((Origin) b).getPassReward() + "金币.", 3);
		player.setCash(player.getCash() + ((Origin) b).getPassReward());
	}

	/**
	 * 
	 * 
	 * 玩家移动的方法
	 * 
	 * 
	 */
	private void move02() {
		int dice = this.run.getPoint() + 1;
		PlayerModel p = this.run.getNowPlayer();
		// 单位移动像素
		int movePixel = 1;
		if (p.getX() < 12 * 60 && p.getY() == 0) {
			p.setX(p.getX() + movePixel);
		} else if (p.getX() == 12 *60 && p.getY() < 2 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() == 12 * 60 && p.getY() == 2 * 60){
			if ((int)(Math.random() * 2 ) == 0){
				p.setX(p.getX() - movePixel);
			} else {
				p.setY(p.getY() + movePixel);
			}
		} else if (p.getX() == 12 * 60 && p.getY() > 2 * 60 && p.getY() < 4 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() > 8 * 60 && p.getX() <= 12 * 60 && p.getY() == 4 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 8 * 60 && p.getY() == 4 * 60){
			if ((int)(Math.random() * 2 ) == 0){
				p.setX(p.getX() - movePixel);
			} else {
				p.setY(p.getY() + movePixel);
			}
		} else if (p.getX() > 4 * 60 && p.getX() < 8 * 60 && p.getY() == 4 * 60) {
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 8 * 60 && p.getY() > 4 * 60 && p.getY() < 7 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() >  4 * 60 && p.getX() <= 8 * 60 && p.getY() == 7 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() > 4 * 60 && p.getX() < 12 * 60 && p.getY() == 2 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 4 * 60 && p.getY() >= 2 * 60 && p.getY() < 7 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() > 0 && p.getX() <= 4 * 60 && p.getY() == 7 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 0 && p.getY() > 0){
			p.setY(p.getY() - movePixel);
		}
	}
	
	/**
	 * 
	 * 
	 * 玩家移动的方法
	 * 
	 * 
	 */
	private void move01() {
		int dice = this.run.getPoint() + 1;
		PlayerModel p = this.run.getNowPlayer();
		// 单位移动像素
		int movePixel = 1;
		Boolean turn = dice % 2 != 0;
		if (p.getX() < 9 * 60 && p.getY() == 0) {
			// 上面
			if (p.getX() == 4 * 60 && turn) {
				// 分岔点情况
				p.setY(p.getY() + movePixel);
			} else {
				p.setX(p.getX() + movePixel);
			}
		} else if (p.getX() == 9 * 60 && p.getY() >= 0 && p.getY() < 60) {
			// [0,9]
			// ↓
			p.setY(p.getY() + movePixel);
		} else if (p.getX() >= 8 * 60 && p.getX() < 12 * 60
				&& p.getY() >= 1 * 60 && p.getY() <= 60 * 1.5) {
			// →
			p.setX(p.getX() + movePixel);
		} else if (p.getX() == 12 * 60 && p.getY() >= 1 * 60
				&& p.getY() < 7 * 60) {
			// ↓
			p.setY(p.getY() + movePixel);
		} else if (p.getX() > 0 && p.getY() == 7 * 60) {
			// ←
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 0 && p.getY() > 0) {
			// ↑
			p.setY(p.getY() - movePixel);
		} else if (p.getX() == 4 * 60 && p.getY() > 0 && p.getY() < 7 * 60) {
			// ↓
			p.setY(p.getY() + movePixel);
		}
	}
	/**
	 * 
	 * 
	 * 玩家移动的方法
	 * 
	 * 
	 */
	private void move03() {
		PlayerModel p = this.run.getNowPlayer();
		// 单位移动像素
		int movePixel = 1;
		if (p.getX() < 12 * 60 && p.getY() == 0) {
			p.setX(p.getX() + movePixel);
		} else if (p.getX() == 12 *60 && p.getY() < 7 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() > 0 && p.getY() == 7 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 0 && p.getY() > 0){
			p.setY(p.getY() - movePixel);
		}
	}
	/**
	 * 
	 * 玩家移动完毕，停下判断
	 * 
	 */
	public void playerStopJudge() {
		// 当前玩家
		PlayerModel player = this.run.getNowPlayer();
		if (player.getInHospital() > 0) {
			this.textTip.showTextTip(player, player.getName() + "当前在医院,不能移动.",
					2);
			// 更换玩家状态
			this.run.nextState();
		} else if (player.getInPrison() > 0) {
			this.textTip.showTextTip(player, player.getName() + "当前在监狱,不能移动.",
					2);
			// 更换玩家状态
			this.run.nextState();
		} else {
			// 进行玩家操作（买房 事件等）
			this.playerStop();
		}
	}

	/**
	 * 
	 * 玩家移动完毕，停下操作
	 * 
	 */
	public void playerStop() {
		// 当前玩家
		PlayerModel player = this.run.getNowPlayer();
		// 该地点房屋
		Building building = this.building.getBuilding(player.getY() / 60,
				player.getX() / 60);
		if (building != null) {// 获取房屋
			int event = building.getEvent();
			// 触发房屋信息
			disposeStopEvent(building, event, player);

		}
	}

	/**
	 * 
	 * 停留房屋事件处理
	 * 
	 * 
	 */
	private void disposeStopEvent(Building b, int event, PlayerModel player) {
		switch (event) {
		case GameState.HOSPITAL_EVENT:
			// 停留在医院
			stopInHospital(b, player);
			break;
		case GameState.HUOSE_EVENT:
			// 停留在可操作土地
			stopInHouse(b, player);
			break;
		case GameState.LOTTERY_EVENT:
			// 停留在乐透点上
			stopInLottery(b, player);
			break;
		case GameState.NEWS_EVENT:
			// 停留在新闻点上
			stopInNews(b, player);
			break;
		case GameState.ORIGIN_EVENT:
			// 停留在原点
			stopInOrigin(b, player);
			break;
		case GameState.PARK_EVENT:
			// 停留在公园
			stopInPack(b, player);
			break;
		case GameState.POINT_EVENT:
			// 停留在点卷位
			stopInPoint(b, player);
			break;
		case GameState.PRISON_EVENT:
			// 停留在监狱
			stopInPrison(b, player);
			break;
		case GameState.SHOP_EVENT:
			// 停留在商店
			stopInShop(b, player);
			break;
		}

	}

	/**
	 * 
	 * 停留在商店
	 * 
	 */
	private void stopInShop(Building b, PlayerModel player) {
		if (player.getNx() > 0){
		// 为商店的货架从新生成商品
		((Shop_) b).createCards();
		// 为商店面板更新新的卡片商品
		this.panel.getShop().addCards((Shop_) b);
		// 將商店面板推送至頂
		this.panel.getShop().moveToFront();
		} else {
			this.run.nextState();
		}
	}

	/**
	 * 
	 * 停留在监狱
	 * 
	 */
	private void stopInPrison(Building b, PlayerModel player) {
		int days = (int) (Math.random() * 3) + 2;
		player.setInPrison(days);
		int random = (int) (Math.random() * ((Prison) b).getEvents().length);
		String text = ((Prison) b).getEvents()[random];
		this.textTip.showTextTip(player, player.getName() + text + "停留"
				+ (days - 1) + "天.", 3);
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 停留在点卷位
	 * 
	 */
	private void stopInPoint(Building b, PlayerModel player) {
		player.setNx(((Point) b).getPoint() + player.getNx());
		this.textTip.showTextTip(player, player.getName() + " 获得 "
				+ ((Point) b).getPoint() + "点卷.", 3);
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 停留在公园
	 * 
	 */
	private void stopInPack(Building b, PlayerModel player) {
		int random = (int) (Math.random() * ((Park) b).getImgageEvents().length);

		switch (random) {
		case 0:
		case 1:
			// 减一金币
			player.setCash(player.getCash() - 1);
			break;
		case 2:
			// 减200金币
			player.setCash(player.getCash() - 200);
			break;
		case 3:
			// 加200金币
			player.setCash(player.getCash() + 200);
			break;
		}
		// 在事件层显示事件
		this.events.showImg(((Park) b).getImgageEvents()[random], 3, new Point(
				320, 160, 0));
		new Thread(new MyThread(run, 3)).start();
	}

	/**
	 * 
	 * 停留在原点
	 * 
	 */
	private void stopInOrigin(Building b, PlayerModel player) {
		this.textTip.showTextTip(player, player.getName() + " 在起点停留，奖励 "
				+ ((Origin) b).getReward() + "金币.", 3);
		player.setCash(player.getCash() + ((Origin) b).getReward());
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 停留在新闻点上
	 * 
	 */
	private void stopInNews(Building b, PlayerModel player) {
		int random = (int) (Math.random() * ((News) b).getImgageEvents().length);
		switch (random) {
		case 0:
		case 1:
			// 设置天数
			player.setInHospital(player.getInHospital() + 4);
			// 玩家位置切换到医院位置
			if (LandModel.hospital != null) {
				player.setX(LandModel.hospital.x);
				player.setY(LandModel.hospital.y);
			}
			break;
		case 2:
		case 3:
			player.setCash(player.getCash() - 1000);
			break;
		case 4:
			player.setCash(player.getCash() - 1500);
			break;
		case 5:
			player.setCash(player.getCash() - 2000);
			break;
		case 6:
		case 7:
			player.setCash(player.getCash() - 300);
			break;
		case 8:
			player.setCash(player.getCash() - 400);
			break;
		case 9:
			// 点卷小于不能发生事件
			if (player.getNx() < 40) {
				stopInNews(b, player);
				return;
			}
			player.setNx(player.getNx() - 40);
			break;
		case 10:
			player.setCash(player.getCash() - 500);
			break;
		case 11:
			player.setCash(player.getCash() + 1000);
			break;
		case 12:
		case 13:
			player.setCash(player.getCash() + 2000);
			break;
		case 14:
			player.setCash(player.getCash() + 3999);
			player.setNx(player.getNx() + 100);
			break;
		case 15:
			player.setNx(player.getNx() + 300);
			break;
		case 16:
			for (int i = 0; i  < player.getCards().size();i++){
//				System.out.println(player.getCards().get(i).getcName());
				// 嫁祸卡
				if (player.getCards().get(i).getName().equals("CrossingCard")){
					player.getCards().remove(i);
					// 对手减少金钱.
					player.getOtherPlayer().setCash(player.getOtherPlayer().getCash() - 3000);
					this.textTip.showTextTip(player, player.getName() + "将一笔\"3000元\"嫁祸给 "+ player.getOtherPlayer().getName()+"。真是人算不如天算啊.", 6);
					this.events.showImg(((News) b).get3000(), 3, new Point(
							420, 160, 0));
					new Thread(new MyThread(run, 3)).start();
					return;
				}
			}
			player.setCash(player.getCash() - 3000);
			break;
		}
		// 在事件层显示事件
		this.events.showImg(((News) b).getImgageEvents()[random], 3, new Point(
				420, 160, 0));
		new Thread(new MyThread(run, 3)).start();
	}

	/**
	 * 
	 * 停留在乐透点上
	 * 
	 */
	private void stopInLottery(Building b, PlayerModel player) {
		// 未制作
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 
	 * 停留在可操作土地
	 * 
	 * 
	 */
	private void stopInHouse(Building b, PlayerModel player) {
		if (b.isPurchasability()) {// 玩家房屋
			if (b.getOwner() == null) { // 无人房屋
				// 执行买房操作
				this.buyHouse(b, player);
			} else {// 有人房屋
				if (b.getOwner().equals(player)) {// 自己房屋
					// 执行升级房屋操作
					this.upHouseLevel(b, player);
				} else {// 别人房屋
					// 执行交税操作
					this.giveTax(b, player);
				}
			}
		}
	}

	/**
	 * 
	 * 执行交税操作
	 * 
	 * 
	 */
	private void giveTax(Building b, PlayerModel player) {
		if (b.getOwner().getInHospital() > 0) {
			// 增加文本提示
			this.textTip.showTextTip(player, b.getOwner().getName()
					+ "正在住院,免交过路费.", 3);
		} else if (b.getOwner().getInPrison() > 0) {
			// 增加文本提示
			this.textTip.showTextTip(player, b.getOwner().getName()
					+ "正在监狱,免交过路费.", 3);
		} else {
			int revenue = b.getRevenue();
			// 该玩家减少金币
			player.setCash(player.getCash() - revenue);
			// 业主得到金币
			b.getOwner().setCash(b.getOwner().getCash() + revenue);
			// 增加文本提示
			this.textTip.showTextTip(player, player.getName() + "经过"
					+ b.getOwner().getName() + "的地盘，过路费:" + revenue + "金币.", 3);

		}
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 执行升级房屋操作
	 * 
	 */
	private void upHouseLevel(Building b, PlayerModel player) {
		if (b.canUpLevel()) {
			// 升级房屋
			int price = b.getUpLevelPrice();
			String name = b.getName();
			String upName = b.getUpName();
			int choose = JOptionPane.showConfirmDialog(null,
					"亲爱的:" + player.getName() + "\r\n" + "是否升级这块地？\r\n" + name
							+ "→" + upName + "\r\n" + "价格：" + price + " 金币.");
			if (choose == JOptionPane.OK_OPTION) {
				if (player.getCash() >= price) {
					b.setLevel(b.getLevel() + 1);
					// 减少需要的金币
					player.setCash(player.getCash() - price);
					// 增加文本提示
					this.textTip.showTextTip(player, player.getName() + " 从 "
							+ name + " 升级成 " + upName + ".花费了 " + price
							+ "金币. ", 3);
				} else {
					// 增加文本提示
					this.textTip.showTextTip(player, player.getName()
							+ " 金币不足,操作失败. ", 3);
				}
			}
		}
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 执行买房操作
	 * 
	 * 
	 */
	private void buyHouse(Building b, PlayerModel player) {
		int price = b.getUpLevelPrice();
		int choose = JOptionPane.showConfirmDialog(
				null,
				"亲爱的:" + player.getName() + "\r\n" + "是否购买下这块地？\r\n"
						+ b.getName() + "→" + b.getUpName() + "\r\n" + "价格："
						+ price + " 金币.");

		if (choose == JOptionPane.OK_OPTION) {
			// 购买
			if (player.getCash() >= price) {
				b.setOwner(player);
				b.setLevel(1);
				// 将该房屋加入当前玩家的房屋列表下
				player.getBuildings().add(b);
				// 减少需要的金币
				player.setCash(player.getCash() - price);
				this.textTip.showTextTip(player, player.getName()
						+ " 买下了一块空地.花费了: " + price + "金币. ", 3);
			} else {
				this.textTip.showTextTip(player, player.getName()
						+ " 金币不足,操作失败. ", 3);
			}
		}
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 停留在医院
	 * 
	 */
	private void stopInHospital(Building b, PlayerModel player) {
		int days = (int) (Math.random() * 4) + 2;
		player.setInHospital(days);
		int random = (int) (Math.random() * ((Hospital) b).getEvents().length);
		String text = ((Hospital) b).getEvents()[random];
		this.textTip.showTextTip(player, player.getName() + text + "停留"
				+ (days - 1) + "天.", 3);
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 卡片效果作用
	 * 
	 */
	public void cardsBuff() {
		List<Card>delete = new ArrayList<Card>();
		for (Card a : this.run.getNowPlayer().getEffectCards()) {
			int buff = a.cardBuff();
			cardBuff(a, buff,delete);
		}
		this.run.getNowPlayer().getEffectCards().removeAll(delete);
		this.run.nextState();
	}

	/**
	 * 
	 * 卡片效果持续
	 * 
	 * 
	 */
	private void cardBuff(Card card, int buff,List<Card>delete) {
		switch (buff) {
		case GameState.CARD_BUFF_TORTOISE:
			// 乌龟卡BUff
			buffTortoiseCard((TortoiseCard) card,delete);
			break;
		case GameState.CARD_BUFF_STOP:
			// 停留卡Buff
			buffStopCard(card,delete);
			break;
		}
	}

	/**
	 * 
	 * 停留卡Buff
	 * 
	 * 
	 */
	private void buffStopCard(Card card,List<Card>delete) {
		// 增加文本提示
		this.textTip.showTextTip(card.geteOwner(), card.geteOwner().getName()
				+ " 受\"停留卡\" 作用，不能移动.. ", 2);
		// 移除卡片
		delete.add(card);
		this.run.nextState();
		new Thread(new MyThread(run, 1)).start();
	}
	

	/**
	 * 
	 * 乌龟卡BUff
	 * 
	 */

	private void buffTortoiseCard(TortoiseCard card,List<Card>delete) {
		if (card.getLife() <= 0) {
			delete.add(card);
			return;
		} else {
			card.setLife(card.getLife() - 1);
		}
		this.textTip.showTextTip(card.geteOwner(), card.geteOwner().getName()
				+ " 受\"乌龟卡\" 作用，只能移动一步.. ", 2);
		this.run.setPoint(0);
	}

	/**
	 * 
	 * 使用卡片
	 * 
	 */
	public void useCards() {
		PlayerModel p = this.run.getNowPlayer();
		while (true) {
			if (p.getCards().size() == 0) {
				// 无卡片，跳过阶段
				this.run.nextState();
				break;
			} else {
				Object[] options = new Object[p.getCards().size() + 1];
				int i;
				for (i = 0; i < p.getCards().size(); i++) {
					options[i] = p.getCards().get(i).getcName() + "\r\n";
				}
				options[i] = "跳过,不使用";
				int response = JOptionPane.showOptionDialog(null,
						" " + p.getName() + "，选择需要使用的卡片", "卡片使用阶段.",
						JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options, options[0]);
				if (response != i && response != -1) {
					// 获得卡片
					int th = p.getCards().get(response).useCard();
					// 使用卡片
					useCard(p.getCards().get(response), th);
				} else {
					// 不使用，跳过阶段.
					this.run.nextState();
					break;
				}
			}
		}
	}

	/**
	 * 
	 * 使用卡片
	 * 
	 */
	private void useCard(Card card, int th) {
		switch (th) {
		case GameState.CARD_ADDLEVEL:
			// 使用加盖卡
			useAddLevelCard(card);
			break;
		case GameState.CARD_AVERAGERPOOR:
			// 使用均贫卡
			useAveragerPoorCard(card);
			break;
		case GameState.CARD_CHANGE:
			// 使用换屋卡
			useChangeCard(card);
			break;
		case GameState.CARD_CONTROLDICE:
			// 使用遥控骰子卡
			useControlDiceCard(card);
			break;
		case GameState.CARD_HAVE:
			// 使用购地卡
			useHaveCard(card);
			break;
		case GameState.CARD_REDUCELEVEL:
			// 使用降级卡
			useReduceLevelCard(card);
			break;
		case GameState.CARD_ROB:
			// 使用抢夺卡
			useRobCard(card);
			break;
		case GameState.CARD_STOP:
			// 使用停留卡
			useStopCard(card);
			break;
		case GameState.CARD_TALLAGE:
			// 使用查税卡
			useTallageCard(card);
			break;
		case GameState.CARD_TORTOISE:
			// 使用乌龟卡
			useTortoiseCard(card);
			break;
		case GameState.CARD_TRAP:
			// 使用陷害卡
			useTrapCard(card);
			break;
		case GameState.CARD_CROSSING:
			// 使用嫁祸卡
			useCrossingCard(card);
			break;
		}
	}

	/**
	 * 
	 * 使用嫁祸卡
	 * 
	 */
	private void useCrossingCard(Card card) {
		Object[] options1 = { "重新选择" };
		JOptionPane.showOptionDialog(null, " 嫁祸卡在大事件发生时会自动使用.",
				"卡片使用阶段.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options1,
				options1[0]);
	}

	/**
	 * 
	 * 使用陷害卡
	 * 
	 */
	private void useTrapCard(Card card) {
		Object[] options = { "确认使用", "重新选择" };
		int response = JOptionPane.showOptionDialog(null, "确认使用\"陷害卡\"将 \""
				+ card.getOwner().getOtherPlayer().getName() + "\"入狱2天?",
				"卡片使用阶段.", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		if (response == 0) {
			// 使用
			PlayerModel cPlayer = card.getOwner().getOtherPlayer();
			// 设置天数
			cPlayer.setInPrison(cPlayer.getInPrison() + 2);
			// 玩家位置切换到医院位置
			if (LandModel.prison != null) {
				cPlayer.setX(LandModel.prison.x);
				cPlayer.setY(LandModel.prison.y);
			}
			// 增加文本提示
			this.textTip
					.showTextTip(card.getOwner(), card.getOwner().getName()
							+ " 使用了 \"陷害卡\"，将 \""
							+ card.getOwner().getOtherPlayer().getName()
							+ "\"入狱2天.", 2);
			// 　减去卡片
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 使用乌龟卡
	 * 
	 * 
	 */
	private void useTortoiseCard(Card card) {
		Object[] options = { card.getOwner().getName(),
				card.getOwner().getOtherPlayer().getName(), "重新选择" };
		int response = JOptionPane.showOptionDialog(null,
				" 请选择目标玩家，对其打出\"乌龟卡\".", "卡片使用阶段.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == 0) {
			card.getOwner().getEffectCards().add(card);
			card.seteOwner(card.getOwner());
			// 增加文本提示
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " 对自己使用了\"乌龟卡\". ", 2);
			card.getOwner().getCards().remove(card);
		} else if (response == 1) {
			card.getOwner().getOtherPlayer().getEffectCards().add(card);
			card.seteOwner(card.getOwner().getOtherPlayer());
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " 对\"" + card.getOwner().getOtherPlayer().getName()
					+ "\"使用了\"乌龟卡\". ", 2);
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 使用查税卡
	 * 
	 * 
	 */
	private void useTallageCard(Card card) {
		Object[] options = { "确认使用", "重新选择" };
		int response = JOptionPane.showOptionDialog(null, "确认使用\"查税卡\"从 \""
				+ card.getOwner().getOtherPlayer().getName() + "\"手中获得 10%税款?",
				"卡片使用阶段.", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		if (response == 0) {
			// 使用
			int money = (int) (card.getOwner().getOtherPlayer().getCash() / 10);
			card.getOwner().setCash(card.getOwner().getCash() + money);
			card.getOwner()
					.getOtherPlayer()
					.setCash(card.getOwner().getOtherPlayer().getCash() - money);
			// 增加文本提示
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " 使用了 \"查税卡\"，从 \""
					+ card.getOwner().getOtherPlayer().getName()
					+ "\"手中获得 10%税款", 2);
			// 　减去卡片
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 
	 * 使用停留卡
	 * 
	 */
	private void useStopCard(Card card) {
		Object[] options = { card.getOwner().getName(),
				card.getOwner().getOtherPlayer().getName(), "重新选择" };
		int response = JOptionPane.showOptionDialog(null,
				" 请选择目标玩家，对其打出\"停留卡\".", "卡片使用阶段.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == 0) {
			card.getOwner().getEffectCards().add(card);
			card.seteOwner(card.getOwner());
			// 增加文本提示
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " 对自己使用了\"停留卡\". ", 2);
			card.getOwner().getCards().remove(card);
		} else if (response == 1) {
			card.getOwner().getOtherPlayer().getEffectCards().add(card);
			card.seteOwner(card.getOwner().getOtherPlayer());
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " 对\"" + card.getOwner().getOtherPlayer().getName()
					+ "\"使用了\"停留卡\". ", 2);
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 
	 * 使用抢夺卡
	 * 
	 * 
	 */
	private void useRobCard(Card card) {
		if (card.getOwner().getCards().size() >= PlayerModel.MAX_CAN_HOLD_CARDS) {
			// 无法使用
			Object[] options = { "重新选择" };
			JOptionPane.showOptionDialog(null, " 您的卡片数量已经达到上限，无法使用\"抢夺卡\"",
					"卡片使用阶段.", JOptionPane.YES_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		} else if (card.getOwner().getOtherPlayer().getCards().size() == 0) {
			// 无法使用
			Object[] options = { "重新选择" };
			JOptionPane.showOptionDialog(null, " \""
					+ card.getOwner().getOtherPlayer().getName()
					+ "\"没有卡片，无法使用\"抢夺卡\"", "卡片使用阶段.", JOptionPane.YES_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		} else {
			PlayerModel srcPlayer = card.getOwner().getOtherPlayer();
			// 随机选取一张
//			System.out.println(srcPlayer.getCards().size() + "zhang");
			Card getCard = srcPlayer.getCards().get((int) (srcPlayer.getCards().size() * Math.random()));
			// 对手丧失卡片
			srcPlayer.getCards().remove(getCard);
			// 卡片拥有者获得
			card.getOwner().getCards().add(getCard);
			// 更改获得卡片拥有者
			getCard.setOwner(card.getOwner());
			// 增加文本提示
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " 使用了 \"抢夺卡\"，抢夺了 \"" + srcPlayer.getName() + "\"的一张\""
					+ getCard.getcName() + ".\". ", 2);
			// 　减去卡片
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 使用降级卡
	 * 
	 */
	private void useReduceLevelCard(Card card) {
		Building building = this.building.getBuilding(
				card.getOwner().getY() / 60, card.getOwner().getX() / 60);
		if (building.getOwner() != null
				&& building.getOwner().equals(card.getOwner().getOtherPlayer())) {// 是对手的房屋
			if (building.getLevel() > 0) { // 可以降级
				// 降级
				building.setLevel(building.getLevel() - 1);
				// 增加文本提示
				this.textTip.showTextTip(card.getOwner(), card.getOwner()
						.getName()
						+ " 使用了 \"降级卡\"，将\""
						+ card.getOwner().getOtherPlayer().getName()
						+ "\"的房屋等级降低一级. ", 2);
				// 　减去卡片
				card.getOwner().getCards().remove(card);
			} else {
				// 无法使用,不可降级
				Object[] options = { "重新选择" };
				JOptionPane.showOptionDialog(null, " 当前房屋不可降级", "卡片使用阶段.",
						JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options, options[0]);
			}
		} else {
			// 无法使用.
			Object[] options = { "重新选择" };
			JOptionPane.showOptionDialog(null, " 当前房屋不能使用该卡片.", "卡片使用阶段.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, options[0]);
		}
	}

	/**
	 * 
	 * 使用购地卡
	 * 
	 */
	private void useHaveCard(Card card) {
		// 该地点房屋
		Building building = this.building.getBuilding(
				card.getOwner().getY() / 60, card.getOwner().getX() / 60);
		if (building.getOwner() != null
				&& building.getOwner().equals(card.getOwner().getOtherPlayer())) {// 是对方的房屋
			Object[] options = { "确认使用", "重新选择" };
			int response = JOptionPane.showOptionDialog(null,
					"确认使用\"购地卡\"将此地收购？需要花费：" + building.getAllPrice() + " 金币.",
					"卡片使用阶段.", JOptionPane.YES_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (response == 0) {
				if (card.getOwner().getCash() >= building.getAllPrice()) {
					// 金币交换
					building.getOwner().setCash(
							building.getOwner().getCash()
									+ building.getAllPrice());
					card.getOwner().setCash(
							card.getOwner().getCash() - building.getAllPrice());
					building.setOwner(card.getOwner());
					// 增加文本提示
					this.textTip.showTextTip(card.getOwner(), card.getOwner()
							.getName() + " 使用了 \"购地卡\"，收购获得了该土地. ", 2);
					// 　减去卡片
					card.getOwner().getCards().remove(card);
				} else {
					Object[] options1 = { "重新选择" };
					JOptionPane.showOptionDialog(null, " 金币不足，无法购买房屋!",
							"卡片使用阶段.", JOptionPane.YES_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options1,
							options1[0]);
				}
			}
		} else {
			Object[] options1 = { "重新选择" };
			JOptionPane.showOptionDialog(null, "此房屋无法使用该卡片.", "卡片使用阶段.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options1, options1[0]);
		}
	}

	/**
	 * 
	 * 
	 * 使用遥控骰子卡
	 * 
	 * 
	 */
	private void useControlDiceCard(Card card) {
		Object[] options = { "1点", "2点", "3点", "4点", "5点", "6点", "重新选择" };
		int response = JOptionPane.showOptionDialog(null,
				"确认使用\"遥控骰子卡\"遥控骰子点数?", "卡片使用阶段.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == -1 || response == 6) {
			return;
		} else {
			// 使用
			this.run.setPoint(response);
			// 增加文本提示
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " 使用了 \"遥控骰子卡\".", 2);
			// 　减去卡片
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 使用换屋卡
	 * 
	 */
	private void useChangeCard(Card card) {
		Building building = this.building.getBuilding(
				card.getOwner().getY() / 60, card.getOwner().getX() / 60);
		if (building.getOwner() != null
				&& building.getOwner().equals(card.getOwner().getOtherPlayer())) {// 是对手房屋
			Object[] options = { "确认使用", "重新选择" };
			int response = JOptionPane.showOptionDialog(null,
					"确认使用\"换屋卡\"与对手交换一块同类型的房屋（随机）", "卡片使用阶段.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				// 找寻相等级别房屋
				int thisBuildingLevel = building.getLevel();
				Building changeBuilding = null;
				for (Building a : card.getOwner().getBuildings()) {
					if (a.getLevel() == thisBuildingLevel) {
						changeBuilding = a;
						break;
					}
				}
				// 找到同类型房屋
				if (changeBuilding != null) {
					changeBuilding.setOwner(card.getOwner().getOtherPlayer());
					building.setOwner(card.getOwner());
					// 增加文本提示
					this.textTip.showTextTip(card.getOwner(), card.getOwner()
							.getName()
							+ " 使用了 \"换屋卡\"，将某处房屋与"
							+ card.getOwner().getOtherPlayer().getName()
							+ "该地的房屋进行交换.. ", 2);
					// 　减去卡片
					card.getOwner().getCards().remove(card);
				} else {
					Object[] options1 = { "重新选择" };
					JOptionPane.showOptionDialog(null, " 当前房屋不可使用\"换屋卡\"",
							"卡片使用阶段.", JOptionPane.YES_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options1,
							options1[0]);
				}
			}
		} else {
			Object[] options = { "重新选择" };
			JOptionPane.showOptionDialog(null, " 当前房屋不可使用\"换屋卡\"", "卡片使用阶段.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, options[0]);
		}
	}

	/**
	 * 
	 * 使用均贫卡
	 * 
	 */
	private void useAveragerPoorCard(Card card) {
		Object[] options = { "确认使用", "重新选择" };
		int response = JOptionPane.showOptionDialog(null,
				"确认使用\"均贫卡\"与对手平分现金?", "卡片使用阶段.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == 0) {
			// 使用
			int money = (int) (card.getOwner().getCash() + card.getOwner()
					.getOtherPlayer().getCash()) / 2;
			card.getOwner().setCash(money);
			card.getOwner().getOtherPlayer().setCash(money);
			// 增加文本提示
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " 使用了 \"均贫卡\"，与对手平分了现金,现在双方现金数为:" + money + " 金币. ", 2);

			// 　减去卡片
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 使用加盖卡
	 * 
	 */

	private void useAddLevelCard(Card card) {
		Building building = this.building.getBuilding(
				card.getOwner().getY() / 60, card.getOwner().getX() / 60);
		if (building.getOwner() != null
				&& building.getOwner().equals(card.getOwner())) {// 是自己的房屋
			if (building.canUpLevel()) { // 可升级
				// 升级
				building.setLevel(building.getLevel() + 1);
				// 增加文本提示
				this.textTip.showTextTip(card.getOwner(), card.getOwner()
						.getName() + " 使用了 \"加盖卡\"，将房屋等级提升一级. ", 2);
				// 　减去卡片
				card.getOwner().getCards().remove(card);
			} else {
				// 无法使用,不可升级
				Object[] options = { "重新选择" };
				JOptionPane.showOptionDialog(null, " 当前房屋不可升级.", "卡片使用阶段.",
						JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options, options[0]);
			}
		} else {
			// 无法使用.
			Object[] options = { "重新选择" };
			JOptionPane.showOptionDialog(null, " 当前房屋不能使用该卡片.", "卡片使用阶段.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, options[0]);
		}
	}

	/**
	 * 
	 * 退出商店
	 * 
	 */
	public void exitShop() {
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 商店里买卡片操作
	 * 
	 * 
	 */
	public void buyCard(Shop_ shop) {
		int chooseCard = this.panel.getShop().getChooseCard();
		if (chooseCard >= 0
				&& this.panel.getShop().getCard().get(chooseCard) != null) {
			// 购买卡片 如果购买成功
			if (this.buyCard(shop, chooseCard)) {
				// ＵＩ消去卡片
				this.panel.getShop().getCard().get(chooseCard).setEnabled(false);
				// 初始化已选卡片
				this.panel.getShop().setChooseCard(-1);
			}
		}
	}

	/**
	 * 
	 * 购买卡片
	 * 
	 * 
	 */
	public boolean buyCard(Shop_ shop, int p) {
		if (this.panel.getShop().getCard().get(p) != null) {
			if (this.run.getNowPlayer().getCards().size() >= PlayerModel.MAX_CAN_HOLD_CARDS) {
				JOptionPane.showMessageDialog(null, "您最大可持有:"
						+ PlayerModel.MAX_CAN_HOLD_CARDS + "张卡片,目前已经不能再购买了!");
				return false;
			}
			if (this.run.getNowPlayer().getNx() < shop.getCards().get(p)
					.getPrice()) {
				JOptionPane.showMessageDialog(null, "当前卡片需要:"
						+ shop.getCards().get(p).getPrice() + "点卷,您的点卷不足.");
				return false;
			}
			// 设置卡片拥有者
			shop.getCards().get(p).setOwner(this.run.getNowPlayer());
			// 向玩家卡片库中添加卡片
			this.run.getNowPlayer().getCards().add(shop.getCards().get(p));
			// 减去对应点卷
			this.run.getNowPlayer().setNx(
					this.run.getNowPlayer().getNx()
							- shop.getCards().get(p).getPrice());
		}
		return true;
	}

	/**
	 * 
	 * 游戏结束~
	 * 
	 * 
	 * @param winer
	 */
	public void gameOver () {
		this.run.setNowPlayerState(GameRunning.GAME_STOP);
		this.panel.getBackgroundUI().moveToFront();
		this.panel.getRunning().moveToFront();
		this.panel.getPlayerInfo().moveToFront();
		this.panel.getEffect().moveToFront();
		this.music.gameOver();
		this.effect.showImg("timeover2");
		
	}
}
