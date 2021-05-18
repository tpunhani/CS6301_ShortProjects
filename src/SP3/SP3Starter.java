/**
 * Sample starter code for SP3.
 *
 * @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 * Merge Sort
 * Ver 1.0: 2021/02/26
 * shuffling the array after every trail takes lot of time
 * create worst case input and copy to input array after every trail
 * not the best option. but saves time
 * if space is an issue, use csgrads1.utdallas.edu server
 */

package SP3;

import java.util.Random;

public class SP3Starter {
    public static Random random = new Random();
    public static int numTrials = 50;
    public static int[] wcInput; //inp array
    public static int threshholdForInsertionSort = 32;

    public static void main(String[] args) {
        int n = 10007;
//	int choice = 1 + random.nextInt(4);
        int choice = 6;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            choice = Integer.parseInt(args[1]);
        }
        int[] arr = new int[n]; // initally inp. finally sorted
        wcInput = new int[n];
        wcInitArray(wcInput, 0, wcInput.length);
        Timer timer = new Timer();
        switch (choice) {
            case 0:
                for (int i = 0; i < numTrials; i++) {
                    System.out.println("Trial No: "+i+" started.");
                    initArray(arr);
                    mergeSort0(arr);
                }
                break;
            case 3:
                for (int i = 0; i < numTrials; i++) {
                    System.out.println("Trial No: "+i+" started.");
                    initArray(arr);
                    mergeSort3(arr);
                }
                break;  // etc

            case 4:
                for (int i = 0; i < numTrials; i++) {
                    System.out.println("Trial No: "+i+" started.");
                    initArray(arr);
                    mergeSort4(arr);
                }
                break;


            case 6:
                for (int i = 0; i < numTrials; i++) {
                    System.out.println("Trial No: "+i+" started.");
                    initArray(arr);
                    mergeSort6(arr);
                }
                break;
        }
        timer.end();
        timer.scale(numTrials);

        System.out.println("Choice: " + choice + "\n" + timer);

