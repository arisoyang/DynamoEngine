package Objects;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import Pathfinding.LRTA;
import Pathfinding.PathModule;


import gameDemo.Game;

public class MoveableObject {

	protected static ArrayList<MoveableObject> list = new ArrayList<MoveableObject>();
	
	protected Point pos;
	protected double true_x, true_y;
	
	int speed;
	int height_thresh;
	
	private String file_name;
	private DrawObject draw_obj;
	
	private Point target;
	protected Point waypoint;
	//Tbastar pathfinder;
	private LRTA pathfinder;
	
	public MoveableObject(int _x, int _y, int _height_thresh, int _layer, String _file, int _speed){
		true_x = _x;
		true_y = _y;
		height_thresh = _height_thresh;
		speed = _speed;
		waypoint = new Point();
		pos = new Point(_x, _y);
		target = pos;
		file_name = _file;
		draw_obj = new DrawObject(_x, _y, _layer, _file);
		updatePosition(_x, _y);
		addList();
		pathfinder = null;
	}
	
	public double getTrueX(){
		return true_x;
	}
	
	public double getTrueY(){
		return true_y;
	}
	
	public Point getPos(){
		return pos;
	}
	
	public Point getTarget(){
		return target;
	}
	
	public String getFile(){
		return file_name;
	}
	
	public void updatePosition(double _x, double _y){
		true_x = _x;
		true_y = _y;
		int ix = (int)Math.floor(_x);
		int iy = (int)Math.floor(_y);
		int drawx = (int)Math.round(ix-(draw_obj.getWidth()/2))-1;
		int drawy = (int)Math.round(iy-(draw_obj.getHeight()/2));
//		System.out.println("x is "+ix+" draw is "+drawx);
		pos.x = ix;
		pos.y = iy;
		draw_obj.updatePosition(drawx, drawy);
//		draw_obj.centerOnPoint();
	}
	
	public DrawObject getDrawObj(){
		return draw_obj;
	}
	
	public void setTarget(Point _tar){
		waypoint = null;
		target = _tar;
		restartPathfinding();
	}
	
	public void restartPathfinding(){
		if ((pos.x != target.x || pos.y != target.y)){
			//pathfinder = new Tbastar(this, Game.getBlock(pos.x, pos.y), Game.getBlock(target.x, target.y), Game.getMap());
			pathfinder = PathModule.getLRTA(this, Game.getBlock(target.x, target.y), Game.getMap());
			//System.out.println("iterating");
			//pathfinder.iteration();
			pathfinder.lrtastep(Game.getBlock(pos.x, pos.y));
		}
	}
	
	public void addList(){
		list.add(this);
	}
	
	public static ArrayList<MoveableObject> getList(){
		return list;
	}
	
	public void clearWaypoint(){
		waypoint = null;
	}
	
	public void setWaypoint(Point p){
		waypoint = p;
	}
	
	public void move(){
		double distance_left = speed;
		
		while(waypoint != null &&pos.distance(waypoint) <= distance_left){
			distance_left -= pos.distance(waypoint);
			pos = waypoint;
			//pathfinder.iteration();
			if ((pos.x != target.x || pos.y != target.y)){
				pathfinder.lrtastep(Game.getBlock(pos.x, pos.y));
			}else{
				pos = target;
				updatePosition(target.x, target.y);
				System.out.println("COMPLETELY ARRIVED");
				distance_left = 0;
				clearWaypoint();
			}
		}
		
		if (waypoint != null){
		
			double new_x = true_x;
			double new_y = true_y;
			
			if ((pos.x != target.x || pos.y != target.y)){
				
				double angle;
				if ((pos.x-waypoint.x) != 0){
					angle = Math.atan((pos.y-waypoint.y)/(pos.x-waypoint.x));
					if (pos.x > waypoint.x){
						angle += Math.PI;
					}
				}else if (pos.y > waypoint.y){
					angle = 3*Math.PI/2;
				}else{
					angle = Math.PI/2;
				}
				
				//System.out.println("from "+pos.x+" "+pos.y+" to "+waypoints.peekFirst().x+" "+waypoints.peekFirst().y+" is "+angle);
				
				new_x += Math.cos(angle)*distance_left;
				new_y += Math.sin(angle)*distance_left;
			}
			updatePosition(new_x, new_y);
		}
	}

	public int getHeightThreshhold() {
		return height_thresh;
	}
	
}
