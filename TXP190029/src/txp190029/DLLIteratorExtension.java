package txp190029;

public interface DLLIteratorExtension<T> {
    boolean hasPrev();
    T prev();
    void add(T x);

    boolean hasNext();
    void remove();
    T next();
}