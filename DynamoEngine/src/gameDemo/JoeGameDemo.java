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
import core.RenderModule;
import core.Screen;

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
	private static int x=0,y=0;
	
	public static void main(String[] args) {
		genGame("avgHeight0.txt");
		genGame("avgHeight6Map.txt");
		genGame("AvgHeight0WithDifference.txt");
	}
	
	public static void genGame(String fileName){

		mapMod=new MapModule(40,40,7);
	
		mapMod.setTilesPer(1);
		mapHeights=mapMod.load(fileName);
		
		game = new GameModule(500,500,fileName,x,y);
		x=x+300;
		
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
				game_objects[x][y] = new GameObject(x, y, mapHeights[x][y], "tile"+mapHeights[x][y]+".png");
				
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
	//Screen sc=game.renderer.getScreen();
	//while(sc.isPaintingTile()){
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//}
		draw_objs.clear();
		game.setDrawObjects(draw_objs);
		
	}
	
	public static GameObject getBlock(int _x, int _y){
		return game_objects[(int) Math.floor(_x/OBJ_WIDTH)][(int) Math.floor(_y/OBJ_HEIGHT)];
	}
	
	public static GameObject[][] getMap(){
		return game_objects;
	}

}
