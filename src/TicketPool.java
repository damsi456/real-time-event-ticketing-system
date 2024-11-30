import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final int maxCapacity;
    private final List <Integer> tickets;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new ArrayList<>();
    }

    public synchronized boolean addTickets(int count) {
        if(tickets.size() + count <= maxCapacity) {
            for (int i=0; i < count; i++) {
                tickets.add(tickets.size() + 1);
            }
            System.out.println(count + " tickets added. Current ticket pool: ");
            return true;
        }

        else {
            System.out.println("Cannot add more tickets. Ticket pool is at max capacity (" + maxCapacity +").");
            return false;
        }
    }

    public synchronized boolean removeTickets(int count) {
        if(tickets.size() >= count){
            for (int i = 0; i < count; i++) {
                tickets.removeFirst();
            }
            System.out.println(count + " tickets removed. Current ticket pool: ");
            return true;
        }
        else {
            System.out.println("Not enough tickets to remove.");
            return false;
        }
    }

    public synchronized int getTotalTickets() {
        return tickets.size();
    }

    public synchronized List<Integer> getTicketPool() {
        return new ArrayList<>(tickets);
    }
}
