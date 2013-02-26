package Pathfinding;

import Objects.GameObject;

public class node_collection {
	
	private GameObject location;
	private int f_val;
	private int g_val;
	private node_collection came_from;
	
	public node_collection(GameObject _loc){
		location = _loc;
		came_from = null;
	}
	
	public node_collection(GameObject _loc, int _f, int _g){
		location = _loc;
		f_val = _f;
		g_val = _g;
		came_from = null;
	}
	
	public boolean equals(node_collection comp){
		return (location.equals(comp.location));
	}
	
	public boolean canWalk(node_collection to){
		return (to.getLoc().getHeight() == location.getHeight());
	}
	
	public void setF(int _f){
		f_val = _f;
	}
	
	public void setG(int _g){
		g_val = _g;
	}
	
	public void setCameFrom(node_collection _c){
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
	
	public node_collection getCameFrom(){
		return came_from;
	}
	
	public int travelCost(node_collection to){
		return 0;
	}
}
