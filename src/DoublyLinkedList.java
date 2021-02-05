/** @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 *  Doubly linked list
 *  Ver 1.0: 2021/02/05
 */

import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {

    //Added previous pointer in SLL Entry
    static class Entry<E> extends SinglyLinkedList.Entry<E>{

        Entry<E> prev;

        Entry(E x, Entry<E> nxt, Entry<E> prev) {
            super(x, nxt);
            this.prev = prev;
        }
    }

    //Added previous pointer in the constructor
    public DoublyLinkedList(){
        head = new Entry<>(null, null, null);
        tail = head;
        size = 0;
    }


    public DLLIterator dllIterator(){
        return new DLLIterator();
    }

    protected class DLLIterator extends SLLIterator implements DLLIteratorExtension<T>{


        @Override
        public boolean hasPrev() {
            if(((Entry<T>)cursor).prev == null || cursor == head){
                throw new NoSuchElementException("No previous element exists for the node");
            }else
                return true;
        }

        @Override
        public T prev() {
            if(hasPrev()){
                cursor = ((Entry<T>)cursor).prev;
                prev = ((Entry<T>) cursor).prev;

                //Assume that it is similar to next() operation in SLL where remove operation is allowed only after next
                if (cursor != head){
                    ready = true;
                }
            }
            return cursor.element;
        }

        // Add new element in the list from Iterator
        @Override
        public void add(T x) {
            Entry<T> newEntry = new Entry<>(x, null, null);
            newEntry.next = cursor.next;
            cursor.next = newEntry;
            newEntry.prev = (Entry<T>) cursor;

            //also updating previous pointer which is tracked in SLL
            prev = newEntry.prev;

            if(cursor != tail)
                ((Entry<T>)newEntry.next).prev = newEntry;
            else
                tail = newEntry;

            //Leaving cursor at the next entry
            cursor = cursor.next;
            size++;
        }


        @Override
        public void remove(){
            if (cursor != tail)
                ((Entry)cursor.next).prev = ((Entry)cursor).prev;
            super.remove();
        }
    }

    //overrides singly link list add method for new entry type
    @Override
    public void add(T x) {
        //passed tail because singly linked list add method do not change previous pointer.
        add(new Entry<T>(x, null, (Entry<T>) tail));
    }


    public static void main(String[] args) throws NoSuchElementException {

        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        DoublyLinkedList<Integer> myList = new DoublyLinkedList<>();

        for(int i=1; i<=n; i++) {
            myList.add(Integer.valueOf(i));
        }
        myList.printList();

        DLLIteratorExtension dllIterator = myList.dllIterator();


        Scanner in = new Scanner(System.in);
        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:  // Move to next element and print it
                    if (dllIterator.hasNext()) {
                        System.out.println(dllIterator.next());
                    } else {
                        break whileloop;
                    }
                    break;
                case 2: // insert new element x before the next of current element
                    System.out.println("Enter the number (**Only numbers are accepted**):");
                    int number = in.nextInt();
                    dllIterator.add(Integer.valueOf(number));
                    myList.printList();
                    break;
                case 3: // Remove the element and print it
                    // Please note that remove is only allowed for once with every next() or prev() call
                    dllIterator.remove();
                    myList.printList();
                    break;
                case 4: // Move to previous element and print it
                    try {
                        if (dllIterator.hasPrev())
                            System.out.println(dllIterator.prev());
                        else
                            break whileloop;
                    }catch (NoSuchElementException e){
                        e.printStackTrace();
                    }

                    break;
                case 5: // print list
                    myList.printList();
                    System.out.println("Rearranging elements");
                    myList.unzip();
                    myList.printList();
                default:  // Exit loop
                    break whileloop;
            }
        }
    }
}


/*
Sample Input:
1 1 1 1 2 2 3 1 3 4 4 1 5
Sample Output:
10: 1 2 3 4 5 6 7 8 9 10
1
1
1
2
1
3
1
4
2
Enter the number (**Only numbers are accepted**):
12
11: 1 2 3 4 12 5 6 7 8 9 10
2
Enter the number (**Only numbers are accepted**):
52
12: 1 2 3 4 12 52 5 6 7 8 9 10
3
11: 1 2 3 4 12 5 6 7 8 9 10
1
5
3
10: 1 2 3 4 12 6 7 8 9 10
4
4
4
3
1
4
5
10: 1 2 3 4 12 6 7 8 9 10
Rearranging elements
10: 1 3 12 7 9 2 4 6 8 10
 */