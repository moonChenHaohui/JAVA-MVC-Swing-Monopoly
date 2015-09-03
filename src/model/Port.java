package model;

public interface Port {
	/**
	 * 
	 * 模型更新
	 * 
	 */
	public abstract void updata(long tick);
	
	/**
	 * 
	 * 游戏初始化
	 * 
	 */
	public abstract void startGameInit();
}
