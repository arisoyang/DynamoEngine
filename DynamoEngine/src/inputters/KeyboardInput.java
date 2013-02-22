package inputters;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * 
 * @author Joe Muoio
 * based off code from a java games keyboard input tutorial.
 */
public class KeyboardInput implements KeyListener {

	private static final int NUMKEYS=256; 
	
	private boolean[] currentKeys;
	private KeyState[] keys;
	private KeyState[] keys_prev;
	
	public KeyboardInput(){
		currentKeys=new boolean[NUMKEYS];
		keys = new KeyState[NUMKEYS];
		keys_prev = new KeyState[NUMKEYS];
		for(int i=0;i<NUMKEYS;i++){
			keys[i]=KeyState.RELEASED;
			keys_prev[i]=KeyState.RELEASED;
		}
	}
	
	public void poll(){
		for (int i=0;i<NUMKEYS;i++){
			if(currentKeys[i]){
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
	
	public KeyState getButtonState (int code){
		return keys[code];
	}
	
	public boolean keyChanged (int code){
		boolean changed = false;
		if(keys_prev[code]!=keys[code]){
			changed = true;
		}
		keys_prev[code] = keys[code];
		return changed;
	}
	
	public boolean keyDown(int code){
		if(keys[code]==KeyState.PRESSED || keys[code]==KeyState.TAPPED){
			return true;
		}
		return false;
	}
	public boolean keyTapped(int code){
		if(keys[code]==KeyState.TAPPED){
			return true;
		}
		return false;
	}
	
	
	@Override
 	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code<NUMKEYS && code>=0){
			currentKeys[code]=true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code<NUMKEYS && code>=0){
			currentKeys[code]=false;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		//only here because it is necessary for KeyListener.		
	}

}
