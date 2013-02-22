package core;

import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

import javax.swing.JFrame;

import Objects.drawObject;
import Pathfinding.PathModule;


import inputters.KeyInformation;
import inputters.KeyboardInput;
import inputters.MouseInput;

public class GameModule{

	public static PathModule pathfinding ;
	public static ObjectModule objects;
	public static AiModule unitai;
	public static KeyboardInput keyboard;
	public static MouseInput mouse;
	public static RenderModule renderer;
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

		addListeners();
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

		addListeners();
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
		
		addListeners();
	}
	
	public void addListeners(){
		renderer.addMouseListener(mouse);
		renderer.addMouseMotionListener(mouse);		
	}
	
	
	/**
	 * 
	 * @return false if the user wants to quit
	 */
	
	public void addMouseMap(int _button, GameAction _ga){
		InputMap.addMouseButtonMap(_button, _ga);
	}
	public void addMouseMap(KeyInformation key, GameAction _ga){
		InputMap.addMouseButtonMap(key, _ga);
	}
	
	public boolean updateFromInput(){
		//apply the correct action for the right type of input
		keyboard.poll();
		mouse.poll();
		if(keyboard.keyDown(KeyEvent.VK_ESCAPE)){//checks to see if user wants to quit
			return true;
		}
		for(int k:InputMap.getKeyboardHash().keySet()){
			if(keyboard.keyChanged(k)){
				InputMap.getKeyboardHash().get(k).run();
			}
		}	

		for(int k:InputMap.getMouseHash().keySet()){
			if(mouse.buttonChanged(k)){
				KeyInformation key = new KeyInformation(k, mouse.getButtonState(k));
				if(InputMap.getMouseStateHash().containsKey(key)){
					InputMap.getMouseStateHash().get(key).run();
				}
			}
		}
		if (mouse.mouseMoved()){
			//do something here about mouse movement
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
		long time = new Date().getTime();
		long cur_time; //ms
		
		int frames;
		boolean need_to_redraw = true; 
		boolean quit = false;
		
		
		while (!quit) { 
			//System.out.println("KO");
			//System.pollForOSMessages();
			
			//updates mouse and keyboard input (by default)
			quit=updateFromInput();
			cur_time = new Date().getTime(); 
			frames = 0; 
			//System.out.println((cur_time - time)+" "+(cur_time - time >= REDRAWING_PERIOD)+" "+(frames<MAX_FRAME_SKIP));
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
			//if (time < cur_time){
			if (frames >= MAX_FRAME_SKIP){	
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
	
	public void setDrawObjects(PriorityQueue<drawObject> objects){
		renderer.setDrawObjects(objects);
	}
	
}
