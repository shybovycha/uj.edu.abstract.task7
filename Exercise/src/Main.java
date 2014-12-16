/**
 * Created by shybovycha on 16.12.14.
 */
public class Main {
    public static void main(String[] args) {
        DoubleLinkedList<Object> dll = new DoubleLinkedList<>();

        dll.add("Ala");
        dll.add("Ola");
        dll.add("Ula");

        dll.add(0, "Nela");

        while (dll.size() > 0) {
            dll.removeAt(0);
        }

        System.out.printf("New list:\n");

        for (Object i : dll) {
            System.out.printf("%s\n", i);
        }
    }
}
