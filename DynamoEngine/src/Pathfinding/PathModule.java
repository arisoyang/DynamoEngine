package Pathfinding;

import Objects.MoveableObject;
import gameDemo.Game;

public class PathModule {

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
				if (o.getWaypoints().size() == 0 ||(o.getWaypoints().peekLast().x != o.getTarget().x || o.getWaypoints().peekLast().y != o.getTarget().y)){
					
					//System.out.println("TARGET AT "+o.getTarget());
					
					tbastar.tbastar(o, Game.getBlock(o.getPos().x, o.getPos().y), Game.getBlock(o.getTarget().x, o.getTarget().y), Game.getMap());
				}
				
				
				//System.out.println("WERE MOVING");
				
				o.move();
			}
		}
		
		
	}
	
}
