package core;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Objects.DrawObject;

public class RenderModule extends JFrame{

private Screen sc;

private ArrayList<Rectangle> redrawAreas;

public RenderModule(int x,int y){
	 sc = new Screen();
	 add(sc);
     setTitle("GameScreen");
     setDefaultCloseOperation(EXIT_ON_CLOSE);
     setSize(x, y);
     setLocationRelativeTo(null);
     setVisible(true);
     setResizable(false);
	
     redrawAreas = new ArrayList<Rectangle>();
}
public RenderModule(int x,int y, String name){
	this(x,y);
    setTitle(name);
}
public RenderModule(int x,int y, String name,int x2,int y2){
	this(x,y,name);
    sc.setLocation(x2, y2);
    System.out.println("bef: "+ sc.getLocation());
    System.out.println("Set: "+x2+", "+y2);
    System.out.println("aft: "+sc.getLocation());
}

public void update(int a){
	checkUpdatedAreas();
	sc.getState(a);
	sc.repaint();
}

public void addUpdatedArea(Rectangle rect){
	redrawAreas.add(rect);
}

public void checkUpdatedAreas(){
	for (DrawObject i:DrawObject.getList()){
		for(Rectangle rect:redrawAreas){
			if (rect != null || i.getRect() != null){
				if (rect.intersects(i.getRect())){
					addDrawObject(i);
					break;
				}
			}
		}
	}
	redrawAreas.clear();
}

public void checkAreaAboveLayer(Rectangle rect, int layer){
	for (DrawObject i:DrawObject.getList()){
		if (i.getLayer() > layer && rect.intersects(i.getRect())){
			addDrawObject(i);
		}
	}
	redrawAreas.clear();
}

public void addDrawObject(DrawObject object){
	sc.addDrawObject(object);
}

public void setDrawObjects(PriorityQueue<DrawObject> objects){
	sc.setDrawObjects(objects);
}


}
