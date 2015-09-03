package ui;

import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import control.Control;


public class ShopButton extends JPanel implements MouseListener {
	
	private Shop shopUI;
	
	private Image[] img;
	
	private Image normalImage;
	private Image rolloverImage;
	private Image pressedImage;
	private Image disabledImage;
	
	private Image currentImage;

	private boolean enabled = true;
	
	private String name = null;

	private Control control;
	
	public ShopButton(Shop shopUI,String name, int x, int y,Control control) {
		this.shopUI = shopUI;
		this.name = name;//设置名称
		this.control = control;
		this.img = this.shopUI.createCardImg(name);
		this.normalImage = this.img[0];
		this.rolloverImage = this.img[1];
		this.pressedImage =this.img[2];
		this.disabledImage = this.img[3];
		this.currentImage = normalImage;
		this.setBounds(x, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
		this.addMouseListener(this);
	}

	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void paint(Graphics g) {
		this.setOpaque(false); // 背景透明
		if (enabled){
			g.drawImage(currentImage, this.getX(), this.getY(), this.getWidth(),
					this.getHeight(), this);
		} else if (!(name.equals("buy") || name.equals("cancel"))){
			g.drawImage(disabledImage, this.getX(), this.getY(), this.getWidth(),
					this.getHeight(), this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
			currentImage = pressedImage;
			if(enabled){
				if (this.name.equals("close")){//退出商店
					this.shopUI.moveToBack();
					this.control.exitShop();
				} else if (this.name.equals("cancel")){//取消当前选择
					this.shopUI.setChooseCard(null);
				} else if (this.name.equals("buy")){//购买当前选择
					this.control.buyCard(this.shopUI.getShop());
				} else {
					this.shopUI.setChooseCard(this);
				}
			}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
			currentImage = rolloverImage;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
			currentImage = rolloverImage;
	}

	@Override
	public void mouseExited(MouseEvent e) {
			currentImage = normalImage;
	}
}