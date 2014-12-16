/**
 * Created by shybovycha on 16.12.14.
 */
public class Main {
    public static void main(String[] args) {
        DoubleLinkedList<Object> dll = new DoubleLinkedList<>();
        dll.add("Ala");
        dll.add("Ola");
        dll.add("Ula");
        System.out.printf("List size: %d\n", dll.size());
        System.out.printf("List at 0: %s\n", dll.get(0));
        System.out.printf("List at 1: %s\n", dll.get(1));
        System.out.printf("List at 2: %s\n", dll.get(2));
        System.out.printf("Head: %s\n", dll.getHead().getValue());
        System.out.printf("Tail: %s\n", dll.getTail().getValue());

        for (Object i : dll) {
            System.out.printf("i: %s\n", i);
        }
    }
}
