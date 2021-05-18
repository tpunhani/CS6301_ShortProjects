/**
 * @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 * Hashing - SP8
 * Ver 1.0: 2021/04/16
 */

package SP8;

import java.util.*;
import idsa.Timer;

public class Cuckoo<T> {

    Entry<T>[][] hashTable; //define the hash table
    HashMap<T, Integer> map = new HashMap<>(); //to store hash function failures after thresh-hold
    float loadFactor;   //maximum load of the hash table size
    int maxSize; //maximum size of the table
    int size; //current size of the table
    int k; //number of hash functions -> columns in the hash table
    int threshold; //maximum number for the loop of hash failures

    // Entry corresponding to an element in Hash Table
    static class Entry<T> {
        T element;

        public Entry(T element) {
            this.element = element;
        }
    }

    // Default Constructor
    public Cuckoo(int size1, float loadFactor, int k) {
        this.loadFactor = loadFactor;
        size = 0;
        this.k = k;
        maxSize = size1;
        hashTable = new Entry[maxSize][k];
        threshold = (int) Math.log((double) maxSize);

    }


    /**
     * Calculate the hash value of the passed integer -> Hash Function 1
     * @param h whose hash code will always be the same
     * Return integer value of hash code
     */
    public int hash1(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }


    /**
     * Calculate the hash value of the passed integer -> Hash Function 2
     * @param h whose hash code will always be the same
     * Return integer value of hash code
     */
    public int hash2(int h) {
        h ^= (h >>> 21) ^ (h >>> 13);
        return h ^ (h >>> 1) ^ (h >>> 5);
    }


    /**
     * Calculate the hash value of the passed integer -> Hash Function 3
     * @param h whose hash code will always be the same
     * Return integer value of hash code
     */
    public int hash3(int h) {
        h ^= (h >>> 21) ^ (h >>> 13);
        return h ^ (h >>> 5) ^ (h >>> 3);
    }


    /**
     * Calculate the hash value
     * @param i is the hash function number to be used
     * @param  x is the number for which hash value needs to be calculated
     * Return integer value of hash code
     */
    private int hashFunction(int i, T x) {
        switch (i) {
            case 1:
                return hash1(x.hashCode()) % maxSize;
            case 2:
                return hash2(x.hashCode()) % maxSize;
            default:
                return hash3(x.hashCode()) % maxSize;
        }
    }


    /**
     * Add new element in the hash table
     * @param x the element to be added
     * Return boolean value of the result
     */
    public boolean add(T x) {

        //check for duplicates
        if (contains(x) || map.containsKey(x))
            return false;


        if (((float) size / ((float) maxSize * k)) >= loadFactor) {
            map.put(x, hashFunction(1, x));
            System.out.println("Added in map " + map.size());
            return true;
        }
        Entry<T> newEntry = new Entry<>(x);
        int i;
        for (i = 0; i < threshold; i++) {
            if (hashTable[hashFunction((i % k) + 1, x)][i % k] == null) {
                hashTable[hashFunction((i % k) + 1, x)][(i % k)] = newEntry;
                size++;
                return true;
            } else {
                Entry<T> temp = hashTable[hashFunction((i % k) + 1, x)][i % k];
                hashTable[hashFunction((i % k) + 1, x)][i % k] = newEntry;
                newEntry = temp;
                x = temp.element;
            }
        }

        //when no space is available for the element of it's index
        if (i == threshold) {
            map.put(x, hashFunction(1, x));
            System.out.println("Added in map " + map.size());
            return true;
        }
        return false;
    }



    /**
     * Find if the element exists in the hash table or secondary storage
     * @param x the element to be added
     * Return boolean value of the result
     */
    public boolean contains(T x) {
        for (int i = 0; i < k; i++) {
            if (hashTable[hashFunction((i % k) + 1, x)][i % k] != null &&
                    hashTable[hashFunction((i % k) + 1, x)][i % k].element.equals(x)) {
                return true;
            }
        }
        return map.containsKey(x);
    }


    /**
     * Remove the element from hash table
     * @param x the element to be removed
     * Return the removed element
     */
    public T remove(T x) {
        //check if hash table size is 0
        if(size == 0){
            return null;
        }

        //check in every hashtable column
        for (int i = 0; i < k; i++) {
            if (hashTable[hashFunction((i % k) + 1, x)][i % k] != null &&
                    hashTable[hashFunction((i % k) + 1, x)][i % k].element.equals(x)) {
                hashTable[hashFunction((i % k) + 1, x)][i] = null;
                size--;
                return x;
            }
        }

        //if the element not exist in the hashtable than check in the secondary map
        if (map.containsKey(x)) {
            map.remove(x);
            return x;
        }
        return null;
    }

    public static void main(String[] args) {
        float loadfactor=0.50f;
        int k=2;
        int size = 1 * 1000000;
        List<String> options = new ArrayList<String>();
        options.add("Add");
        options.add("Remove");
        options.add("Contains");
        Random rand = new Random();

        Timer timer = new Timer();
        int numTrials=10;
        for (int t = 0; t < numTrials; t++) {
            Cuckoo<Integer> cuckoo = new Cuckoo<Integer>(size, loadfactor, k);
//            Map<Integer, Integer> cuckoo = new Hashtable<>(size, loadfactor);
//            Map<Integer, Integer> cuckoo = new HashMap<>(size, loadfactor);

//            Set<Integer> cuckoo = new HashSet<>(size, loadfactor);
            for (int i = 1; i < 2 * size; i += 1) {
                int option_number = rand.nextInt(3);
//                System.out.println("Value of i: "+i);
                String choosed_option = options.get(option_number);

                if (choosed_option.equals("Add")) {
                    cuckoo.add(i);
                } else if (choosed_option.equals("Remove")) {
                    cuckoo.remove(i);
                } else {
                    cuckoo.contains(i);
                }

            }
        }
        timer.end();
        timer.scale(numTrials);
        System.out.println(size + "\n" + timer );
    }
}
