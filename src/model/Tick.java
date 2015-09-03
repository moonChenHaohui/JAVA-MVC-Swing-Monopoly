package model;

/**
 * 
 * 游戏对象父类
 * 
 * @author MOVELIGHTS
 *
 */
public class Tick {
	/**
	 * 记载当前的tick值
	 */
	protected long nowTick;
	
	/**
	 * 开始事件的tick值
	 */
	protected long startTick;
	/**
	 * 事件持续时间 s * 30;
	 */
	protected long lastTime;
	/**
	 * 下一个tick值
	 */
	protected long nextTick;
	
	public long getLastTime() {
		return lastTime;
	}
	
	public long getNowTick() {
		return nowTick;
	}

	public long getStartTick() {
		return startTick;
	}

	public long getNextTick() {
		return nextTick;
	}
	

	public void setNowTick(long nowTick) {
		this.nowTick = nowTick;
	}

	public void setStartTick(long startTick) {
		this.startTick = startTick;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public void setNextTick(long nextTick) {
		this.nextTick = nextTick;
	}

}
