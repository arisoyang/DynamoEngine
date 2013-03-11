package Tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
	private int length, width;
	private int[][][]heightMapArray;
	private static int MAXLAYERS;
	private static Random rand;
	private int avgHeightTarget=0;
	
	private boolean difference=false,moveability=true,symmetry=false, avgHeight=false;
	private int differenceScalar=1,moveabilityScalar=1,symmetryScalar=1,avgHeightScalar=1;
	//private int[][][] rampArray; //4 digit number of 1 and 0 to tell if there are any ramps
	//up right down left :||: left->right
	
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
		//rampArray=new int[populationSize][length][width];
		int p,i,j,k;
		for(p=0;p<populationSize;p++){//the starting population
			for (i=0;i<length;i++){
				for(j=0;j<width;j++){
					heightMapArray[p][i][j]=rand.nextInt(MAXLAYERS);
					/*for(k=0;k<4;k++){
						rampArray[p][i][j]+=Math.abs(rand.nextInt()%2)*Math.pow(10, k);
//						System.out.println(rampArray[p][i][j]);
					}*/
				}
			}
		}
		
		
	}
	public void setAllFlags(boolean b){
		difference=b;
		avgHeight=b;
		moveability=b;
		symmetry=b;
	}
	public void setDifference(boolean b){
		difference=b;
	}
	public void setDifference(boolean b, int scalar){
		difference=b;
		differenceScalar=scalar;
	}
	public void setMoveability(boolean b){
		moveability=b;
	}
	public void setMoveability(boolean b,int scalar){
		moveability=b;
		moveabilityScalar=scalar;
	}
	public void setSymmetry(boolean b, int scalar){
		symmetry=b;
		symmetryScalar=scalar;
	}
	public void setSymmetry(boolean b){
		symmetry=b;
	}
	public boolean setAvgHeight(boolean b, int height, int scalar){
		if(height<MAXLAYERS){
			avgHeightTarget=height;
			avgHeight=true;
			avgHeightScalar=scalar;
			return true;
		}
		return false;
	}
	public boolean setAvgHeight(boolean b, int height){
		return setAvgHeight(b,height,1);
	}
	public void evolutionary(){
		evolutionary(100,populationSize/10);
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
		int[][][] auxilaryRamp=new int[populationSize][length][width];
		int one,two;
		for(int i=0;i<populationSize;i+=2){
			one=lookupProbabilityDensity(rand.nextDouble());
			two=lookupProbabilityDensity(rand.nextDouble());
			cross(one,two,auxilary,auxilaryRamp,i,i+1);
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
	private void cross(int one,int two,int[][][]aux,int[][][]auxRamp,int auxOne,int auxTwo){
		int randomInt;
		int[] tempRow1, tempRow2,rampRow1, rampRow2;
		//loops through each row of the map
		for(int i=0;i<length;i++){
			tempRow1=new int[width];
			tempRow2=new int[width];
			rampRow1=new int[width];
			rampRow2=new int[width];
			//random integer is generated to see where split occurs
			randomInt=rand.nextInt(width);
			//this crosses the row
			for(int j=0;j<randomInt;j++){
				tempRow1[j]=heightMapArray[one][i][j];
				tempRow2[j]=heightMapArray[two][i][j];
//				rampRow1[j]=rampArray[one][i][j];
//				rampRow2[j]=rampArray[two][i][j];
			}
			for(int j=randomInt;j<width;j++){
				tempRow1[j]=heightMapArray[two][i][j];
				tempRow2[j]=heightMapArray[one][i][j];
				//rampRow1[j]=rampArray[two][i][j];
			//	rampRow2[j]=rampArray[one][i][j];
			}
			aux[auxOne][i]=tempRow1;
			aux[auxTwo][i]=tempRow2;
			//auxRamp[auxOne][i]=rampRow1;
			//auxRamp[auxTwo][i]=rampRow2;
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
	private double fitness(int[][] heightMap){//TODO:fix stuffs
		double fitness=0;
		double height=0;
		int lowest=MAXLAYERS;
		int highest=0;
		int numberNextToSame=0;
		int currHeight;
		int[] adjList;
		if(difference || avgHeight ||moveability){
			for(int i=0;i<heightMap.length;i++){
				for(int j=0;j<heightMap[i].length;j++){
					currHeight=heightMap[i][j];
					height+=currHeight;
					if(moveability){
						adjList=getAdjacentHeights(heightMap,i,j);
						for(int k:adjList){
							if (currHeight>=k-1&&currHeight<=k+1){
								numberNextToSame++;
							}
						}
					}
					if(lowest<currHeight){
						lowest=currHeight;
					}
					else if(highest>currHeight){
						highest=currHeight;
					}
				}
			}
		}
		if(symmetry)
			fitness+=symmetryScalar*findSymmetryPercent(heightMap);
		double average=(height/(double)(length*width));
		//difference between highest point and lowest
		if(difference)
			fitness+=differenceScalar*(highest-lowest);
		//the difference between the average of the map and the true average
		if(avgHeight)
			fitness-=avgHeightScalar*Math.abs(((double) avgHeightTarget)-average);
		//all the squares that have their same height boardering
		if(moveability)
			fitness+=moveabilityScalar*numberNextToSame;
		return fitness;
	}	
	
	private double findSymmetryPercent(int[][] heightMap) {
		double total=0,same=0;
		for(int i=0;i<length;i++){
			for(int j=0;j<width/2;j++){
				if(heightMap[i][j]==heightMap[i][width-1-j]){
					same++;
				}
				total++;
			}
		}
		/*for(int i=0;i<width;i++){
			for(int j=0;j<length/2;j++){
				if(heightMap[j][i]==heightMap[length-1-j][i]){
					same++;
				}
				total++;
			}
		}*/
		
		return same/total;
	}
	public String export(){
		String toReturn="";
		for(int i=0;i<heightMapArray[0].length;i++){
			for(int j=0;j<heightMapArray[0][i].length;j++){
				toReturn+=heightMapArray[0][i][j]+" ";
			}
			toReturn+="\n";
		}
		toReturn+="*******************";
		/*for(int i=0;i<rampArray[0].length;i++){
			for(int j=0;j<rampArray[0][i].length;j++){
				toReturn+=rampArray[0][i][j]+" ";
			}
			toReturn+="\n";
		}
		*/
		return toReturn;
	}
	public void export(String filename){
		try {
			BufferedWriter outputStream = new BufferedWriter(new FileWriter(filename));
			outputStream.write(export());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int[] getAdjacentHeights(int[][] heightMap,int i, int j) {
		int[] adjList;
		int size=4;
		if(j==width-1||j==0){
			size--;
		}
		if(i==length-1||i==0){
			size--;
		}
		adjList=new int[size];
	
		if(j==width-1){
			adjList[0]=heightMap[i][j-1];
			if(i==length-1){//corner
				adjList[1]=heightMap[i-1][j];
				
			}
			else if(i==0){//other corner
				adjList[1]=heightMap[i+1][j];
				
			}
			else{//not corner
				adjList[1]=heightMap[i-1][j];
				adjList[2]=heightMap[i+1][j];
			}
		}
		else if (j==0){
			adjList[0]=heightMap[i][j+1];
			if(i==length-1){//corner
				adjList[1]=heightMap[i-1][j];
				
			}
			else if(i==0){//other corner
				adjList[1]=heightMap[i+1][j];
				
			}
			else{//not corner
				adjList[1]=heightMap[i-1][j];
				adjList[2]=heightMap[i+1][j];
			}
		}
		else{
			adjList[0]=heightMap[i][j-1];
			adjList[1]=heightMap[i][j+1];
			if(i==length-1){//corner
				adjList[2]=heightMap[i-1][j];
				
			}
			else if(i==0){//other corner
				adjList[2]=heightMap[i+1][j];
				
			}
			else{//not corner
				adjList[2]=heightMap[i-1][j];
				adjList[3]=heightMap[i+1][j];
			}
		}
	
		
		return adjList;
	}
	
	private void sort(double[] array){
		String arr="";
		for (double d:array){
			arr+=" "+d;
		}
//		System.out.println("before: "+arr);
		for(int i=0;i<array.length;i++){
			for(int j=array.length-1;j>i;j--){
				if(array[i]<array[j])
					swap(array,i,j);
			}
		}
		/*arr="";
		for (double d:array){
			arr+=" "+d;
		}
		System.out.println("after: "+arr);*/
	}
	private void swap(double[] array, int i,int j){
		double temp=array[i];
		array[i]=array[j];
		array[j]=temp;
		int [][]tempMap=heightMapArray[i];
		heightMapArray[i]=heightMapArray[j];
		heightMapArray[j]=tempMap;
//		tempMap=rampArray[i];
//		rampArray[i]=rampArray[j];
//		rampArray[j]=tempMap;
	}
	public int[][] bestSol(){
		selection();
		int[][] ret=heightMapArray[0];
//		ret[1]=rampArray[0];
		return ret;
	}
	//THese below methods are for testing help
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
	/*	member=rampArray[number];
		for(int i=0;i<member.length;i++){
			for(int j=0;j<member[i].length;j++){
				toReturn+=member[i][j]+" ";
			}
			toReturn+="\n";
		}*/
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
		MapGenerator mg= new MapGenerator(7,10,10,20);
		mg.evolutionary(100000,2);
		mg.setAllFlags(false);
//		mg.setSymmetry(true,10);
//		mg.setMoveability(true, 2);
		mg.setAvgHeight(true, 0,100);
//		System.out.println(mg.findSymmetryPercent(mg.bestSol()));
//		System.out.println(mg.fitness(mg.bestSol()));
		System.out.println(mg.fitness(mg.bestSol()));
		System.out.println(mg.stringBestSol());
//		for(int i:mg.getAdjacentHeights(mg.bestSol(), 5, 8))
//			System.out.print(" "+i);
//		System.out.println(mg.getPreviousBestSol());
	}

}
