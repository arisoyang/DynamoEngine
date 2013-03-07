package inputters;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseInput implements MouseListener, MouseMotionListener {

	private static final int NUMKEYS=3; 
	
	private boolean[] state;
	private KeyState[] keys;
	private KeyState[] keys_prev;
	private Point mousePos;
	private Point currentPos;
	private Point lastPos;
	
	
	public MouseInput(){
		mousePos = new Point(0,0);
		currentPos = new Point (0,0);
		lastPos = new Point (0,0);
		state = new boolean[ NUMKEYS ];
	    keys = new KeyState[ NUMKEYS ];
	    keys_prev = new KeyState[ NUMKEYS ];
	    for (int i =0;i<NUMKEYS;i++){
	    	keys[i]=KeyState.RELEASED;
	    	keys_prev[i]=KeyState.RELEASED;
	    }
	}
	public void poll(){
		mousePos = new Point( currentPos );
		for (int i=0;i<NUMKEYS;i++){
			//System.out.println(i+" "+state[i]);
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
	public KeyState getButtonState (int code){
		return keys[code];
	}
	public boolean mouseMoved (){
		boolean changed = false;
		if(!lastPos.equals(currentPos)){
			changed = true;
		}
		lastPos = currentPos;
		return changed;
	}
	public boolean buttonChanged (int code){
		boolean changed = false;
		if(keys_prev[code]!=keys[code]){
			changed = true;
		}
		keys_prev[code] = keys[code];
		return changed;
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
		checkPopup(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		state[e.getButton()-1] = false;
		checkPopup(e);
		
	}
	
	private void checkPopup(MouseEvent e){
		if(e.isPopupTrigger()){
			//spawn menu
		}
	}
	public void mouseDragged(MouseEvent e){
		mouseMoved(e);
	}
	public void mouseMoved(MouseEvent e){
		currentPos=e.getPoint();
		//this may need to be removed
		currentPos.y -= 25;
		if (currentPos.y < 0){
			currentPos.y = 0;
		}
	}

}
