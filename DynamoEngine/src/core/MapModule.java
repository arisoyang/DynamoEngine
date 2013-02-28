package core;

import java.util.HashMap;

import Objects.GameObject;
import Tools.MapGenerator;

public class MapModule {

	
	private MapGenerator mg;
	private int length,width,layers;
	private final int tilesPer=2;
	public MapModule(int length,int width, int layers){//height and width to be divisible by tilesPer
		this.layers=layers;
		this.width=width;
		this.length=length;
		mg=new MapGenerator(length/tilesPer,width/tilesPer,layers,20);
	}
	
	public int[][] makeMap(){
		mg.evolutionary(200, 2);
		int[][]map=new int[length][width];
		int [][][] dataStruct=mg.bestSol();
		int[][] currentHeights=dataStruct[0];
		//int [][] ramps=dataStruct[1];
		//HashMap<int[],int[]> rampMapping=makeRampList(ramps);
		for(int i=0;i<currentHeights.length;i++){
			for(int j=0;j<currentHeights[i].length;j++){
				for(int t=0;t<tilesPer;t++){
					map[tilesPer*i+t][tilesPer*j]=currentHeights[i][j];
					for(int t2=0;t2<tilesPer;t2++){
						map[tilesPer*i+t][tilesPer*j+t2]=currentHeights[i][j];
					}
				}
				for(int t=0;t<tilesPer;t++){
					map[tilesPer*i][tilesPer*j+t]=currentHeights[i][j];
				}
				
			}
		}
		
		return map;
	}
/*	public HashMap<int[],int[]> makeRampList(int[][] ramps){

		HashMap<int[],int[]> rampMapping=new HashMap<int[],int[]>();
		int ones,tens,hundreds,thousands;
		for(int[] list:ramps){
			for(int i:list){
				ones=i%2;
				tens=i/10%2;
				hundreds=i/100%2;
				thousands=i/1000%2;
				
			}
		}
		
		return rampMapping;
	}*/
	public void save(){
		save(System.currentTimeMillis()+"Map.txt");
	}
	public void save(String filename){
		mg.export(filename);
	}
	
	
}
