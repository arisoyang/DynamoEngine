package gameDemo;

import java.awt.Point;

import core.GameAction;

public class contextMenu extends GameAction{

	public void run(){
		System.out.println("Mouse right click "+Game.game.mouse.getPosition());
		//open context menu.
	}
	
}
