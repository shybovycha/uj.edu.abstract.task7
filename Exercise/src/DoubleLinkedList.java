import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements Iterable<T> {
    protected Element<T> head;
    protected Element<T> tail;

    public DoubleLinkedList() {

    }

    public DoubleLinkedList(Iterable<T> elements) {
        for (T i : elements) {
            add(i);
        }
    }

    public Element<T> getHead() {
        return head;
    }

    public Element<T> getTail() {
        return tail;
    }

    // insertAtEnd
    public void add(T val) {
        add(this.size(), val);
    }

    // insertAt
    public void add(int index, T val) {
        Element<T> elt = new Element<T>(val);

        if (this.head == null) {
            this.head = elt;
        } else if (this.tail == null || index == this.size()) {
            this.tail = elt;
            elt.previous = this.head;
            this.head.next = elt;
        } else {
            Element<T> curr = this.head;

            int i = 1;

            while (curr != null && i < index) {
                curr = curr.next;
                i++;
            }

            if (curr == null) {
                return;
            }

            elt.previous = curr;
            elt.next = curr.next;
            curr.next = elt;
        }
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

        if (curr == null) {
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
        int i = 0;

        while (curr != null) {
            curr = curr.next;
            i++;
        }

        return i;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements java.util.Iterator<T> {
        private Element<T> current;

        private LinkedListIterator() {
            this.current = head;  // from the enclosing class --
            // ListIterator cannot be a static class
        }

        public boolean hasNext() {
            return (this.current != null);
        }

        public T next() {
            if (hasNext()) {
                T result = this.current.value;
                this.current = this.current.next;   // may be null

                return result;
            }  // no next element

            throw new NoSuchElementException("linked list.next");
        }

        public void remove() {
            throw new UnsupportedOperationException("Linked list iterator remove not supported");
        }
    }
}