package Pathfinding;

import java.util.Hashtable;

import Objects.GameObject;
import Objects.MoveableObject;
import gameDemo.Game;

public class PathModule {

	public static Hashtable<GameObject, LRTA> lrtaHash = new Hashtable<GameObject, LRTA>();
	
	public PathModule(){
		
	}
	
	public void updatePathfinding(){
		//iterate through all units in the "pathfinding queue"
		//determine their new locations (ex next spot on path)
		//tell the units the spot to move to
		//System.out.println("CYCLING "+moveableObject.getList().size());
		
		for (MoveableObject o:MoveableObject.getList()){
			//System.out.println(o.getPos().x+" "+o.getPos().y+" "+o.getTarget().x+" "+o.getTarget().y);
			if (o.getPos().x != o.getTarget().x || o.getPos().y != o.getTarget().y){
				o.move();
			}
		}		
	}
	
	public static LRTA getLRTA(MoveableObject _subject, GameObject _goal, GameObject[][] _map){
		if (lrtaHash.containsKey(_goal)){
			System.out.println("EXISTING LRTA!!!!!!!!!!!!");
			return lrtaHash.get(_goal);
		}
		System.out.println("NEW LRTA!!!!!!!!!!!!!!!");
		LRTA newLRTA = new LRTA(_subject, _goal, _map);
		lrtaHash.put(_goal, newLRTA);
		return newLRTA;
	}
	
}
