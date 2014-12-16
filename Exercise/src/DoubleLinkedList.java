import java.util.Iterator;

public class DoubleLinkedList<T> implements Iterable {
    protected Element<T> head;
    protected Element<T> tail;

    public DoubleLinkedList() {

    }

    public DoubleLinkedList(Iterable elements) {

    }

    public Element<T> getHead() {
        return head;
    }

    public Element<T> getTail() {
        return tail;
    }

    // insertAtEnd
    public void add(T val) {
        Element<T> elt = new Element<T>(val);
        elt.previous = this.tail;
        this.tail = elt;
    }

    // insertAt
    public void add(int index, T val) {
        Element<T> curr = this.head;

        int i = 1;

        while (curr != null && i < index) {
            curr = curr.next;
            i++;
        }

        if (curr == null) {
            return;
        }

        Element<T> elt = new Element<T>(val);

        elt.previous = curr;
        elt.next = curr.next;
        curr.next = elt;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}