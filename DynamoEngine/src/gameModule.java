
public class gameModule {

	public static pathModule pathfinding ;
	public static objectModule objects;
	public static aiModule unitai;
	
	public gameModule(){
		pathfinding = new pathModule();
		objects = new objectModule();
		unitai = new aiModule();
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
