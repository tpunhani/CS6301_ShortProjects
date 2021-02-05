/** @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 *  Doubly linked list
 *  Ver 1.0: 2021/02/05
 */

package txp190029;

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