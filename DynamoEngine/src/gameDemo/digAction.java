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
				System.out.println("Got here");
				if (o.getHeight()==0){
					o.getDrawObj().setImage("black.png");
				}else if (o.getHeight()==1){
					o.getDrawObj().setImage("black40.png");
				}
				else if (o.getHeight()==2){
					o.getDrawObj().setImage("black80.png");
				}
				else if (o.getHeight()==3){
					o.getDrawObj().setImage("black120.png");
				}else if (o.getHeight()==4){
					o.getDrawObj().setImage("black160.png");
				}
				else if (o.getHeight()==5){
					o.getDrawObj().setImage("black200.png"); 
				}
				else if (o.getHeight()==6){
					o.getDrawObj().setImage("black240.png"); 
				}

			}
		}
		Game.game.contextmenu.clearPopup();
		
		
		
	}
}
