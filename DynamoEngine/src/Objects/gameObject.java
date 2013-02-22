package Objects;

import gameDemo.Game;

public class gameObject {
	
	private int x, y, height;
	private String file_name;
	private drawObject draw_obj;
	
	public gameObject(int _x, int _y, int _height, String _file){
		x = _x;
		y = _y;
		height = _height;
		file_name = _file;
		draw_obj = new drawObject(_x*Game.OBJ_WIDTH, _y*Game.OBJ_HEIGHT, _height, _file);
	}
	
	public boolean equals(gameObject comp){
		return ((x==comp.x) && (y==comp.y));
	}
	
	public String getFile(){
		return file_name;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getHeight(){
		return height;
	}
	
	public drawObject getDrawObj(){
		return draw_obj;
	}
	
}
