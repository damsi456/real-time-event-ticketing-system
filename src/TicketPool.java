import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final int maxCapacity;
    private final List <Integer> tickets;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new ArrayList<>();
    }

    public synchronized void addTickets(int count) {
        String threadName = Thread.currentThread().getName();

        if(tickets.size() + count <= maxCapacity) {
            for (int i=0; i < count; i++) {
                tickets.add(tickets.size() + 1);
            }
            System.out.println(threadName + " added " + count + " ticket(s) to the pool. Total tickets available: " +
                    tickets.size());
        }

        else {
            System.out.println(threadName + "'s attempt to add " + count + " tickets failed. Total tickets available: " +
                    tickets.size());
        }
    }

    public synchronized void removeTickets(int count) {
        String threadName = Thread.currentThread().getName();
        if(tickets.size() >= count){
            for (int i = 0; i < count; i++) {
                tickets.removeLast();
            }
            System.out.println(threadName + " bought " + count + " ticket(s) from the pool. Total tickets available: " +
                    tickets.size());
        }
        else {
            System.out.println(threadName + "'s attempt to buy " + count + " tickets failed. Total tickets available: " +
                    tickets.size());
        }
    }

    public synchronized List<Integer> getTicketPool() {
        return new ArrayList<>(tickets);
    }
}
