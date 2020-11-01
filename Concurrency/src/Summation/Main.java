package Summation;

import java.util.Random;

class Summation extends Thread {

	private int[] arr;
	private int high, partial;

	public Summation(int[] arr, int high){
		this.arr = arr;
		this.high = Math.min(high, arr.length);
	}

	public int getPartialSum(){
		return partial;
	}

	public void run(){
		partial = sum(arr, high);
	}


	public static int sum(int[] arr, int high){ //sums up all values in array and returns total
		int total = 0;
		
		for (int i = 0; i < high; i++) {
			total += arr[i];
		}
		return total;
	}

	public static int parallelSum(int[] arr)

	{

		return parallelSum(arr, Runtime.getRuntime().availableProcessors());

	}


	public static int parallelSum(int[] arr, int threads){
		int size = (int) Math.ceil(arr.length * 1.0 / threads);
		Summation[] sums = new Summation[threads];

		for (int i = 0; i < threads; i++) {
			
			try {
				sums[i] = new Summation(arr, i * size);
				sums[i].start();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}try {

			for (Summation sum : sums) {

				sum.join();
			}

		}catch (InterruptedException e) {
		
		}

		int total = 0;

		for (Summation sum : sums) {
			total += sum.getPartialSum();
		}

		return total;
	}
}


public class Main {

	public static void main(String[] args){
		long start;
		Random rand = new Random();
		int[] arr = new int[200000000]; //Creating array with 200mill indexes

		for (int i = 0; i < arr.length; i++) { 
			arr[i] = rand.nextInt(10) + 1; //Fills array with random numbers between 1-10
		}
		
		//=================SINGLE THREAD=================//
		start = System.currentTimeMillis(); //Captures starting time in milliseconds
		System.out.println(Summation.sum(arr, arr.length));
		System.out.println("Single: " + (System.currentTimeMillis() - start));

		//=================PARALLEL THREAD=================//
		start = System.currentTimeMillis(); //Captures starting time in milliseconds
		System.out.println(Summation.parallelSum(arr, arr.length));
		System.out.println("Parallel: " + (System.currentTimeMillis() - start));
	}
}