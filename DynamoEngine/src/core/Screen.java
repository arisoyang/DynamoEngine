package core;

import javax.swing.JPanel;
import java.awt.*;

public class Screen extends JPanel {
	private int temp; // temporary struct for rendering data
	
	public Screen(){
		setDoubleBuffered(true);
		
	}

   public void paint(Graphics g) {
	   super.paint(g);
	   
	   //Paint from whatever
   }
   public void getState(int a){
	   temp = a;
	   return;
   }
    
}