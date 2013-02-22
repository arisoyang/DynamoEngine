package Pathfinding;
import java.util.ArrayList;
import java.util.PriorityQueue;



public class list_collection {

	public ArrayList<node_collection> closed_list;
	public ArrayList<node_collection> open_list;
	public PriorityQueue<node_collection> open_list_priority;

	public list_collection(){
		closed_list = new ArrayList<node_collection>();
		open_list = new ArrayList<node_collection>();
		open_list_priority = new PriorityQueue<node_collection>(1, new location_compare());
	}
	
}
