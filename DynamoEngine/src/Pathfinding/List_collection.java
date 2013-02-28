package Pathfinding;
import java.util.ArrayList;
import java.util.PriorityQueue;



public class List_collection {

	public ArrayList<Node_collection> closed_list;
	public ArrayList<Node_collection> open_list;
	public PriorityQueue<Node_collection> open_list_priority;

	public List_collection(){
		closed_list = new ArrayList<Node_collection>();
		open_list = new ArrayList<Node_collection>();
		open_list_priority = new PriorityQueue<Node_collection>(1, new Location_compare());
	}
	
}
