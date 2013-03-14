package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAction extends GameAction implements ActionListener{

	public String id;
	
	public MenuAction(String i)
	{
		id = i;
	}
	public void run(){
		//override this function.
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// Overwrite this function
		
	}
	
	
}
