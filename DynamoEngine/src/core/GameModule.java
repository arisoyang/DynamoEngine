package core;

import java.awt.event.KeyEvent;

import inputters.KeyboardInput;
import inputters.MouseInput;

public class GameModule {

	public static PathModule pathfinding ;
	public static ObjectModule objects;
	public static AiModule unitai;
	
	public GameModule(){
		pathfinding = new PathModule();
		objects = new ObjectModule();
		unitai = new AiModule();
	}
	
	public void updateFromInput(KeyboardInput keyboard, MouseInput mouse){
		//apply the correct action for the right type of input
		keyboard.poll();
		mouse.poll();
		
		for(int k:InputMap.getKeyboardHash().keySet()){
			if(keyboard.keyDown(k)){
				InputMap.getKeyboardHash().get(k).run();
			}
		}	
		for(int k:InputMap.getMouseHash().keySet()){
			if(keyboard.keyDown(k)){
				InputMap.getMouseHash().get(k).run();
			}
		}
		
	}
	
	public boolean cycle(){
		pathfinding.updatePathfinding();
		unitai.updateUnitAI();
		objects.updateObjectStates();
		return true;
	}
	
}
