package core;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Objects.GameObject;

public class ContextMenuModule {

	public ArrayList<GameObject> actionableObjs = new ArrayList<GameObject>();
	public ArrayList<JPopupMenu> menus = new ArrayList<JPopupMenu>();
	private Component screen;
	
	public ContextMenuModule(Component s){
		
		screen = s;
	}
	
	
	public void addActionable(GameObject o){
		
		actionableObjs.add(o);
	}
	
	public void createMenus(){
		
		for (GameObject i: actionableObjs){
			//make a popup
			
			JPopupMenu popup = new JPopupMenu();
			for (MenuAction j: i.getContextActions()){
				JMenuItem MenuItem = new JMenuItem(j.id);
				popup.add(MenuItem);
			}
			//store in list
			menus.add(popup);
			
			
		}
		
	}
	public void showPopup(MouseEvent e){
		if(e.isPopupTrigger()){
			int xVal = e.getX();
			int yVal = e.getY();
			int loc = findGameObj(xVal,yVal);
			//find game object at e.getX() e.getY()
			//pull the menu from the list that maches that game object
			menus.get(loc).show(screen,xVal,yVal);
		}
	}
	public int findGameObj(int x,int y){
		//placeholder function
		
		return 0;
	}
	
}
