import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;

        if (size++ == 0) {
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;

        if (size++ == 0) {
            first = last;
        }
        else {
            oldLast.next = last;
            last.prev = oldLast;
        }
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (--size != 0) {
            first.prev = null;
        }
        else {
            last = null;
        }

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        if (--size != 0) {
            last.next = null;
        }
        else {
            first = null;
        }

        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(0);
        deque.addFirst(1);
        deque.addFirst(2);
        
        Iterator<Integer> iter = deque.iterator();
        System.out.println(iter.hasNext());
        System.out.println(iter.next());
        System.out.println(iter.hasNext());
        System.out.println(iter.next());
        System.out.println(iter.hasNext());
        System.out.println(iter.next());
        System.out.println(iter.hasNext());
        System.out.println(iter.next());
    }
}
