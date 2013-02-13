package gameDemo;

import inputters.KeyboardInput;
import inputters.MouseInput;

import java.awt.event.KeyEvent;
import java.util.Date;

import core.GameModule;

public class Game {

	/**
	 * @param args
	 */
	
	public static GameModule game = new GameModule();
	
	
	public static void main(String[] args) {
		
		long time = new Date().getTime(); //ms
		int REDRAWING_PERIOD = 20; 
		int MAX_FRAME_SKIP = 10; 
		boolean need_to_redraw = true; 
		boolean quit = false;
		
		
		while (!quit) { 
			
			//System.pollForOSMessages();
			
			//need to tie this in with inputMap in some way still
			
			/*
			while ( SDL_PollEvent( &event ) ) { 
			    switch ( event.type ) { 
			        case SDL_KEYDOWN: 
			            if (event.key.keysym.sym == SDLK_F12) { 
			                quit = true; 
			            } 
			            break; 
			       case SDL_MOUSEBUTTONDOWN: 
			game->MouseClick(event.button.x,event.button.y); 
			break; 
			        case SDL_QUIT: 
			            quit = true; 
			            break; 
			    } 
			} 	
			 */
			//updates mouse and keyboard input (by default)
			
			
			quit=game.updateFromInput();
		
			long cur_time = new Date().getTime(); 
			int frames = 0; 
			while (cur_time - time >= REDRAWING_PERIOD && frames<MAX_FRAME_SKIP){
				
				time+=REDRAWING_PERIOD; 
				/*
				keyboard->cycle(); 
				*/				
				if (!game.cycle()){
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
				//game->draw();
				game.render();
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
