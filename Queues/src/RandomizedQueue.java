import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int size, capacity;

    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        size = 0;
        capacity = 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int c) {
        Item[] temp = (Item[]) new Object[c];
        for (int i = 0; i < size; i++) {
            temp[i] = arr[i];
        }

        capacity = c;
        arr = temp;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (size == capacity) {
            resize(capacity*2);
        }
        arr[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randNum = StdRandom.uniform(size--);
        Item temp = arr[randNum];
        arr[randNum] = arr[size];
        arr[size] = null;
        if (size > 0 && size == capacity / 4) {
            resize(capacity/2);
        }
        return temp;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return arr[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(-1);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Item[] listArr;
        private int current, index;

        public ListIterator(int first) {
            current = first;
            index = 0;
            listArr = (Item[]) arr.clone();
            StdRandom.shuffle(listArr);
            // System.out.println(Arrays.deepToString(listArr));
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (listArr[++current] == null) {
                return next();
            }
            index++;
            return (Item) listArr[current];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(0);
        rq.enqueue(1);
        rq.enqueue(2);
        
        Iterator<Integer> iter1 = rq.iterator();
        Iterator<Integer> iter2 = rq.iterator();

        System.out.println(iter1.hasNext());
        System.out.println(iter1.next());
        System.out.println(iter1.hasNext());
        System.out.println(iter1.next());
        System.out.println(iter1.hasNext());
        System.out.println(iter1.next());
        System.out.println(iter1.hasNext());
        
        System.out.println(iter2.next());
        System.out.println(iter2.next());
        System.out.println(iter2.next());
    }
}
