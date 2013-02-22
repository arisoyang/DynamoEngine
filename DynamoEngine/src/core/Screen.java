package core;

import javax.swing.JPanel;

import Objects.drawObject;
import Objects.drawObjectLayerCompare;


import java.awt.*;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

public class Screen extends JPanel {
	private int temp; // temporary struct for rendering data
	
	public PriorityQueue<drawObject> draw_objs;
	
	public Screen(){
		setDoubleBuffered(true);
		draw_objs  = new PriorityQueue<drawObject>(1, new drawObjectLayerCompare());
	}

   public void paint(Graphics g) {	 
	   while (draw_objs.size() != 0){
		   drawObject d = draw_objs.poll();
		  // System.out.println(d.getLayer()+" "+d.getPos());
		   d.draw(g);
	   }

	   
	   //super.paint(g);

	   
	   //Paint from whatever
   }
   public void getState(int a){
	   temp = a;
	   return;
   }
   
   public void addDrawObject(drawObject object){
	   if (!draw_objs.contains(object)){
			//System.out.println(object.getLayer());

		   draw_objs.add(object);
	   }
	}
   
   public void setDrawObjects(PriorityQueue<drawObject> objects){
		draw_objs = objects;
	}
    
}