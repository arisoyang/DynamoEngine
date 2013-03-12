package gameDemo;

import inputters.KeyInformation;
import inputters.KeyState;

import java.util.PriorityQueue;

import Objects.DrawObject;
import Objects.DrawObjectLayerCompare;
import Objects.GameObject;
import Objects.MoveableObject;
import core.GameModule;
import core.MapModule;

public class JoeGameDemo {

	/**
	 * @param args
	 */
	
	public static GameModule game;
	public static int[][] mapHeights;
	
	public static MoveableObject testunit;
	
	public static int OBJ_WIDTH = 25;
	public static int OBJ_HEIGHT = 25;
	
	private static GameObject[][] game_objects;
	private static MapModule mapMod;
	
	public static void main(String[] args) {
		genGame("1x1Map10000.txt");
		genGame("2x2GoodMap.txt");
		genGame("2x2Map10000.txt");
	}
	
	public static void genGame(String fileName){

		mapMod=new MapModule(40,40,7);
	
		mapMod.setTilesPer(1);
		mapHeights=mapMod.load(fileName);
		
		game = new GameModule();
		
		game.addMouseMap(new KeyInformation(0, KeyState.PRESSED), new moveChar());
		game.addMouseMap(new KeyInformation(2, KeyState.PRESSED), new contextMenu());
		
		PriorityQueue<DrawObject> draw_objs  = new PriorityQueue<DrawObject>(1, new DrawObjectLayerCompare());
		
		testunit = new MoveableObject(240, 240, 1, 10, "unit.png", 1);

		draw_objs.add(testunit.getDrawObj());
		
		game_objects = new GameObject[40][40];
		
					
//		game.addMouseMap(new KeyInformation(0, KeyState.PRESSED), new moveChar());
		
		
	
		draw_objs.add(testunit.getDrawObj());
		
		for(int x = 0; x < game_objects.length; x++){
			for (int y = 0; y < game_objects[x].length; y++){
				if (mapHeights[x][y]==0){
					game_objects[x][y] = new GameObject(x, y, mapHeights[x][y], "black.png");
				}else if (mapHeights[x][y]==1){
					game_objects[x][y] = new GameObject(x, y, mapHeights[x][y], "black40.png");
				}
				else if (mapHeights[x][y]==2){
					game_objects[x][y] = new GameObject(x, y, mapHeights[x][y], "black80.png");
				}
				else if (mapHeights[x][y]==3){
					game_objects[x][y] = new GameObject(x, y, mapHeights[x][y], "black120.png");
				}else if (mapHeights[x][y]==4){
					game_objects[x][y] = new GameObject(x, y, mapHeights[x][y], "black160.png");
				}
				else if (mapHeights[x][y]==5){
					game_objects[x][y] = new GameObject(x, y, mapHeights[x][y], "black200.png");
				}
				else if (mapHeights[x][y]==6){
					game_objects[x][y] = new GameObject(x, y, mapHeights[x][y], "black240.png");
				}
				draw_objs.add(game_objects[x][y].getDrawObj());
				game.objects.addObject(game_objects[x][y]);
			}
		}
		
		
		
		for(int x = 0; x < game_objects.length; x++){
			for (int y = 0; y < game_objects[x].length; y++){
				if (x > 0 && Math.abs(game_objects[x][y].getHeight() - game_objects[x-1][y].getHeight())>=2){
					draw_objs.add(new DrawObject(x*25, y*25, 10, "divider_ver.png"));	
				}
				if (x < game_objects.length-1 && Math.abs(game_objects[x][y].getHeight() - game_objects[x+1][y].getHeight())>=2){
					draw_objs.add(new DrawObject(x*25+24, y*25, 10, "divider_ver.png"));	
				}
				if (y > 0 && Math.abs(game_objects[x][y].getHeight() - game_objects[x][y-1].getHeight())>=2){
					draw_objs.add(new DrawObject(x*25, y*25, 10, "divider_hor.png"));	
				}
				if (y < game_objects[x].length-1 && Math.abs(game_objects[x][y].getHeight() - game_objects[x][y+1].getHeight())>=2){
					draw_objs.add(new DrawObject(x*25, y*25+24, 10, "divider_hor.png"));	
				}
			}
		}
		
		
		game.setDrawObjects(draw_objs);
		
		
		System.out.println("bef");
		//game.loop();
		game.render();
		System.out.println("aft");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static GameObject getBlock(int _x, int _y){
		return game_objects[(int) Math.floor(_x/OBJ_WIDTH)][(int) Math.floor(_y/OBJ_HEIGHT)];
	}
	
	public static GameObject[][] getMap(){
		return game_objects;
	}

}
