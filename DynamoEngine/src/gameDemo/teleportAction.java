package gameDemo;

import java.awt.Point;
import java.awt.event.ActionEvent;

import core.MenuAction;

public class teleportAction extends MenuAction{

	public teleportAction(String i) {
		super(i);
		// TODO Auto-generated constructor stub
	}
	public void actionPerformed(ActionEvent e) {
		Game.game.contextmenu.clearPopup();
		Point p = Game.game.mouse.getPosition();
		Game.testunit.updatePosition(p.x,p.y);
		//teleport
		
	}

}
