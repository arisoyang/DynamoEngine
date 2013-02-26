package Objects;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;


import gameDemo.Game;

public class MoveableObject {

	protected static ArrayList<MoveableObject> list = new ArrayList<MoveableObject>();
	
	protected Point pos;
	protected double true_x, true_y;
	
	int speed;
	
	private String file_name;
	private DrawObject draw_obj;
	
	private Point target;
	protected ArrayDeque<Point> waypoints;
	
	public MoveableObject(int _x, int _y, int _layer, String _file, int _speed){
		true_x = _x;
		true_y = _y;
		speed = _speed;
		waypoints = new ArrayDeque<Point>();
		pos = new Point(_x, _y);
		target = pos;
		file_name = _file;
		draw_obj = new DrawObject(_x, _y, _layer, _file);
//		draw_obj.centerOnPoint();
		addList();
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
		pos.x = ix;
		pos.y = iy;
		draw_obj.updatePosition(ix, iy);
//		draw_obj.centerOnPoint();
	}
	
	public DrawObject getDrawObj(){
		return draw_obj;
	}
	
	public void setTarget(Point _tar){
		waypoints.clear();
		target = _tar;
	}
	
	public void addList(){
		list.add(this);
	}
	
	public static ArrayList<MoveableObject> getList(){
		return list;
	}
	
	public ArrayDeque<Point> getWaypoints(){
		return waypoints;
	}
	
	public void addWaypoint(Point p){
		waypoints.addLast(p);
	}
	
	public void move(){
		int distance_left = speed;
		System.out.println("distance left "+distance_left);
		while(waypoints.size() > 0 && pos.distance(waypoints.peekFirst()) <= speed){
			System.out.println("can reach waypoint in one jump "+pos.distance(waypoints.peekFirst()));

			distance_left -= pos.distance(waypoints.peekFirst());
			pos = waypoints.pollFirst();
		}
		
		double new_x = true_x;
		double new_y = true_y;
		
		if (waypoints.size() > 0){
			
			double angle;
			if ((pos.x-waypoints.peekFirst().x) != 0){
				angle = Math.atan((pos.y-waypoints.peekFirst().y)/(pos.x-waypoints.peekFirst().x));
				if (pos.x > waypoints.peekFirst().x){
					angle += Math.PI;
				}
			}else if (pos.y > waypoints.peekFirst().y){
				angle = 3*Math.PI/2;
			}else{
				angle = Math.PI/2;
			}
			
			System.out.println("from "+pos.x+" "+pos.y+" to "+waypoints.peekFirst().x+" "+waypoints.peekFirst().y+" is "+angle);
			
			new_x += Math.cos(angle)*distance_left;
			new_y += Math.sin(angle)*distance_left;
		}
		updatePosition(new_x, new_y);
	}
	
}
