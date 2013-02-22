package Tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * 
 * @author Joe Muoio
 *
 */
public class MapGenerator {

	//private int[][] heightMap;
	private int length, width, tilesPer;
	private int[][][]heightMapArray;
	private static int MAXLAYERS;
	private static Random rand;
	
	private int[][] previousBestSolution;
	
	private double[] probabilityDensity;
	
	private int numIterations,populationSize,numMutations;
	
	/**
	 * 
	 * @param length of the map
	 * @param width of the map
	 * @param tilesPer the number of tiles each map generator tile actually represents in game
	 */
	public MapGenerator(int length, int width,int maxLayers, int populationSize){
		rand=new Random();
		this.length=length;
		this.width=width;
		this.populationSize=populationSize;
		MAXLAYERS=maxLayers;
		heightMapArray=new int[populationSize][length][width];
		for(int p=0;p<populationSize;p++){
			for (int i=0;i<length;i++){
				for(int j=0;j<width;j++){
					heightMapArray[p][i][j]=rand.nextInt(MAXLAYERS);
				}
			}
		}
		
		
	}
	
	public void evolutionary(){
		evolutionary(100,2);
	}
	public void evolutionary(int numIter){
		evolutionary(numIter,2);
	}
	
	public void evolutionary(int numIter,int numMut){
		numMutations=numMut;
		numIterations=numIter;
		//populationSize=popSize;
		evoLoop();
	}
	private void evoLoop(){
		for(int i=0;i<numIterations;i++){
			selection();
			mutation();
			crossover();

			previousBestSolution=heightMapArray[0];
		}
	}
	private void selection(){
		double[] fitvals=new double[populationSize];
		double totalFit=0;
		probabilityDensity=new double[populationSize];
		for (int i=0;i<populationSize;i++){
			fitvals[i]=fitness(heightMapArray[i]);
			totalFit+=fitvals[i];
		}
		//System.out.println("before: "+fitvals[0]);
		sort(fitvals);
		probabilityDensity[0]=fitvals[0]/totalFit;
		for(int i=1;i<populationSize;i++){
			probabilityDensity[i]=fitvals[i]/totalFit+probabilityDensity[i-1];
		}
		//System.out.println("after: "+fitvals[0]);
	}
	private void crossover(){
		//this picks the pop members to cross
		//replaces the bottom half
		int[][][] auxilary=new int[populationSize][length][width];
		int one,two;
		for(int i=0;i<populationSize;i+=2){
			one=lookupProbabilityDensity(rand.nextDouble());
			two=lookupProbabilityDensity(rand.nextDouble());
			cross(one,two,auxilary,i,i+1);
		}
		heightMapArray=auxilary;
	}
	private int lookupProbabilityDensity(double val) {
		int index=0;
		while(index<probabilityDensity.length-1&&val<probabilityDensity[index]){
			index++;
		}
		return index;
	}

	/**
	 * Crosses population members of indicies one and two in the map array
	 * @param one index one to be crossed
	 * @param two index two to be crossed
	 */
	private void cross(int one,int two,int[][][]aux,int auxOne,int auxTwo){
		int randomInt;
		
		int[] tempRow1;
		int[] tempRow2;
		//loops through each row of the map
		for(int i=0;i<length;i++){
			tempRow1=new int[width];
			tempRow2=new int[width];
			//random integer is generated to see where split occurs
			randomInt=rand.nextInt(width);
			//this crosses the row
			for(int j=0;j<randomInt;j++){
				tempRow1[j]=heightMapArray[one][i][j];
				tempRow2[j]=heightMapArray[two][i][j];
			}
			for(int j=randomInt;j<width;j++){
				tempRow1[j]=heightMapArray[two][i][j];
				tempRow2[j]=heightMapArray[one][i][j];
			}
			aux[auxOne][i]=tempRow1;
			aux[auxTwo][i]=tempRow2;
		}
		
	}
	
	private void mutation(){
		for(int i=0;i<numMutations;i++){
			mutate(heightMapArray[populationSize-i-1]);
		}
	}
	private void mutate(int[][] heightMap){
		int randInt;
		for(int i=0;i<length;i++){
			randInt=rand.nextInt(width);
			heightMap[i][randInt]=rand.nextInt(MAXLAYERS);
			randInt=rand.nextInt(width);
			heightMap[i][randInt]=rand.nextInt(MAXLAYERS);
			randInt=rand.nextInt(width);
			heightMap[i][randInt]=rand.nextInt(MAXLAYERS);
			randInt=rand.nextInt(width);
			heightMap[i][randInt]=rand.nextInt(MAXLAYERS);
		}
	}
	private double fitness(int[][] heightMap){
		double fitness=0;
		
		for(int i=0;i<heightMap.length;i++){
			for(int j=0;j<heightMap[i].length;j++){
				fitness+=heightMap[i][j];
			}
		}
		
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
	
	private void sort(double[] array){
		String arr="";
		for (double d:array){
			arr+=" "+d;
		}
		System.out.println("before: "+arr);
		for(int i=0;i<array.length;i++){
			for(int j=array.length-1;j>i;j--){
				if(array[i]<array[j])
					swap(array,i,j);
			}
		}
		arr="";
		for (double d:array){
			arr+=" "+d;
		}
		System.out.println("after: "+arr);
	}
	private void swap(double[] array, int i,int j){
		double temp=array[i];
		array[i]=array[j];
		array[j]=temp;
		int [][]tempMap=heightMapArray[i];
		heightMapArray[i]=heightMapArray[j];
		heightMapArray[j]=tempMap;
	}
	public int[][] bestSol(){
		selection();
		return heightMapArray[0];
	}
	public String stringBestSol(){
		return stringPopMember(0);
	}
	public String stringPopMember(int number){
		int[][] member=heightMapArray[number];
		String toReturn="";
		for(int i=0;i<member.length;i++){
			for(int j=0;j<member[i].length;j++){
				toReturn+=member[i][j]+" ";
			}
			toReturn+="\n";
		}
		return toReturn;
	}
	public String getPreviousBestSol( ){
		String toReturn="";
		for(int i=0;i<previousBestSolution.length;i++){
			for(int j=0;j<previousBestSolution[i].length;j++){
				toReturn+=previousBestSolution[i][j]+" ";
			}
			toReturn+="\n";
		}
		return toReturn;
	}
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MapGenerator mg= new MapGenerator(7,10,10,20);
		mg.evolutionary(10,5);
		System.out.println(mg.fitness(mg.bestSol()));
		System.out.println(mg.stringBestSol());
//		System.out.println(mg.getPreviousBestSol());
	}

}
