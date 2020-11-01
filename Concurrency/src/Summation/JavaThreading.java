package Summation;

import static org.junit.Assert.assertEquals;

import java.text.NumberFormat;
import java.util.Random;

public class JavaThreading {

	public static void main(String[] args){
		long threadStart;
		Random random = new Random(); //Creates random object
		NumberFormat commaFormat = NumberFormat.getInstance();
		
		int[] randomArray = new int[200000000]; //Creates 20mil index array

		for (int i = 0; i < randomArray.length; i++) {
			randomArray[i] = random.nextInt(10) + 1; //Fills array with random numbers
		}
		//=================SINGLE THREAD=================//
		
		threadStart = System.currentTimeMillis(); //Captures starting time in milliseconds
		System.out.println("Single Thread Sum: " + commaFormat.format(IndexSum.threadSum(randomArray, 0, randomArray.length)));
		System.out.println("Single Thread Run Time: " + (System.currentTimeMillis() - threadStart + " ms"));

		for(int n = 0; n < 40; n++){//Line
		    System.out.print("-");
		}
		 System.out.println();
		 
		//=================PARALLEL THREAD=================//

		threadStart = System.currentTimeMillis();
		System.out.println("Parallel Thread Sum: " + commaFormat.format(IndexSum.parallelSum(randomArray, Runtime.getRuntime().availableProcessors())));
		System.out.println("Parallel Thread Run Time: " + (System.currentTimeMillis() - threadStart) + " ms");
	}

}

class IndexSum extends Thread {
	private int[] array;
	private int lowerArray, upperArray;
	static int threadTotal;
	public static int total;

	//=================CONSTRUCTOR=================//
	public IndexSum(int[] array, int lowerArray, int upperArray){
		this.array = array;
		this.lowerArray = lowerArray;
		this.upperArray = Math.min(upperArray, array.length);
	}

	public int threadSum(){ //Used to return thread total since run cannot return a type
		return threadTotal; 
	}

	public void run(){
		threadTotal = threadSum(array, lowerArray, upperArray); //calls sum method with constructor fields passed by object
	}

	public static int threadSum(int[] array, int arrayLowNum, int arrayHighNum){//Adds all values in respective array and returns total
		int total = 0;
		for (int i = arrayLowNum; i < arrayHighNum; i++) {
			total += array[i];
		}
		return total;
	}


	public static int parallelSum(int[] arr, int numberOfThreads){
		int size = (int) Math.ceil(arr.length / numberOfThreads); //diving array among 8 threads (25mil in each)
		IndexSum[] thread = new IndexSum[numberOfThreads];
		
		for (int i = 0; i < numberOfThreads; i++) { //Loop fills each thread in array with 25mil random numbers
			thread[i] = new IndexSum(arr, i * size, (i + 1) * size); //calling constructor and creating thread object
			thread[i].start();
		}

		for (int n = 0; n < thread.length; n++) { //Ensures that all thread totals are stacked
			try {
					thread[n].join();
				} 
			catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

		int total = 0; //Adds full amount of partial thread
		for (int n = 0; n < thread.length; n++) {
			total += thread[n].threadSum();
		}
		
		return total; 
		
	}
}

