package Pathfinding;
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



public class Tbastar {

	/**
	 * @param args
	 */

	private static int adj_dist = 10;
	private static int diag_dist = 14;
	private static int num_expansions = 5;
	private static int num_tracebacks = 5;
	
	public static void tbastar(MoveableObject subject, GameObject start, GameObject goal, GameObject[][] map){
		
		System.out.println("GOING FROM "+start.getX()+" "+start.getY()+" to "+goal.getX()+" "+goal.getY());
		
		boolean out_opt = false;
		
		boolean sol_found = false;
		boolean sol_found_and_traced = false;
		boolean done_trace = true;
		
		Node_collection most_promising = null;
		Node_collection new_most_promising = null;

		ArrayDeque<Node_collection> path_new = new ArrayDeque<Node_collection>();
		ArrayDeque<Node_collection> path_follow = new ArrayDeque<Node_collection>();
		
		List_collection lists = new List_collection();
		Node_collection start_obj = new Node_collection(start, 0+heuristic(start, goal), 0);
		lists.open_list.add(start_obj);
		lists.open_list_priority.add(start_obj);
		
		Node_collection loc = new Node_collection(start);
		
		
		while (!loc.getLoc().equals(goal)){
			System.out.println(loc.getLoc().getX()+" "+loc.getLoc().getY()+" "+goal.getX()+" "+goal.getY());
			if (!sol_found){
				System.out.println("^^^");
				sol_found = astar(subject, lists, start, goal, map, num_expansions);
				
			}
			if (!sol_found_and_traced){
				//if (done_trace){
					//System.out.println("DDD");
					if (lists.open_list_priority.size() == 0){
						out_opt = true;
						System.out.println("BREAKINGGGGGGGGGGGGGGGGGGG");
						break;
					}
				
					new_most_promising = lists.open_list_priority.peek();
					System.out.println("promising "+new_most_promising.getLoc().getX()+" "+new_most_promising.getLoc().getY());
					System.out.println("from "+new_most_promising.getCameFrom().getLoc().getX()+" "+new_most_promising.getCameFrom().getLoc().getY());
					boolean on_same_path = false;
					Node_collection current = new_most_promising;
					Stack<Node_collection> path_back = new Stack<Node_collection>();
					for (int i = 0; i < num_expansions; i++){
						if (most_promising != null && current.equals(most_promising)){
							on_same_path = true;
							break;
						}
						path_back.push(current);
						current = current.getCameFrom();
						if (current == null){
							break;
						}
					}
					if (on_same_path){
						while(path_back.size() != 0){
							path_new.addFirst(path_back.pop());
						}
					}else{
						path_new = new ArrayDeque<Node_collection>();
						path_new.add(new_most_promising);
					}
					most_promising = new_most_promising;
				//}
				done_trace = traceBack(path_new, loc.getLoc(), start, num_tracebacks);
				if (done_trace){
					path_follow = path_new;
					if (path_follow.peekLast().getLoc().equals(goal)){
						sol_found_and_traced = true;
					}
				}
			}
			Node_collection old_loc = loc;
			boolean path_contains = false;
			Node_collection current = path_follow.peekLast();
			while(current != null){
				if (current.equals(loc)){
					path_contains = true;
					break;
				}
				current = current.getCameFrom();
			}
			if (path_contains){
				System.out.println("YO");
				loc = path_follow.removeFirst();
			}else{
				if (!loc.getLoc().equals(start)){
					//LOC = LISTS.STEPBACK(LOC)
					loc = loc.getCameFrom();
				}else{
					//loc = loc.came_from;
				}
			}
			//System.out.println("TTT "+old_loc);
//			loc.setCameFrom(old_loc);
			//move to loc
		}
		//System.out.println("DONE");
		//System.out.println("ENDING AT "+most_promising.getLoc().getX()+" "+most_promising.getLoc().getY());
		//System.out.println("before that be  AT "+path_follow.peekFirst().getLoc().getX()+" "+path_follow.peekFirst().getLoc().getY());
		
		if (path_follow.size() != 0){
			path_follow.pollLast();
			//get rid of the first waypoint because its in the spot you are already in
		}
		
		System.out.println("S");
		
		if (out_opt){
			System.out.println("&");
			subject.setTarget(subject.getPos());
		}else{
			Node_collection last = path_follow.peekFirst();
			ArrayDeque<GameObject> final_path = new ArrayDeque<GameObject>();
			while(last!= null && last.getCameFrom() != null){
				System.out.println("BLOCK IS "+last.getLoc().getX()+" "+last.getLoc().getY());
				final_path.add(last.getLoc());
				last = last.getCameFrom();
				
			}

			subject.clearWaypoints();
			while(final_path.size() != 0){
				GameObject now = final_path.pollFirst();
				int next_x = (int) Math.floor((now.getX()*Game.OBJ_WIDTH) + (Game.OBJ_WIDTH/2));
				int next_y = (int) Math.floor((now.getY()*Game.OBJ_HEIGHT) + (Game.OBJ_HEIGHT/2));
				Point next_point = new Point(next_x, next_y);
							
				//System.out.println("A POINT AT "+next_x+" "+next_y);
				subject.addFirstWaypoint(next_point);				
			}
			subject.addWaypoint(subject.getTarget());

			
			/*
			while(path_follow.size() != 0){
				node_collection next = path_follow.pollLast();
				int next_x = (int) Math.floor((next.getLoc().getX()*Game.OBJ_WIDTH) + (Game.OBJ_WIDTH/2));
				int next_y = (int) Math.floor((next.getLoc().getY()*Game.OBJ_HEIGHT) + (Game.OBJ_HEIGHT/2));
				Point next_point = new Point(next_x, next_y);
							
				System.out.println("A POINT AT "+next_x+" "+next_y);
				subject.addWaypoint(next_point);
			}
			*/
		}
		System.out.println("FINAL POINT AT "+subject.getTarget().x+" "+subject.getTarget().y);
		
		
	}

