package Pathfinding;

import Objects.GameObject;
import Objects.MoveableObject;

public class Node_collection {
	
	private GameObject location;
	private int f_val;
	private int g_val;
	private Node_collection came_from;
	
	public Node_collection(GameObject _loc){
		location = _loc;
		came_from = null;
	}
	
	public Node_collection(GameObject _loc, int _f, int _g){
		location = _loc;
		f_val = _f;
		g_val = _g;
		came_from = null;
	}
	
	public boolean equals(Node_collection comp){
		return (location.equals(comp.location));
	}
	
	public boolean canWalk(MoveableObject mover, Node_collection to){
		return (Math.abs((to.getLoc().getHeight() - location.getHeight())) <= mover.getHeightThreshhold());
	}
	
	public void setF(int _f){
		f_val = _f;
	}
	
	public void setG(int _g){
		g_val = _g;
	}
	
	public void setCameFrom(Node_collection _c){
		came_from = _c;
	}
	
	public int getF(){
		return f_val;
	}
	
	public int getG(){
		return g_val;
	}
	
	public GameObject getLoc(){
		return location;
	}
	
	public Node_collection getCameFrom(){
		return came_from;
	}
	
	public int travelCost(Node_collection to){
		return 0;
	}
}
