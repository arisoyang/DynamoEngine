package core;

import java.awt.event.KeyEvent;
import java.util.Date;

import inputters.KeyboardInput;
import inputters.MouseInput;

public class GameModule {

	public static PathModule pathfinding ;
	public static ObjectModule objects;
	public static AiModule unitai;
	private static KeyboardInput keyboard;
	private static MouseInput mouse;
	private static RenderModule renderer;
	private static int REDRAWING_PERIOD = 20; 
	private static int MAX_FRAME_SKIP = 10;
	/**
	 * Default constructor
	 */
	public GameModule(){
		pathfinding = new PathModule();
		objects = new ObjectModule();
		unitai = new AiModule();
		keyboard=new KeyboardInput();
		mouse = new MouseInput();
		renderer = new RenderModule();
		MAX_FRAME_SKIP=10;
		REDRAWING_PERIOD=20;
	}
	/**
	 * 
	 * @param reDrawPeriod the period that it redraws in
	 */
	public GameModule(int reDrawPeriod){
		pathfinding = new PathModule();
		objects = new ObjectModule();
		unitai = new AiModule();
		keyboard=new KeyboardInput();
		mouse = new MouseInput();
		renderer = new RenderModule();
		MAX_FRAME_SKIP=10;
		REDRAWING_PERIOD=reDrawPeriod;
	}
	/**
	 * 
	 * @param reDrawPeriod the period that it redraws in
	 * @param maxSkip the maximum frames skipped
	 */
	public GameModule(int reDrawPeriod, int maxSkip){
		pathfinding = new PathModule();
		objects = new ObjectModule();
		unitai = new AiModule();
		keyboard=new KeyboardInput();
		mouse = new MouseInput();
		renderer = new RenderModule();
		MAX_FRAME_SKIP=maxSkip;
		REDRAWING_PERIOD=reDrawPeriod;
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
	public void loop(){
		long time = new Date().getTime(),cur_time; //ms
		
		int frames;
		boolean need_to_redraw = true; 
		boolean quit = false;
		
		
		while (!quit) { 
			
			//System.pollForOSMessages();
			
			//updates mouse and keyboard input (by default)
			quit=updateFromInput();
		
			cur_time = new Date().getTime(); 
			frames = 0; 
			while (cur_time - time >= REDRAWING_PERIOD && frames<MAX_FRAME_SKIP){
				
				time+=REDRAWING_PERIOD; 
				/*
				keyboard->cycle(); 
				*/				
				if (!cycle()){
					quit = true;
				}
				cur_time = new Date().getTime(); 
				need_to_redraw=true; 
				frames++; 
			}  
			if (time < cur_time){
				time = cur_time; 
			}
			if (need_to_redraw) { 
				render();
				need_to_redraw=false; 
			} 
			
			//SDL_Delay(1); 
			try {
			    Thread.sleep(1);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
			
		}
	}
	
}
