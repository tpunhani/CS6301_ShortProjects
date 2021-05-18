/** @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 *  Bounded Queue
 *  Ver 1.0: 2021/02/14
 */

package SP2;

import java.util.Scanner;

public class BoundedQueue<T> {
    int bqSize,maxSize,front,rear;
    Object[] bQueue;

    BoundedQueue(int size) {
        bqSize=0;
        front=0;
        maxSize = size;
        rear = front;
        bQueue = new Object[maxSize];
    }

    //adds a new element at the rear of the queue
    //returns false if the element was not added because the queue is full
    public boolean offer(Object obj){
        if(bqSize==maxSize) {
            return false;
        } else if(isEmpty()) {
            this.rear = bqSize;
            bQueue[this.rear++]=obj;
        } else if (bqSize<maxSize && this.rear==maxSize-1){
            bQueue[rear++]=obj;
            rear=0;
        } else {
            bQueue[this.rear++]=obj;
        }
        this.bqSize+=1;
        return true;
    }

    //remove and return the element at the front of the queue
    //return null if the queue is empty
    public Object poll(){
        if (bqSize>0){
            Object obj=bQueue[front];
            this.front+=1;
            if(this.front>maxSize-1) this.front=0;
            this.bqSize-=1;
            return obj;
        } else{
            System.out.println("Queue is empty.");
            return null;
        }
    }


    //return front element, without removing it (null if queue is empty)
    public Object peek() {
        return isEmpty()?null:bQueue[front];
    }

    //Returns the current size of the bounded queue
    public int size(){
        return bqSize;
    }

    //checks if the queue is empty
    public boolean isEmpty(){
        return bqSize == 0;
    }

    //clear the queue (size=0)
    //Resets all the bounded queue variables
    public void clear(){
        bQueue[this.front] = null;
        this.front = 0;
        this.rear = 0;
        this.bqSize = 0;
        System.out.println("Queue is now cleared");
    }

    //fill user supplied array with the elements of the queue, in queue order
    //If queue is already containing some elements, it will add only till it's size is full.
    public void toArray(T[] obj){
        int counter=0;
        if(bqSize==maxSize){
            System.out.println("Queue is already full.");
            return;
        }
        while(bqSize<maxSize && counter<obj.length){
            bQueue[rear++] = obj[counter++];
            if (rear > maxSize - 1) rear = 0;
            bqSize+=1;
        }
        if(bqSize==maxSize)
            System.out.println("Queue is now full. Added " + counter + " items into the queue");
    }

    public void printBQueue(){
        System.out.print(bqSize+":");
        for(int i=0;i<bqSize;i++){
            System.out.print(" "+bQueue[i]);
        }
        System.out.println("");
    }

    //A function to display the menu for the user to select
    public  void displayMenu(){
        System.out.println("Enter your choice: \n" +
                "1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue");
    }

    public static void main (String[] args){
        int maxSize;
        Scanner in = new Scanner (System.in);

        //Input the maximum size of the queue
        System.out.println("Input Maximum Size: ");
        maxSize = in.nextInt();
        BoundedQueue<Integer> boundedQueue = new BoundedQueue<>(maxSize);

        boundedQueue.displayMenu();

        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:
                    if (!boundedQueue.offer(in.nextInt())) {
                        System.out.println("Queue is full");
                    }
                    boundedQueue.displayMenu();
                    break;
                case 2:
                    Object obj=boundedQueue.poll();
                    if(obj==null)
                        System.out.println("null");
                    else
                        System.out.println((Integer)obj);
                    boundedQueue.displayMenu();
                    break;
                case 3:
                    System.out.println(boundedQueue.peek());
                    boundedQueue.displayMenu();
                    break;
                case 4:
                    System.out.println(boundedQueue.size());
                    boundedQueue.displayMenu();
                    break;
                case 5:
                    boundedQueue.clear();
                    boundedQueue.displayMenu();
                    break;
                case 6:
                    System.out.println("Enter the size of array: ");
                    int sizeOfArray = in.nextInt();
                    if(sizeOfArray > maxSize){
                        System.out.println("Please provide an array which is less than or equal to queue size");
                        boundedQueue.displayMenu();
                        break;
                    }
                    Integer[] arr = new Integer[sizeOfArray];
                    for(int i=0; i<sizeOfArray; i++){
                        arr[i] = in.nextInt();
                    }
                    System.out.println("Adding array elements till the available size of the queue");
                    boundedQueue.toArray(arr);
                    boundedQueue.displayMenu();
                    break;

                case 7:
                    boundedQueue.printBQueue();
                    boundedQueue.displayMenu();
                    break;
                default:
                    break whileloop;
            }
        }
    }
}


/*
Sample Input:
4 7 1 21 3 21 4 1 5 1 45 3 45

Sample Output:
Input Maximum Size:
4
Enter your choice:
1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue
7
0:
Enter your choice:
1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue
1
21
Enter your choice:
1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue
3
21
Enter your choice:
1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue
4
1
Enter your choice:
1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue
5
Queue is now cleared
Enter your choice:
1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue
1
45
Enter your choice:
1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue
3
45
Enter your choice:
1. Offer (Add) 2. Poll (Remove) 3. Peek 4. Size of the Queue 5. Clear the Queue 6. Convert to Array 7. Display Queue

 */