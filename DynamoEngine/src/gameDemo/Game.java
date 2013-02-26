package gameDemo;

import inputters.KeyInformation;
import inputters.KeyState;
import inputters.KeyboardInput;
import inputters.MouseInput;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;

import Objects.DrawObject;
import Objects.DrawObjectLayerCompare;
import Objects.GameObject;
import Objects.MoveableObject;

import core.GameAction;
import core.GameModule;

public class Game {

	/**
	 * @param args
	 */
	
	public static GameModule game;
	
	public static MoveableObject testunit;
	
	public static int OBJ_WIDTH = 25;
	public static int OBJ_HEIGHT = 25;
	
	private static GameObject[][] game_objects;
	
	public static void main(String[] args) {
				
		System.out.println(new Rectangle(25, 25, 25, 25).intersects(new Rectangle(49, 25, 1, 25)));
		System.out.println(new Rectangle(24, 24, 26, 26).intersects(new Rectangle(50, 49, 2, 1)));
		
		
		game = new GameModule();
		
		game.addMouseMap(new KeyInformation(0, KeyState.PRESSED), new moveChar());
		
		PriorityQueue<DrawObject> draw_objs  = new PriorityQueue<DrawObject>(1, new DrawObjectLayerCompare());
		
		testunit = new MoveableObject(35, 35, 3, "unit.png", 1);

		draw_objs.add(testunit.getDrawObj());
		
		game_objects = new GameObject[20][20];
		
		/*for(int x = 0; x < game_objects.length; x++){
			for (int y = 0; y < game_objects[x].length; y++){
				game_objects[x][y] = new gameObject(x, y, 1, "file1.png");
				if (Math.random() < .1){
					game_objects[x][y] = new gameObject(x, y, 2, "file2.png");
				}else if (Math.random() < .1){
					game_objects[x][y] = new gameObject(x, y, 3, "file3.png");
				}
				
				draw_objs.add(game_objects[x][y].getDrawObj());
			}
		}*/
		
		
		
		for(int x = 0; x < game_objects.length; x++){
			for (int y = 0; y < game_objects[x].length; y++){
				if (x > 0 && game_objects[x][y].getHeight() != game_objects[x-1][y].getHeight()){
					draw_objs.add(new DrawObject(x*25, y*25, 10, "divider_ver.png"));	
				}
				if (x < game_objects.length-1 && game_objects[x][y].getHeight() != game_objects[x+1][y].getHeight()){
					draw_objs.add(new DrawObject(x*25+24, y*25, 10, "divider_ver.png"));	
				}
				if (y > 0 && game_objects[x][y].getHeight() != game_objects[x][y-1].getHeight()){
					draw_objs.add(new DrawObject(x*25, y*25, 10, "divider_hor.png"));	
				}
				if (y < game_objects[x].length-1 && game_objects[x][y].getHeight() != game_objects[x][y+1].getHeight()){
					draw_objs.add(new DrawObject(x*25, y*25+24, 10, "divider_hor.png"));	
				}
			}
		}
		
		
		game.setDrawObjects(draw_objs);
		
		
		System.out.println("bef");
		game.loop();
		System.out.println("aft");
		
		
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		//IS IT A PROBLEM THAT AFTER GAME.LOOP IS CALLED, NOTHING ELSEAFTER IT WILL WORK???
		
	}
	
	public static GameObject getBlock(int _x, int _y){
		return game_objects[(int) Math.floor(_x/OBJ_WIDTH)][(int) Math.floor(_y/OBJ_HEIGHT)];
	}
	
	public static GameObject[][] getMap(){
		return game_objects;
	}

}
