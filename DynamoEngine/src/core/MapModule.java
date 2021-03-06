package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Tools.MapGenerator;

public class MapModule {

	
	private MapGenerator mg;
	private int length,width,layers;
	private int tilesPer=2;
	private int numberOfIterations=1000;
	
	public MapModule(int length,int width, int layers){//height and width to be divisible by tilesPer
		this.layers=layers;
		this.width=width;
		this.length=length;
		mg=new MapGenerator(length/tilesPer,width/tilesPer,layers,20);
		mg.setAllFlags(false);
		mg.setMoveability(true);
		mg.setSymmetry(true,15);
		
	}
	public boolean setTilesPer(int tilesPer){
		if(length%tilesPer==0 && width%tilesPer==0){
			this.tilesPer=tilesPer;
			return true;
		}
		return false;
	}
	public void setAllFlags(boolean b){
		mg.setAllFlags(b);
	}
	public void setDifference(boolean b){
		mg.setDifference(b);
	}
	public void setDifference(boolean b, int scalar){
		mg.setDifference(b, scalar);
	}
	
	public void setMoveability(boolean b){
		mg.setMoveability(b);
	}
	public boolean setAvgHeight(boolean b, int height, int scalar){
		return mg.setAvgHeight(b, height,scalar);
	}
	public boolean setAvgHeight(boolean b, int height){
		return setAvgHeight(b,height,1);
	}
	public void setMoveability(boolean b,int scalar){
		mg.setMoveability(b, scalar);
	}
	public void setSymmetry(boolean b, int scalar){
		mg.setSymmetry(b, scalar);
	}
	public void setSymmetry(boolean b){
		mg.setSymmetry(b);
	}
	public void setNumberIterations(int number){
		numberOfIterations=number;
	}
	public int [][] makeMap(){
		return makeMap(System.currentTimeMillis()+"Map.txt");
	}
	public int[][] makeMap(String filename){
		mg.evolutionary(numberOfIterations, 2);
		int[][]map=new int[length][width];
		int[][] currentHeights=mg.bestSol();
		
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
		save(filename);
		return map;
	}

	public int[][]load(String file){
		int [][] map=new int[length][width];
	    try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int outer=0, inner=0;
			String line=br.readLine();
			while (line!=null){
				System.out.println(line);
				Scanner s=new Scanner(line);
				inner=0;
				while(s.hasNextInt()){
					map[outer][inner]=s.nextInt();
					inner++;
				}
				outer++;
				line=br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("***Error Opening file***");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("***Error Reading file***");
			System.exit(0);
		}
		
		return map;
	}
	public void save(){
		save(System.currentTimeMillis()+"Map.txt");
	}
	public void save(String filename){
		mg.export(filename);
	}
	
	
}
