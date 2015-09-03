package ui.massage;

import java.awt.Image;
import java.awt.Point;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


import ui.JPanelGame;

public class Massage extends JPanel {

	protected Image bg = new ImageIcon("images/massage/massage.png").getImage();

	protected Point origin = new Point(); // 全局的位置变量，用于表示鼠标在窗口上的位置

	protected int x, y, w, h;

	protected String titileStr = "标题";
	protected JLabel titile  = null;

	protected JPanelGame panel = null;


	
	/**
	 * 
	 * 创建一个信息对话框
	 * 
	 */
	protected Massage(String titile,JPanelGame panel) {
		this.titileStr = titile;
		// 初始化位置
		initBounds();
		setLayout(null);
		// 增加标题
		addTitle();
		// 增加文本域
		// 增加监听
		addListener();
		// 设置背景透明
		setOpaque(false);
		this.panel = panel;
	}

	public void setTitileStr(String titileStr) {
		this.titileStr = titileStr;
		this.titile.setText("<html><font color='white' >"+titileStr+"</font></html>");
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
	
	private void initBounds() {
		this.x = (950 - bg.getWidth(null)) /2 ;
		this.y = (650 - bg.getHeight(null)) / 2;
		this.w = bg.getWidth(null);
		this.h =bg.getHeight(null);
		setBounds(x, y, w, h);
	}

	private void addTitle() {
		titile = new JLabel("<html><font color='white' >"+titileStr+"</font></html>");
		titile.setBounds(18, 4, 230, 20);
		add(titile);
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
	/**
	 * 
	 * 按下ok按钮
	 * 
	 */
	public void ok() {
		System.out.println("ok");
	}
	
	/**
	 * 
	 * 按下cancel按钮
	 * 
	 */
	public void cancel() {
		System.out.println("cancel");
	}
}
