package inputters;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseInput implements MouseListener, MouseMotionListener {

	private static final int NUMKEYS=3; 
	
	private boolean[] state;
	private KeyState[] keys;
	private Point mousePos;
	private Point currentPos;
	
	
	public MouseInput(){
		mousePos = new Point(0,0);
		currentPos = new Point (0,0);
		state = new boolean[ NUMKEYS ];
	    keys = new KeyState[ NUMKEYS ];
	    for (int i =0;i<NUMKEYS;i++){
	    	keys[i]=KeyState.RELEASED;
	    }
	}
	public void poll(){
		mousePos = new Point( currentPos );
		for (int i=0;i<NUMKEYS;i++){
			if(state[i]){
				if(keys[i]==KeyState.RELEASED){
					keys[i]=KeyState.TAPPED;
				}
				else{
					keys[i]=KeyState.PRESSED;
				}
			}
			else{
				keys[i]=KeyState.RELEASED;
			}
		}
	}
	
	public Point getPosition(){
		return mousePos;
	}
	public boolean buttonDown (int code){
		if(keys[code]==KeyState.PRESSED || keys[code]==KeyState.TAPPED){
			return true;
		}
		return false;
	}
	public boolean buttonTapped(int code){
		if(keys[code]==KeyState.TAPPED){
			return true;
		}
		return false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// not needed??
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		state[e.getButton()-1] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		state[e.getButton()-1] = false;
	}
	
	public void mouseDragged(MouseEvent e){
		mouseMoved(e);
	}
	public void mouseMoved(MouseEvent e){
		currentPos=e.getPoint();
	}

}