        //commented code to print the final array
//        Integer[] inpArray = Arrays.stream(arr).boxed().toArray(Integer[]::new);
//        Shuffle.printArray(inpArray, "Print the array");
    }

    public static void insertionSort(int[] arr, int p, int r) {
        for (int i = p + 1; i < r+1; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= p && key < arr[j]) {
                arr[j + 1] = arr[j];
                j -= 1;
            }
            arr[j + 1] = key;
        }
    }

    public static void mergeSort0(int[] arr) {
        mergeSort0(arr, 0, arr.length - 1);
    }

    public static void mergeSort3(int[] arr) {
        //create new array B
        int[] arrB = new int[arr.length];
        System.arraycopy(arr, 0, arrB, 0, arr.length);
        mergeSort3(arr, arrB, 0, arr.length - 1);
    }

    public static void mergeSort4(int[] arr) {
        //create new array B
        int[] arrB = new int[arr.length];
        System.arraycopy(arr, 0, arrB, 0, arr.length);
        mergeSort4(arr, arrB, 0, arr.length - 1);
    }

    public static void mergeSort6(int[] arr){
        int n = arr.length;
        //create new array B
        int[] arrB = new int[n];

        int[] inpArray = arr;
        int threshHoldValue=1;

        if(n > threshholdForInsertionSort-1) {
            for (int k = 0; k < n; k = k + threshholdForInsertionSort) {
                //Handling power of 2 case
                if(k + threshholdForInsertionSort-1 > n){
                    insertionSort(arr, k, n-1);
                }else{
                    insertionSort(arr, k, k + threshholdForInsertionSort - 1);
                }

            }
            threshHoldValue = threshholdForInsertionSort;
        }

        for(int i=threshHoldValue; i<n; i=2*i){
            for(int j=0; j<n; j=j+2*i){
                int p = j;
                int q = j+i-1;
                int r = j+2*i-1;
                //Handling power of 2 case
                if(r < n){
                    merge3(arrB, inpArray, p, q, r);
                }else{
                    merge3(arrB, inpArray, p, q, n-1);
                }

            }
            int[] temp = inpArray;
            inpArray = arrB;
            arrB = temp;
        }
        if (arr != inpArray){
            System.arraycopy(inpArray, 0, arr, 0, inpArray.length);
        }

    }


    public static void mergeSort0(int[] arr, int p, int r) {
        if (p < r) {
            int q = p + (r - p) / 2;
            mergeSort0(arr, p, q);
            mergeSort0(arr, q + 1, r);
            merge0(arr, p, q, r);
        }
    }

    public static void mergeSort3(int[] arr, int[] arrB, int p, int r) {
        if (p < r) {
            int q = p + (r - p) / 2;
            mergeSort3(arrB, arr, p, q);
            mergeSort3(arrB, arr, q + 1, r);
            merge3(arr, arrB, p, q, r);
        }
    }

    public static void mergeSort4(int[] arr, int[] arrB, int p, int r) {
        if (p < r) {
            if ((r - p + 1) < threshholdForInsertionSort) {
                insertionSort(arr, p, r);
            }else {
                int q = p + (r - p) / 2;
                mergeSort4(arrB, arr, p, q);
                mergeSort4(arrB, arr, q + 1, r);
                merge3(arr, arrB, p, q, r);
            }
        }
    }


    public static void merge0(int[] arr, int p, int q, int r) {
        int[] arrB = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrB[i] = arr[i];
        }
        int i = p, k = p, j = q + 1;
        while (i <= q && j <= r) {
            if (arrB[i] <= arrB[j]) {
                arr[k++] = arrB[i++];
            } else {
                arr[k++] = arrB[j++];
            }
        }

        while (i <= q) {
            arr[k++] = arrB[i++];
        }

        while (j <= r) {
            arr[k++] = arrB[j++];
        }
    }


    public static void merge3(int[] arr, int[] arrB, int p, int q, int r) {
        int i = p, j = q + 1, k = p;
        while (i <= q && j <= r) {
            if (arrB[i] <= arrB[j]) {
                arr[k++] = arrB[i++];
            } else {
                arr[k++] = arrB[j++];
            }
        }

        while (i <= q && i < arr.length) {
            arr[k++] = arrB[i++];
        }

        while (j <= r && i < arr.length) {
            arr[k++] = arrB[j++];
        }
    }

    /* initialize the array with worst case input. Nice algorithm
     * src: https://stackoverflow.com/questions/24594112/when-will-the-worst-case-of-merge-sort-occur
     */
    public static void wcInitArray(int[] arr, int start, int sz) {

        if (sz == 1) {
            arr[start] = 1;
            return;
        }
        int lsz = sz / 2;
        //int rsz = (sz%2 == 0 ? lsz : lsz+1);
        wcInitArray(arr, start, lsz);
        wcInitArray(arr, start + lsz, (sz % 2 == 0 ? lsz : lsz + 1));
        for (int i = start; i < start + lsz; i++) {
            arr[i] *= 2;
        }
        for (int i = start + lsz; i < start + sz; i++) {
            arr[i] = arr[i] * 2 - 1;
        }
    }

    // copy array inp to arr
    public static void initArray(int[] arr) {
        System.arraycopy(wcInput, 0, arr, 0, arr.length);
    }


    /**
     * Timer class for roughly calculating running time of programs
     *
     * @author rbk
     * Usage:  Timer timer = new Timer();
     * timer.start();
     * timer.end();
     * System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() {
            if (!ready) {
                end();
            }
            return elapsedTime;
        }

        public long memory() {
            if (!ready) {
                end();
            }
            return memUsed;
        }

        public void scale(int num) {
            elapsedTime /= num;
        }

        public String toString() {
            if (!ready) {
                end();
            }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / " + (memAvailable / 1048576) + " MB.";
        }
    }

    /**
     * @author rbk : based on algorithm described in a book
     */


    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static <T> void shuffle(T[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from + 1;
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        public static <T> void shuffle(T[] arr, int from, int to) {
            int n = to - from + 1;
            Random random = new Random();
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        static <T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        public static <T> void printArray(T[] arr, String message) {
            printArray(arr, 0, arr.length - 1, message);
        }

        public static <T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for (int i = from; i <= to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }
    }
}