	public static boolean traceBack(ArrayDeque<Node_collection> path_new, GameObject loc, GameObject start, int num_tracebacks){
		//System.out.println("path_new length "+path_new.size());
		for (int traces = 0; traces < num_tracebacks && path_new.peekLast() != null; traces++){
			System.out.println("now looking at "+path_new.peekLast().getLoc().getX()+" "+path_new.peekLast().getLoc().getY());
			if (path_new.peekLast().getLoc().equals(loc) || path_new.peekLast().getLoc().equals(start)){
				return true;
			}else{
				path_new.add(path_new.peekLast().getCameFrom());
			}
		}
		return false;
	}
	
	
	public static boolean astar(MoveableObject subject, List_collection lists, GameObject start, GameObject goal, GameObject[][] map, int num_expansions){
		ArrayList<Node_collection> closed_list = lists.closed_list;
		ArrayList<Node_collection> open_list = lists.open_list;
		PriorityQueue<Node_collection> open_list_priority = lists.open_list_priority;
		
		
		int expansions = 0;
		
		while (open_list.size() != 0 && expansions < num_expansions){
			expansions++;
			Node_collection current = open_list_priority.peek();	
			if (current.getLoc().equals(goal)){
				System.out.println("goal of "+goal.getX()+" "+goal.getY());
				System.out.println("FOUND GOAL leaving "+current.getLoc().getX()+" "+current.getLoc().getY()+" in open list g "+current.getG()+" "+current.getF()+" "+heuristic(current.getLoc(), goal));
				System.out.println("with came from "+current.getCameFrom().getLoc().getX()+" "+current.getCameFrom().getLoc().getY());
				//return reconstruct_path(came_from, goal)
				//listPath(current);
				//System.out.println("APPROXIMATE DISTANCE IS "+current.f_val);
				//break;
				
				return true;
			}
			//if its not the goal then we pop it off
			open_list_priority.poll();
			open_list.remove(current);
			System.out.println("Adding "+current.getLoc().getX()+" "+current.getLoc().getY()+" (height) "+current.getLoc().getHeight()+" to closed list g "+current.getG()+" "+current.getF()+" "+heuristic(current.getLoc(), goal));
			if (current.getCameFrom() != null){
				System.out.println("came from "+current.getCameFrom().getLoc().getX()+" "+current.getCameFrom().getLoc().getY());
			}
			closed_list.add(current);
			for (Node_collection neighbor : neighborNodes(subject, current, map)){
				if (!isInSet(neighbor, closed_list)){
					int tentative_g = current.getG() + distBetween(current, neighbor) + current.travelCost(neighbor);
					boolean is_in_open = isInSet(neighbor, open_list);
					if (is_in_open){
						//neighbor becomes the version already in the set
						neighbor = findInSet(neighbor, open_list);
					}
					if (!is_in_open || tentative_g < neighbor.getG()){
						//if neighbor not in openset or tentative_g_score <= g_score[neighbor] 
						neighbor.setCameFrom(current);
						neighbor.setG(tentative_g);
						neighbor.setF(neighbor.getG() + heuristic(neighbor.getLoc(), goal));
						if(!is_in_open){
							System.out.println("NOW open listing "+neighbor.getLoc().getX()+" "+neighbor.getLoc().getY());
							System.out.println("with a came fom of "+neighbor.getCameFrom().getLoc().getX()+" "+neighbor.getCameFrom().getLoc().getY());
							open_list.add(neighbor);
							open_list_priority.add(neighbor);
						}
					}
				}
			}
		}

		return false;
	}

