package threadTest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ThreadandRunnable {

	 private static final int THREAD_COUNT = 12; 
	    private static final int NUMBER_COUNT = 200000;
	    private static final int PER_COUNT = NUMBER_COUNT / THREAD_COUNT;
	    private static final int RANDOM_LIMIT = 10000000;


	    public static void main(String[] args) throws InterruptedException {

	        
	        Random random = new Random();
	        int[] array = new int[NUMBER_COUNT];
	        for (int i = 0; i < NUMBER_COUNT; i++) {
	            array[i] = random.nextInt(RANDOM_LIMIT);
	        }

	        List<Thread> threadList = new LinkedList<>();
	        for (int index = 0; index < THREAD_COUNT; index++) {
	            Thread t = new Thread(new SortRunnable(array, index * PER_COUNT, (index + 1) * PER_COUNT));
	            t.start();
	            threadList.add(t);
	        }

	       
	        join(threadList);

	        
	        int[] result = merge(array, PER_COUNT, THREAD_COUNT);
	        if (check(result)) {
	        	for(int i = 0;i < result.length;i++){
	        		System.out.println(result[i]);
	        	}
	            System.out.println("merge done");
	        }
	    }

	    private static boolean check(int[] array) {
	        for (int i = 0; i < array.length - 1; i++) {
	            if (array[i] > array[i + 1]) {
	                System.out.println("error");
	                return false;
	            }
	        }
	        return true;
	    }

	    private static void join(List<Thread> threads) throws InterruptedException {
	        for (Thread thread : threads) {
	            thread.join();
	        }
	    }

	  
	    private static int[] merge(int[] array, int size, int count) {

	        
	        int[] indexes = new int[count];
	        for (int i = 0; i < count; i++) {
	            indexes[i] = i * size;
	        }

	        int[] result = new int[array.length];
	        
	        for (int i = 0; i < result.length; i++) {

	            int minNumber = Integer.MAX_VALUE;
	            int minIndex = 0;
	            
	            for (int index = 0; index < indexes.length; index++) {
	               
	                if ((indexes[index] < (index + 1) * size) && (array[indexes[index]] < minNumber)) {
	                    minNumber = array[indexes[index]];
	                    minIndex = index;
	                }
	            }

	            result[i] = minNumber;
	            indexes[minIndex]++;
	        }

	        return result;
	    }

	    private static class SortRunnable implements Runnable {

	        private int[] array;
	        private int start;
	        private int end;

	        public SortRunnable(int[] array, int start, int end) {
	            this.array = array;
	            this.start = start;
	            this.end = end;
	        }

	        @Override
	        public void run() {
	           
	            Arrays.sort(array, start, end);
	        }
	    }

}
