package Pathfinding;
//import location_compare;
//import node;
//import node_collection;
import gameDemo.Game;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import Objects.GameObject;
import Objects.MoveableObject;



public class LRTA {

	/**
	 * @param args
	 */

	private static int adj_dist = 10;
	private static int diag_dist = 14;

	
	private MoveableObject subject;
	private GameObject start, goal;
	private GameObject[][] map;

	private int[][] heuristic_map;
		
	public LRTA(MoveableObject _subject, GameObject _goal, GameObject[][] _map){
		subject = _subject;
		goal = _goal;
		map = _map;
		heuristic_map = new int[map.length][map[0].length];
		
		for (int i = 0; i < heuristic_map.length; i++){
			for (int j = 0; j < heuristic_map[i].length; j++){
				heuristic_map[i][j] = heuristic(map[i][j], goal);
			}
		}
		
		System.out.println("NEW LRTA PATH to "+goal.getX()+" "+goal.getY());
		
	}
	 
	
	public void lrtastep(GameObject loc){
		System.out.println("Beginning an LRTA step from "+loc.getX()+" "+loc.getY());
		int f_min = Integer.MAX_VALUE;
			
		GameObject best_neigh = null;
				
		for (GameObject neighbor : neighborGameObjects(loc)){
			int k = distBetween(loc, neighbor);
			int h = heuristic_map[neighbor.getX()][neighbor.getY()];
			int f = k+h;
			if (h == heuristic(neighbor, goal)){
				System.out.println("Examining "+neighbor.getX()+" "+neighbor.getY()+" k "+k+" h "+h+" f "+f);
			}else{
				System.out.println("Examining "+neighbor.getX()+" "+neighbor.getY()+" k "+k+" h "+h+" f "+f+", with an original h of "+heuristic(neighbor, goal));
			}
			if (f < f_min){
				f_min = f;
				best_neigh = neighbor;
			}else if (f == f_min && Math.random() < .5){
				f_min = f;
				best_neigh = neighbor;
			}
		}
		
		
		
		if (f_min == Integer.MAX_VALUE){
			//trapped
			if ((goal.getX() == loc.getX()) && (goal.getY() == loc.getY())){
				subject.setWaypoint(subject.getTarget());				
			}else{
				subject.setTarget(subject.getPos());								
			}
		}else{
			if (heuristic_map[loc.getX()][loc.getY()] < f_min){
				heuristic_map[loc.getX()][loc.getY()] = f_min;
			}
			System.out.println("Setting h of "+loc.getX()+" "+loc.getY()+" to "+heuristic_map[loc.getX()][loc.getY()]);
			System.out.println("Moving to "+best_neigh.getX()+" "+best_neigh.getY());
			if (best_neigh.equals(goal)){
				subject.setWaypoint(subject.getTarget());		
			}else{
				int next_x = (int) Math.floor((best_neigh.getX()*Game.OBJ_WIDTH) + (Game.OBJ_WIDTH/2));
				int next_y = (int) Math.floor((best_neigh.getY()*Game.OBJ_HEIGHT) + (Game.OBJ_HEIGHT/2));
				//System.out.println("waypoint of "+next_x+" "+next_y);
				subject.setWaypoint(new Point(next_x, next_y));
			}
		}
		/*
		try {
			//Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	

	public int heuristic(GameObject loc1, GameObject loc2){
		int dist_x = loc1.getX()-loc2.getX();
		dist_x *= adj_dist;
		int dist_y = loc1.getY()-loc2.getY();
		dist_y *= adj_dist;
		//return (int) Math.floor(Math.sqrt((dist_x*dist_x) + (dist_y*dist_y))); //crow flies
		return (Math.abs(dist_x)+Math.abs(dist_y))*adj_dist; //manhatten
	}
	
	
	public ArrayList<GameObject> neighborGameObjects(GameObject focus){
		int x = focus.getX();
		int y = focus.getY();

		ArrayList<GameObject> neighbors = new ArrayList<GameObject>();
		
		boolean[][] valid = new boolean[3][3];
		
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				valid[i][j] = false;
			}
		}
		
		for (int a = -1; a < 2; a++){
			for (int b = -1; b < 2; b++){
				if (a != 0 || b != 0){
					if (x+a >= 0 && x+a < map.length && y+b >= 0 && y+b < map[x+a].length){
						if (Math.abs(map[x][y].getHeight() - map[x+a][y+b].getHeight()) <= 1){
							valid[a+1][b+1] = true;
						}
					}
				}
			}
		}
		
		if (valid[0][0]){
			if (!valid[0][1] || !valid[1][0]){
				valid[0][0] = false;
			}
		}
		
		if (valid[0][2]){
			if (!valid[1][2] || !valid[0][1]){
				valid[0][2] = false;
			}
		}
		
		if (valid[2][0]){
			if (!valid[1][0] || !valid[2][1]){
				valid[2][0] = false;
			}
		}
		
		if (valid[2][2]){
			if (!valid[1][2] || !valid[2][1]){
				valid[2][2] = false;
			}
		}
		
		valid[0][0] = false;
		valid[0][2] = false;
		valid[2][0] = false;
		valid[2][2] = false;
		
		for (int a = 0; a < 3; a++){
			for (int b = 0; b <3; b++){
				if (valid[a][b]){
					
					System.out.println("adding node at "+(x+a-1)+" "+(y+b-1)+" with height "+map[x+a-1][y+b-1].getHeight());
					
					neighbors.add(map[x+a-1][y+b-1]);
				}
			}
		}
		return neighbors;
	}

	
	/*
	public static boolean isInSet(Node_collection check, ArrayList<Node_collection> list){
		for (int i = 0; i < list.size(); i++){
			if (check.equals(list.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public static Node_collection findInSet(Node_collection check, ArrayList<Node_collection> list){
		for (int i = 0; i < list.size(); i++){
			if (check.equals(list.get(i))){
				return list.get(i);
			}
		}
		return null;
	}
	*/

	public int distBetween(GameObject col1, GameObject col2){
		if (col1.getX() == col2.getX() || col1.getY() == col2.getY()){
			return adj_dist;
		}
		return diag_dist;
	}

}
