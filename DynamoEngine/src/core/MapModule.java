package core;

import Objects.GameObject;
import Tools.MapGenerator;

public class MapModule {

	
	private MapGenerator mg;
	
	public MapModule(){
		mg=new MapGenerator(10,10,4,20);
	}
	
	public GameObject[][] makeMap(int length,int width){
		GameObject[][] game_objects=new GameObject[length][width];
		for(int x = 0; x < game_objects.length; x++){
			for (int y = 0; y < game_objects[x].length; y++){
				game_objects[x][y] = new GameObject(x, y, 1, "file1.png");
				if (Math.random() < .1){
					game_objects[x][y] = new GameObject(x, y, 2, "file2.png");
				}else if (Math.random() < .1){
					game_objects[x][y] = new GameObject(x, y, 3, "file3.png");
				}
				
				//draw_objs.add(game_objects[x][y].getDrawObj());
			}
		}
		return game_objects;
	}
	
	public void save(){
		save(System.currentTimeMillis()+"Map.txt");
	}
	public void save(String filename){
		mg.export(filename);
	}
	
	
}
