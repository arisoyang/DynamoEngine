package Objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import core.GameModule;

public class DrawObject {
	
	protected static ArrayList<DrawObject> list = new ArrayList<DrawObject>();
	
	protected int layer;
	
	protected Point pos;
	
	protected BufferedImage img;
	
	protected int height, width;
	
	public DrawObject(){	
		addList();
	}
	
	public DrawObject(int _x, int _y, String _img_name){
		addList();
		img = null;
		width = 0;
		height = 0;
		layer = 0;
		try {
		    img = ImageIO.read(new File(_img_name));
			width = img.getWidth();
			height = img.getHeight();
		} catch (IOException e) {
		}
		pos = new Point(_x, _y);
		addUpdatedArea(pos.x, pos.y, width, height);

	}
	
	public DrawObject(int _x, int _y, int _layer, String _img_name){
		addList();
		img = null;
		width = 0;
		height = 0;
		layer = _layer;
		try {
		    img = ImageIO.read(new File(_img_name));
			width = img.getWidth();
			height = img.getHeight();
		} catch (IOException e) {
		}
		pos = new Point(_x, _y);
		addUpdatedArea(pos.x, pos.y, width, height);

	}
	
	public DrawObject(int _x, int _y, int _width, int _height, String _img_name){
		addList();
		img = null;
		try {
		    img = ImageIO.read(new File(_img_name));
		} catch (IOException e) {
		}
		pos = new Point(_x, _y);
		height = _height;
		width = _width;
		layer = 0;
		addUpdatedArea(pos.x, pos.y, width, height);

	}
	
	public DrawObject(int _x, int _y, int _width, int _height, int _layer, String _img_name){
		addList();
		img = null;
		try {
		    img = ImageIO.read(new File(_img_name));
		} catch (IOException e) {
		}
		pos = new Point(_x, _y);
		height = _height;
		width = _width;
		layer = _layer;
	}
	
	public void addList(){
		list.add(this);
	}
	
	public void draw(Graphics g){
		g.drawImage(img, pos.x, pos.y, width, height, null);
		checkHigherLayerRedraw();
	}
	
	public void updatePosition(int _x, int _y){
		addUpdatedArea(pos.x, pos.y, width, height);
		pos.x = _x;
		pos.y = _y;
		addUpdatedArea(pos.x, pos.y, width, height);
	}
	
	public void updateSize(int _width, int _height){
		addUpdatedArea(pos.x, pos.y, width, height);
		width = _width;
		height = _height;
		addUpdatedArea(pos.x, pos.y, width, height);
	}
	
	public void resetSize(){
		if (img != null){
			addUpdatedArea(pos.x, pos.y, width, height);
			width = img.getWidth();
			height = img.getHeight();
			addUpdatedArea(pos.x, pos.y, width, height);
		}
	}
	
	public void checkHigherLayerRedraw(){
		Rectangle rect = new Rectangle(pos.x, pos.y, width, height);
		GameModule.renderer.checkAreaAboveLayer(rect, layer);
	}
	
	public void addUpdatedArea(int _x, int _y, int _width, int _height){
		GameModule.renderer.addUpdatedArea(new Rectangle(_x, _y, _width, _height));
	}
	
	public int getLayer(){
		return layer;
	}
	
	public static ArrayList<DrawObject> getList(){
		return list;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Rectangle getRect(){
		return new Rectangle(pos.x, pos.y, width, height);
	}
	
	public Point getPos(){
		return pos;
	}
	
	public void centerOnPoint(){
		updatePosition((int)Math.floor(pos.x-(width/2)), (int)Math.floor(pos.y-(height/2)));
	}
	
	/*
	
	boolean Graphics.drawImage(Image img,
            int x, int y,
            ImageObserver observer);
	
	g.drawImage(img, 
            0, 0, width, height,
            0, 0, imageWidth, imageHeight,
            null);  
	
	
	Each piece of terrain or object is represented by a custom “game object”.  
	These game objects, in addition to containing all the properties of the object (for example, health or flammability) 
	essentially serve as a map between all possible actions and what state it will transform into.  
	For example, a grass object may contain information that it is flammable and diggable.  
	It may also contain information that the “burn” action will turn it into dirt, and the “water” action may turn it into a garden.

*/

}
