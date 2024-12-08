import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final int maxCapacity;
    private final List <Integer> tickets;

    /**
     * Constructor
     * @param maxCapacity maximum capacity for ticketPool
     */
    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.tickets = new ArrayList<>();
        for (int i = 1; i <= totalTickets; i++) {
            tickets.add(i);
        }
    }

    /**
     * Adds tickets to the pool
     * @param vendorName name of the vendor
     * @param count number of tickets to add
     */
    public synchronized void addTickets(String vendorName, int count) {
        while (tickets.size() + count > maxCapacity){
            System.out.println(vendorName + " is waiting as the pool is full.");
            // Make vendor wait if the pool is full
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        for (int i = 0; i < count; i++) {
            tickets.add(tickets.size()+1);
        }

        System.out.println(vendorName + " added " + count + " ticket(s) to the pool. Total tickets available: "
                + tickets.size());
        // Notify all waiting customers that tickets have been added
        notifyAll();
    }

    /**
     * Removes tickets from the pool
     * @param customerName name of the customer
     * @param count number of tickets to remove
     */
    public synchronized void removeTickets(String customerName, int count) {
        while (tickets.size() < count) {
            System.out.println(customerName + " is waiting as there are not enough tickets.");
            // Make customer wait if there are not enough tickets in the pool
            try{
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }

        for (int i = 0; i < count; i++) {
            tickets.removeLast();
        }

        System.out.println(customerName + " bought " + count + " ticket(s). Tickets remaining: " + tickets.size());
        // Notify all waiting vendors that there are space in the pool
        notifyAll();
    }

    public List<Integer> getTicketPool() {
        return new ArrayList<>(tickets);
    }
}
