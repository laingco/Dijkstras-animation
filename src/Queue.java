import java.util.ArrayList;

public class Queue {
    private ArrayList<DijkstrasNode> list;
    private DijkstrasNode front;
    private DijkstrasNode rear;

    public Queue() {
        list = new ArrayList<>();
        front = null;
        rear = null;
    }

    public void enqueue(DijkstrasNode item) {
        list.add(item);
        rear = item;
    }

    public DijkstrasNode dequeue() {
        if (isEmpty()) {
            return null;
        }
        DijkstrasNode item = list.get(0);
        list.remove(front);
        front = list.get(0);
        return item;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public DijkstrasNode peek() {
        if (isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
