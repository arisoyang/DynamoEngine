package Pathfinding;
import java.util.Comparator;



public class Location_compare implements Comparator<Node_collection>
{
    public int compare(Node_collection x, Node_collection y)
    {
    	if (x.getF() < y.getF()){
    		return -1;
    	}
    	if (x.getF() > y.getF()){
    		return 1;
    	}
        return 0;
    }

}