public class Element<T> {
    protected T value;
    protected Element<T> previous;
    protected Element<T> next;

    public Element() {
        this.value = null;
        this.next = null;
        this.previous = null;
    }

    public Element(T value) {
        this.value = value;
        this.next = null;
        this.previous = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Element<T> getPrevious() {
        return previous;
    }

    public Element<T> getNext() {
        return next;
    }

    public void setPrevious(Element<T> previous) {
        this.previous = previous;
    }

    public void setNext(Element<T> next) {
        this.next = next;
    }
}