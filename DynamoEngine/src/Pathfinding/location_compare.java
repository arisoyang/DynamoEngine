package Pathfinding;
import java.util.Comparator;



public class location_compare implements Comparator<node_collection>
{
    public int compare(node_collection x, node_collection y)
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