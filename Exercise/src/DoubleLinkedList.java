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

        boolean setTail = (index == this.size());

        if (this.head == null) {
            this.head = elt;
        } else if (this.tail == null) {
            this.tail = elt;
            elt.previous = this.head;
            this.head.next = elt;
        } else if (index == 0) {
            this.head.previous = elt;
            elt.next = this.head;
            this.head = elt;
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

            elt.next = curr.next;
            elt.previous = curr;
            curr.next = elt;

            if (setTail) {
                this.tail = elt;
            }
        }
    }

    public void reverse() {
        Element<T> currNew = new Element<>(this.tail.value);
        Element<T> headNew = currNew;
        Element<T> curr = this.tail.previous;

        while (curr != null) {
            Element<T> tmp = new Element<>(curr.value);
            currNew.next = tmp;
            tmp.previous = currNew;
            curr = curr.previous;
            currNew = tmp;
        }

        this.head = headNew;
        this.tail = currNew;
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

        if (index == 0) {
            if (this.head == null) {
                throw new IndexOutOfBoundsException();
            }

            this.head = this.head.next;

            if (this.head != null) {
                this.head.previous = null;
            } else {
                this.tail = null;
            }
        } else if (index == this.size() - 1) {
            if (this.tail == null) {
                throw new IndexOutOfBoundsException();
            }

            this.tail = this.tail.previous;
            this.tail.next = null;
        } else {
            Element<T> curr = this.head;
            int i = 0;

            while (i < index && curr != null) {
                curr = curr.next;
                i++;
            }

            if (curr == null) {
                throw new IndexOutOfBoundsException();
            }

            curr.previous.next = curr.next;
            curr.next.previous = curr.previous;
        }
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