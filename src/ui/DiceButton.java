package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import control.Control;

import model.DiceModel;

public class DiceButton extends JPanel implements MouseListener {
	
	private Control control;
	private DiceModel dice;
	
	private Image normalImage;
	private Image rolloverImage;
	private Image pressedImage;

	private Image currentImage;

	private boolean enabled = true;

	public DiceButton(Control control, int x, int y) {
		this.control = control;
		this.dice = control.getDice();
		this.normalImage = dice.getDiceIMG()[0].getImage();
		this.rolloverImage = dice.getDiceIMG()[1].getImage();
		this.pressedImage =dice.getDiceIMG()[2].getImage();
		
		this.currentImage = normalImage;
		this.setBounds(x, y, 50, 50);
		this.addMouseListener(this);
		repaint();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void paint(Graphics g) {
		this.setOpaque(false); // ±³¾°Í¸Ã÷
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
				// °´ÏÂ
				control.pressButton();
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