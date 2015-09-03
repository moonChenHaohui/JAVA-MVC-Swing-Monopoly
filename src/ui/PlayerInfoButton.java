package ui;

import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.Main;

import control.Control;


public class PlayerInfoButton extends JPanel implements MouseListener {
	
	private Image[] img = {
			new ImageIcon("images/end/button/normal.png").getImage(),
			new ImageIcon("images/end/button/mouseOver.png").getImage(),
			new ImageIcon("images/end/button/pressed.png").getImage()
	};
	
	private Image normalImage;
	private Image rolloverImage;
	private Image pressedImage;

	private Image currentImage;

	private boolean enabled = true;
	
	private String name = null;

	private Control control;
	
	public PlayerInfoButton(String name, int x, int y) {
		this.name = name;//…Ë÷√√˚≥∆
		this.normalImage = this.img[0];
		this.rolloverImage = this.img[1];
		this.pressedImage =this.img[2];
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
		this.setOpaque(false); // ±≥æ∞Õ∏√˜
		if (enabled){
			g.drawImage(currentImage, this.getX(), this.getY(), this.getWidth(),
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
				System.exit(0);
				Main.main(null);
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