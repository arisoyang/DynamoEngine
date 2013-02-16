package Tools;

import java.util.HashMap;

/**
 * 
 * @author Joe Muoio
 *
 */
public class MapGenerator {

	private int[][] heightMap;
	private int length, width, tilesPer;
	
	
	
	/**
	 * 
	 * @param length of the map
	 * @param width of the map
	 * @param tilesPer the number of tiles each map generator tile actually represents in game
	 */
	public MapGenerator(int length, int width, int tilesPer){
		this.length=length;
		this.width=width;
		this.tilesPer=tilesPer;
		heightMap=new int[length][width];
		for (int i=0;i<length;i++){
			for(int j=0;j<width;j++){
				heightMap[i][j]=0;
			}
		
		}
	}
	
	/**
	 * possibly have parameters to pick different algorithms or change params of algo
	 */
	private void generateHeightMap(){
		
	}
	private void evolutionary(){
		evolutionary(100,20,2);
	}
	private void evolutionary(int numIter){
		evolutionary(numIter,20,2);
	}
	private void evolutionary(int numIter, int popSize){
		evolutionary(numIter,popSize,2);
	}
	
	private void evolutionary(int numIter, int popSize, int numMut){
		
		
	}
	private void selection(){
		
	}
	private void crossover(){
		
	}
	private void mutation(){
		
	}
	private double fitness(){
		double fitness=0;
		
		return fitness;
	}
	/**
	 * Simulates the flow of water on the map
	 * @param waterAmount the amount of water starting on the map per square.
	 */
	private void simulateFlow(int waterAmount){
		
		//map of each coordinate to the amount of water
		HashMap<String,Integer> mapWithWater = new HashMap<String,Integer>(length*width);
		//maps a string of the coordinates to the water amount
		String coordinates;
		for(int i=0;i<length;i++){
			for(int j=0;j<width;j++){
				coordinates=i+","+j;
				mapWithWater.put(coordinates, waterAmount);
			}
		}
		
		
	}
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
