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



public class tbastar {

	/**
	 * @param args
	 */

	private static int adj_dist = 10;
	private static int diag_dist = 14;
	private static int num_expansions = 5;
	private static int num_tracebacks = 5;
	
	public static void tbastar(MoveableObject subject, GameObject start, GameObject goal, GameObject[][] map){
		
		System.out.println("GOING FROM "+start.getX()+" "+start.getY()+" to "+goal.getX()+" "+goal.getY());
		
		boolean sol_found = false;
		boolean sol_found_and_traced = false;
		boolean done_trace = true;
		
		node_collection most_promising = null;
		node_collection new_most_promising = null;

		ArrayDeque<node_collection> path_new = new ArrayDeque<node_collection>();
		ArrayDeque<node_collection> path_follow = new ArrayDeque<node_collection>();
		
		list_collection lists = new list_collection();
		node_collection start_obj = new node_collection(start, 0+heuristic(start, goal), 0);
		lists.open_list.add(start_obj);
		lists.open_list_priority.add(start_obj);
		
		node_collection loc = new node_collection(start);
		
		
		while (!loc.getLoc().equals(goal)){
			System.out.println(loc.getLoc().getX()+" "+loc.getLoc().getY()+" "+goal.getX()+" "+goal.getY());
			if (!sol_found){
				System.out.println("^^^");
				sol_found = astar(lists, start, goal, map, num_expansions);
				
			}
			if (!sol_found_and_traced){
				//if (done_trace){
					//System.out.println("DDD");
					new_most_promising = lists.open_list_priority.peek();
					System.out.println("promising "+new_most_promising.getLoc().getX()+" "+new_most_promising.getLoc().getY());
					//System.out.println("from "+new_most_promising.getCameFrom().getLoc().getX()+" "+new_most_promising.getCameFrom().getLoc().getY());
					boolean on_same_path = false;
					node_collection current = new_most_promising;
					Stack<node_collection> path_back = new Stack<node_collection>();
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
						path_new = new ArrayDeque<node_collection>();
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
			node_collection old_loc = loc;
			boolean path_contains = false;
			node_collection current = path_follow.peekLast();
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
				if (!loc.equals(start)){
					//LOC = LISTS.STEPBACK(LOC)
					loc = loc.getCameFrom();
				}else{
					//loc = loc.came_from;
				}
			}
			//System.out.println("TTT "+old_loc);
			loc.setCameFrom(old_loc);
			//move to loc
		}
		//System.out.println("DONE");
		//System.out.println("ENDING AT "+most_promising.getLoc().getX()+" "+most_promising.getLoc().getY());
		//System.out.println("before that be  AT "+path_follow.peekFirst().getLoc().getX()+" "+path_follow.peekFirst().getLoc().getY());
		
		if (path_follow.size() != 0){
			path_follow.pollLast();
			//get rid of the first waypoint because its in the spot you are already in
		}
		
		while(path_follow.size() != 0){
			node_collection next = path_follow.pollLast();
			int next_x = (int) Math.floor((next.getLoc().getX()*Game.OBJ_WIDTH) + (Game.OBJ_WIDTH/2));
			int next_y = (int) Math.floor((next.getLoc().getY()*Game.OBJ_HEIGHT) + (Game.OBJ_HEIGHT/2));
			Point next_point = new Point(next_x, next_y);
						
			System.out.println("A POINT AT "+next_x+" "+next_y);
			subject.addWaypoint(next_point);
		}
		subject.addWaypoint(subject.getTarget());
		System.out.println("FINAL POINT AT "+subject.getTarget().x+" "+subject.getTarget().y);
		
		
	}

	public static boolean traceBack(ArrayDeque<node_collection> path_new, GameObject loc, GameObject start, int num_tracebacks){
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
	
	
	public static boolean astar(list_collection lists, GameObject start, GameObject goal, GameObject[][] map, int num_expansions){
		ArrayList<node_collection> closed_list = lists.closed_list;
		ArrayList<node_collection> open_list = lists.open_list;
		PriorityQueue<node_collection> open_list_priority = lists.open_list_priority;
		
		
		int expansions = 0;
		
		while (open_list.size() != 0 && expansions < num_expansions){
			expansions++;
			node_collection current = open_list_priority.peek();	
			if (current.getLoc().equals(goal)){
				System.out.println("FOUND GOAL leaving "+current.getLoc().getX()+" "+current.getLoc().getY()+" in open list g "+current.getG()+" "+current.getF()+" "+heuristic(current.getLoc(), goal));
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
			closed_list.add(current);
			for (node_collection neighbor : neighborNodes(current, map)){
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
						//System.out.println("NOW EXAMINING "+neighbor.location.x+" "+neighbor.location.y+" "+neighbor.g_val+" "+neighbor.f_val);
						if(!is_in_open){
							open_list.add(neighbor);
							open_list_priority.add(neighbor);
						}
					}
				}
			}
		}

		return false;
	}

	public static void listPath(node_collection current){
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

	public static ArrayList<node_collection> neighborNodes(node_collection focus, GameObject[][] map){
		int x = focus.getLoc().getX();
		int y = focus.getLoc().getY();

		ArrayList<node_collection> nodes = new ArrayList<node_collection>();
		for (int a = -1; a < 2; a++){
			for (int b = -1; b < 2; b++){
				if (a != 0 || b != 0){
					if (x+a >= 0 && x+a < map.length && y+b >= 0 && y+b < map[x+a].length){
						node_collection temp_node = new node_collection(map[x+a][y+b]);
						if (focus.canWalk(temp_node)){
							nodes.add(temp_node);
						}
					}
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

	public static boolean isInSet(node_collection check, ArrayList<node_collection> list){
		for (int i = 0; i < list.size(); i++){
			if (check.equals(list.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public static node_collection findInSet(node_collection check, ArrayList<node_collection> list){
		for (int i = 0; i < list.size(); i++){
			if (check.equals(list.get(i))){
				return list.get(i);
			}
		}
		return null;
	}

	public static int distBetween(node_collection col1, node_collection col2){
		if (col1.getLoc().getX() == col2.getLoc().getX() || col1.getLoc().getY() == col2.getLoc().getY()){
			return adj_dist;
		}
		return diag_dist;
	}

}
