package SP1;

public interface DLLIteratorExtension<T> {

    //New methods for Doubly Linked List class
    boolean hasPrev();
    T prev();
    void add(T x);

    //Existing method overridden in Doubly Linked List class
    void remove();

    //Provided in interface to use them in the Doubly linked list class
    boolean hasNext();
    T next();
}