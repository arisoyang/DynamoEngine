package gameDemo;

import java.awt.Point;

import core.GameAction;

public class moveChar extends GameAction{

	public void run(){
		System.out.println("Mouse click "+Game.game.mouse.getPosition());
		Game.testunit.setTarget(Game.game.mouse.getPosition());
	}
	
}
