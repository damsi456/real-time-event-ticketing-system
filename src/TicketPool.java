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
        if(tickets.size() + count <= maxCapacity) {
            for (int i=0; i < count; i++) {
                tickets.add(1);
            }
            System.out.println(count + " ticket(s) added to the pool by a vendor. Total tickets available: " +
                    tickets.size());
        }

        else {
            System.out.println("A vendor's attempt to add a ticket failed. Ticket pool is at max capacity (" +
                    maxCapacity +").");
        }
    }

    public synchronized void removeTickets(int count) {
        if(tickets.size() >= count){
            for (int i = 0; i < count; i++) {
                tickets.removeFirst();
            }
            System.out.println(count + " ticket(s) bought from the pool by a customer. Total tickets available: " +
                    tickets.size());
        }
        else {
            System.out.println("There wasn't enough tickets to fulfil a customer's attempt. Total tickets available: " +
                    tickets.size());
        }
    }

    public synchronized int getTotalTickets() {
        return tickets.size();
    }

    public synchronized List<Integer> getTicketPool() {
        return new ArrayList<>(tickets);
    }
}
