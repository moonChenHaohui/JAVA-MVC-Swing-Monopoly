package ui.massage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import ui.JPanelGame;

public class MassageYesNo extends Massage {

	private JTextArea textArea = null;
	
	private JPanelGame panel = null;
	
	private MassageButton ok;
	private MassageButton cancel;
	
	
	public MassageYesNo(String titile,String information,JPanelGame panel) {
		super(titile, panel);
		// 增加按钮
		addButtons();
		// 增加文本域
		addTextArea();
		this.textArea.setText(information);
	}
	
	private void addButtons() {
		ok = new MassageButton("ok",this,18 * 8 ,131);
		add(ok);
		cancel = new MassageButton("cancel",this,18 * 8 + 64,131);
		add(cancel);
	}

	public void setInfo(String titileStr) {
		this.textArea.setText(titileStr);
	}

	

	private void addTextArea() {
		textArea = new JTextArea();
		textArea.setText("我了个去。。");
		textArea.setBounds(18, 39, 230, 50);
		textArea.setSelectedTextColor(Color.BLUE);
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		add(textArea);
	}
	@Override
	public void paint(Graphics g) {
		paints(g);
		
		ok.update(g);
		cancel.update(g);
		super.paint(g);
	}

	public void paints(Graphics g) {
		g.drawImage(bg, 0, 0, bg.getWidth(null), bg.getHeight(null), 0, 0,
				bg.getWidth(null), bg.getHeight(null), null);
	}

}
