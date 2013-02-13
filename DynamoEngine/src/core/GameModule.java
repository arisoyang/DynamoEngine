package core;

import java.awt.event.KeyEvent;

import inputters.KeyboardInput;
import inputters.MouseInput;

public class GameModule {

	public static PathModule pathfinding ;
	public static ObjectModule objects;
	public static AiModule unitai;
	private static KeyboardInput keyboard;
	private static MouseInput mouse;
	private static RenderModule renderer;
	
	public GameModule(){
		pathfinding = new PathModule();
		objects = new ObjectModule();
		unitai = new AiModule();
		keyboard=new KeyboardInput();
		mouse = new MouseInput();
		renderer = new RenderModule();
	}
	/**
	 * 
	 * @return false if the user wants to quit
	 */
	public boolean updateFromInput(){
		//apply the correct action for the right type of input
		keyboard.poll();
		mouse.poll();
		if(keyboard.keyDown(KeyEvent.VK_ESCAPE)){//checks to see if user wants to quit
			return true;
		}
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
		return false;
	}
	
	public boolean cycle(){
		pathfinding.updatePathfinding();
		unitai.updateUnitAI();
		objects.updateObjectStates();
		return true;
	}
	public boolean render(){
		renderer.update(1);//will be our data structure
		return true;
	}
	
}
