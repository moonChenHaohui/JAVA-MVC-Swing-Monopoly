package model;

import java.util.ArrayList;
import java.util.List;

import model.buildings.Building;
import model.buildings.Hospital;
import model.buildings.House;
import model.buildings.Lottery;
import model.buildings.News;
import model.buildings.Origin;
import model.buildings.Park;
import model.buildings.Point;
import model.buildings.Prison;
import model.buildings.Shop_;

/**
 * 全局房屋信息
 * 
 * @author MOVELIGHTS
 * 
 */
public class BuildingsModel extends Tick implements Port{
	/**
	 * 房屋链表
	 */
	private List<Building> buildings = null;
	
	private LandModel land = null;

	
	public BuildingsModel (LandModel land){
		this.land = land;
	}


	/**
	 * 
	 * 初始化房屋
	 * 
	 */
	private void initBuilding() {
		// 初始化链表
		buildings = new ArrayList<Building>();
		// 对应地图加入房屋
		int[][] temp = this.land.getLand();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				switch (temp[i][j]) {
				case LandModel.SPACE:
					Building tempBuidling = new House(i, j);
					// 设置空地的属性为可以购买的
					tempBuidling.setPurchasability(true);
					buildings.add(tempBuidling);
					break;
				case LandModel.HOSPITAL:// 医院
					buildings.add(new Hospital(i, j));
					//设置全图医院点
					LandModel.hospital = new java.awt.Point(j * 60,i * 60);
//					System.out.println(LandModel.hospital );
					break;
				case LandModel.LOTTERY:
					buildings.add(new Lottery(i, j));
					break;
				case LandModel.NEWS:
					buildings.add(new News(i, j));
					break;
				case LandModel.ORIGIN:
					buildings.add(new Origin(i, j));
					break;
				case LandModel.PARK:
					buildings.add(new Park(i, j));
					break;
				case LandModel.PIONT_10:
					buildings.add(new Point(i, j, 10));
					break;
				case LandModel.PIONT_30:
					buildings.add(new Point(i, j, 30));
					break;
				case LandModel.PIONT_50:
					buildings.add(new Point(i, j, 50));
					break;
				case LandModel.SHOP:
					buildings.add(new Shop_(i, j));
					break;
				case LandModel.PRISON:// 监狱
					buildings.add(new Prison(i, j));
					//设置全图监狱点
					LandModel.prison = new java.awt.Point(j * 60, i * 60);
//					System.out.println(LandModel.prison );
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * 
	 * 获得房屋表
	 * 
	 * @return
	 */
	public List<Building> getBuilding(){
		return buildings;
	}
	/**
	 * 
	 * 获得当前位置房屋
	 * 
	 */
	public Building getBuilding(int x,int y){
		for (Building temp : this.buildings){
			if (temp.getPosX() == x && temp.getPosY() == y){
				return temp;
			}
		}
		return null;
	}
	/**
	 * 
	 * 开始游戏设置
	 * 
	 */
	public void startGameInit (){
		// 初始化房屋
		initBuilding();
	}

	@Override
	public void updata(long tick) {
		this.nowTick = tick;
		
	}
}