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

    public void reverse() {
        Element<T> last = this.tail;
        Element<T> curr = this.head;

        while (curr != null && curr != this.tail) {
            Element<T> tmp = curr.next;
            last.next = curr;
            curr.next = null;
            curr.previous = last;
            last = curr;
            curr = tmp;
        }

        this.tail = last;
    }

    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Element<T> curr = this.head;
        int i = 0;

        while (i < index && curr != null) {
            curr = curr.next;
            i++;
        }

        if (curr == null || i < index) {
            throw new IndexOutOfBoundsException();
        }

        return curr.value;
    }

    public void removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Element<T> curr = this.head;
        int i = 0;

        while (i < index && curr != null) {
            curr = curr.next;
            i++;
        }

        if (curr == null || i < index) {
            throw new IndexOutOfBoundsException();
        }

        curr.previous.next = curr.next;
        curr.next.previous = curr.previous;
    }

    public int size() {
        if (this.head == null) {
            return 0;
        }

        Element<T> curr = this.head;
        int i = 1;

        while (curr != null) {
            curr = curr.next;
            i++;
        }

        return i;
    }

    @Override
    public Iterator iterator() {
        return null;
    }
}