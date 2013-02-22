package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import inputters.KeyInformation;
import inputters.KeyboardInput;
import inputters.MouseInput;


public class InputMap { 

	private static HashMap<Integer,GameAction> KeyBoardHash=new HashMap<Integer,GameAction>();
	private static HashMap<Integer,GameAction> MouseHash=new HashMap<Integer,GameAction>();
	
	private static HashMap<KeyInformation,GameAction> KeyBoardStateHash=new HashMap<KeyInformation,GameAction>();
	private static HashMap<KeyInformation,GameAction> MouseStateHash=new HashMap<KeyInformation,GameAction>();
	
	public static HashMap<Integer,GameAction> getKeyboardHash(){
		return KeyBoardHash;
	}
	public static HashMap<Integer,GameAction> getMouseHash(){
		return MouseHash;
	}
	
	public static HashMap<KeyInformation,GameAction> getKeyboardStateHash(){
		return KeyBoardStateHash;
	}
	public static HashMap<KeyInformation,GameAction> getMouseStateHash(){
		return MouseStateHash;
	}
	
	
	public static void addKeyboardMap(int key, GameAction ga){
		KeyBoardHash.put(key, ga);
	}
	public static void addKeyboardMap(KeyInformation key, GameAction ga){
		if (!KeyBoardHash.containsKey(key.getKey())){
			KeyBoardHash.put(key.getKey(), new GameAction());
		}
		KeyBoardStateHash.put(key, ga);
	}
	/*
	public void addKeyboardComboMap(List<KeyEvent> keys, GameAction ga){
		
	}
	public void addKeySequenceMap(List<KeyEvent> keys, GameAction ga){
		
	}
	*/
	public static void addMouseButtonMap(int button, GameAction ga){
		MouseHash.put(button, ga);
	}
	public static void addMouseButtonMap(KeyInformation key, GameAction ga){
		if (!MouseHash.containsKey(key.getKey())){
			MouseHash.put(key.getKey(), new GameAction());
		}
		MouseStateHash.put(key, ga);
	}
	/*
	public void addJoystickMap(int koystickEvent, GameAction ga){
		
	}
	*/
	
//	public GameInput getInput(){
		//return the information about what is currently going on in the game
		
//	}
}