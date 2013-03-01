package core;

import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import Objects.GameObject;

public class ContextMenuModule {

	public ArrayList<GameObject> actionableObjs = new ArrayList<GameObject>();
	public ArrayList<JPopupMenu> menus = new ArrayList<JPopupMenu>();
	
	public void addActionable(GameObject o){
		
		actionableObjs.add(o);
	}
	
	public void createMenus(){
		
		for (GameObject i: actionableObjs){
			//make a popup
			//store in list
			JPopupMenu popup = new JPopupMenu();
			for (MenuAction j: i.getContextActions()){
				JMenuItem MenuItem = new JMenuItem(j.id);
				popup.add(MenuItem);
			}
			
			
		}
		
	}
	
}
