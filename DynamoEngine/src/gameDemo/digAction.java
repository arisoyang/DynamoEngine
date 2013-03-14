package gameDemo;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import Objects.GameObject;

import core.MenuAction;

public class digAction extends MenuAction {
	public digAction(String i)
	{
		super(i);
		// TODO Auto-generated constructor stub
	}
	public void actionPerformed(ActionEvent e) {
		Game.game.contextmenu.clearPopup();
		Point p = Game.game.mouse.getPosition();
		int x = p.x;
		int y = p.y;
		// find tile at that location and lower it.
		for (GameObject o : Game.game.objects.objects){
			Rectangle r = o.getDrawObj().getRect();
			if(r.contains(x, y) && o.getHeight() != 0){
				o.setHeight(o.getHeight()-1);
				o.getDrawObj().setImage("tile"+o.getHeight()+".png");
				

			}
		}
		Game.game.contextmenu.clearPopup();
		
		
		
	}
}
