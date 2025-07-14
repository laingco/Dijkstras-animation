import java.util.ArrayList;

public class Queue {
    private ArrayList<DijkstrasNode> list;
    private int front;
    private int rear;

    public Queue() {
        list = new ArrayList<>();
        front = 0;
        rear = 0;
    }

    public void enqueue(DijkstrasNode item) {
        list.add(item);
        rear++;
    }

    public DijkstrasNode dequeue() {
        if (isEmpty()) {
            return null;
        }
        DijkstrasNode item = list.get(front);
        front++;
        if (front > list.size() / 2) {
            list = new ArrayList<>(list.subList(front, rear));
            rear = rear - front;
            front = 0;
        }
        return item;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public DijkstrasNode peek() {
        if (isEmpty()) {
            return null;
        }
        return list.get(front);
    }
}
