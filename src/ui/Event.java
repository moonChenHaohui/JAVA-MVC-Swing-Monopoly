package ui;

import java.awt.Graphics;

import model.EventsModel;


/**
 * ÊÂ¼þÏÔÊ¾UI
 * 
 * @author MOVELIGHTS
 * 
 */
public class Event extends Layer {

	private EventsModel events = null;
	
	protected Event(int x, int y, int w, int h,EventsModel events) {
		super(x, y, w, h);
		this.events = events;
	}

	public void paint(Graphics g) {
		this.paintEvent(g);
	}
	
	private void paintEvent(Graphics g) {
		if (events.getStartTick() < events.getNowTick() && events.getNextTick() >= events.getNowTick()){
			//±³¾°Í¼¸²¸Ç
			g.drawImage(events.getBG_BRACK(), 0, 0, 2000, 2000, 0, 0, 1, 1, null);
			g.drawImage(events.getImg(),events.getImgPoint().getPosX(), events.getImgPoint().getPosY(),
					events.getImgPoint().getPosX() + events.getImg().getWidth(null),
					events.getImgPoint().getPosY() + events.getImg().getHeight(null), 0, 0,
					events.getImg().getWidth(null), events.getImg().getHeight(null), null);

		}
	}
	
	@Override
	public void startPanel() {	
	}

}
