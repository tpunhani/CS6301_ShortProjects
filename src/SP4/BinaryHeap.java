/** @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 *  Binary Heap
 *  Ver 1.0: 2021/03/05
 */
package SP4;

import java.util.*;

public class BinaryHeap<T extends Comparable<? super T>> {
    Comparable[] pq;
    int size;

    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(int maxCapacity) {
        pq = new Comparable[maxCapacity];
        size = 0;
    }

    // add method: resize pq if needed
    public boolean add(T x) {
        if (size >= pq.length - 1) {
            resize();
        }
        pq[size] = x;
        percolateUp(size);
        size++;
        return true;
    }

    public boolean offer(T x) {
        return add(x);
    }

    // throw exception if pq is empty
    public T remove() throws NoSuchElementException {
        T result = poll();
        if (result == null) {
            throw new NoSuchElementException("Priority queue is empty");
        } else {
            return result;
        }
    }

    // return null if pq is empty
    public T poll() {
        if (isEmpty())
            return null;

        Comparable min = pq[0];
        pq[0] = pq[size - 1];
        size--;
        percolateDown(0);
        return (T) min;
    }

    public T min() {
        return peek();
    }

    // return null if pq is empty
    public T peek() {
        if (isEmpty())
            return null;
        T topElement = (T) pq[0];
        return topElement;
    }

    int parent(int i) {
        return (i - 1) / 2;
    }

    int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * pq[index] may violate heap order with parent
     */
    void percolateUp(int index) {
        while (compare(pq[index], pq[parent(index)]) < 0 && index >= 0) {
            Comparable temp = pq[index];
            pq[index] = pq[parent(index)];
            pq[parent(index)] = temp;

            index = parent(index);
        }
    }

    /**
     * pq[index] may violate heap order with children
     */
    void percolateDown(int index) {
        Comparable x = pq[index];
        int small = leftChild(index);
        while (small <= size - 1) {
            if (small < size - 1 && compare(pq[small], pq[small + 1]) > 0) {
                small = small + 1;
            }
            if (compare(x, pq[small]) < 0) {
                break;
            }
            pq[index] = pq[small];
            index = small;
            small = leftChild(index);
        }
        pq[index] = x;
    }

    /**
     * use this whenever an element moved/stored in heap. Will be overridden by IndexedHeap
     */
    void move(int dest, Comparable x) {
        pq[dest] = x;
    }

    int compare(Comparable a, Comparable b) {
        return ((T) a).compareTo((T) b);
    }

    /**
     * Create a heap.  Precondition: none.
     */
    void buildHeap() {
        for (int i = parent(size - 1); i >= 0; i--) {
            percolateDown(i);
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    // Resize array to double the current size
    void resize() {
        int newSize = (pq.length) * 2;
        Comparable[] newQueue = new Comparable[newSize];
        System.arraycopy(pq, 0, newQueue, 0, pq.length);
        pq = newQueue;
    }

    public interface Index {
        public void putIndex(int index);

        public int getIndex();
    }

    // IndexedHeap is useful to implement algorithms, such as Kruskal's MST, that requires
    // decreseKey() operation. You can impelement this now or later when you implement MST algorithms
    public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {
        /**
         * Build a priority queue with a given array
         */
        IndexedHeap(int capacity) {
            super(capacity);
        }

        /**
         * restore heap order property after the priority of x has decreased
         */
        void decreaseKey(T x) {
        }

        @Override
        void move(int i, Comparable x) {
            super.move(i, x);
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {0, 9, 7, 5, 3, 1, 8, 6, 4, 2};
        BinaryHeap<Integer> h = new BinaryHeap(arr.length);

        System.out.print("Before:");
        for (Integer x : arr) {
            h.offer(x);
            System.out.print(" " + x);
        }
        System.out.println();


        for (int i = 0; i < arr.length; i++) {
            arr[i] = h.poll();
        }

        System.out.print("After :");
        for (Integer x : arr) {
            System.out.print(" " + x);
        }
        System.out.println();
    }

}

