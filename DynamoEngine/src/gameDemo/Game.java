package gameDemo;

import inputters.KeyboardInput;
import inputters.MouseInput;

import java.awt.event.KeyEvent;
import java.util.Date;

import core.GameModule;

public class Game {

	/**
	 * @param args
	 */
	
	public static GameModule game;
	
	
	public static void main(String[] args) {
		game = new GameModule();
		
		game.loop(); 
	}

}