	public static void listPath(Node_collection current){
		if (current.getCameFrom() != null){
			listPath(current.getCameFrom());
		}
		System.out.println(current.getLoc().getX()+" "+current.getLoc().getY());
	}
	
	/*
function reconstruct_path(came_from, current_node)
    if came_from[current_node] in set
        p := reconstruct_path(came_from, came_from[current_node])
        return (p + current_node)
    else
        return current_node
	 */        		


	public static int heuristic(GameObject loc1, GameObject loc2){
		int dist_x = loc1.getX()-loc2.getX();
		int dist_y = loc1.getY()-loc2.getY();
		//return (int) Math.floor(Math.sqrt((dist_x*dist_x) + (dist_y*dist_y))); //crow flies
		return (Math.abs(dist_x)+Math.abs(dist_y))*adj_dist; //manhatten
	}

	public static ArrayList<Node_collection> neighborNodes(MoveableObject subject, Node_collection focus, GameObject[][] map){
		int x = focus.getLoc().getX();
		int y = focus.getLoc().getY();

		ArrayList<Node_collection> nodes = new ArrayList<Node_collection>();
		
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
						Node_collection temp_node = new Node_collection(map[x+a][y+b]);
						if (focus.canWalk(subject, temp_node)){
							//nodes.add(temp_node);
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
		
		for (int a = 0; a < 3; a++){
			for (int b = 0; b <3; b++){
				if (valid[a][b]){
					Node_collection temp_node = new Node_collection(map[x+a-1][y+b-1]);
					nodes.add(temp_node);
				}
			}
		}
		
		/*
		 * keep this for if we restrict to just NESW
		if (x != 0){
			nodes.add(new node_collection(map[x-1][y]));
		}
		if(x != map.length-1){
			nodes.add(new node_collection(map[x+1][y]));
		}
		if (y != 0){
			nodes.add(new node_collection(map[x][y-1]));
		}
		if(y != map[x].length-1){
			nodes.add(new node_collection(map[x][y+1]));
		}
		*/
		return nodes;
	}

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

	public static int distBetween(Node_collection col1, Node_collection col2){
		if (col1.getLoc().getX() == col2.getLoc().getX() || col1.getLoc().getY() == col2.getLoc().getY()){
			return adj_dist;
		}
		return diag_dist;
	}

}
