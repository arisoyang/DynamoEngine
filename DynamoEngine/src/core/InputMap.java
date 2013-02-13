package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import inputters.KeyboardInput;
import inputters.MouseInput;


public class InputMap { 

	private static HashMap<Integer,GameAction> KeyBoardHash=new HashMap<Integer,GameAction>();
	private static HashMap<Integer,GameAction> MouseHash=new HashMap<Integer,GameAction>();
	
	public static HashMap<Integer,GameAction> getKeyboardHash(){
		return KeyBoardHash;
	}
	public static HashMap<Integer,GameAction> getMouseHash(){
		return MouseHash;
	}
	
	
	public void addKeyboardMap(int key, GameAction ga){
		KeyBoardHash.put(key, ga);
	}
	/*
	public void addKeyboardComboMap(List<KeyEvent> keys, GameAction ga){
		
	}
	public void addKeySequenceMap(List<KeyEvent> keys, GameAction ga){
		
	}
	*/
	public void addMouseButtonMap(int button, GameAction ga){
		MouseHash.put(button, ga);
	}
	/*
	public void addJoystickMap(int koystickEvent, GameAction ga){
		
	}
	*/
	
//	public GameInput getInput(){
		//return the information about what is currently going on in the game
		
//	}
}