import java.util.ArrayList;

public class PQueue {
    private ArrayList<DijkstrasNode> list;

    public PQueue() {
        list = new ArrayList<>();
    }

    public void enqueue(DijkstrasNode item) {
        int i = 0;
        while (i < list.size() && list.get(i).getDistanceFromStart() <= item.getDistanceFromStart()) {
            i++;
        }
        list.add(i, item);
    }

    public DijkstrasNode dequeue() {
        if (isEmpty()) {
            return null;
        }
        return list.remove(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public DijkstrasNode peek() {
        if (isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
