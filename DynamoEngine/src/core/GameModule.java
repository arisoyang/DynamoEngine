package core;

public class GameModule {

	public static PathModule pathfinding ;
	public static ObjectModule objects;
	public static AiModule unitai;
	
	public GameModule(){
		pathfinding = new PathModule();
		objects = new ObjectModule();
		unitai = new AiModule();
	}
	
	public void updateFromInput(/*inputtype i*/){
		//apply the correct action for the right type of input
	}
	
	public boolean cycle(){
		pathfinding.updatePathfinding();
		unitai.updateUnitAI();
		objects.updateObjectStates();
		return true;
	}
	
}
