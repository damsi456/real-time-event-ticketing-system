import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final int maxCapacity;
    private final List <Integer> tickets;
    private boolean running = true;

    /**
     * Constructor
     * @param maxCapacity maximum capacity for ticketPool
     */
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new ArrayList<>();
    }

    /**
     * Adds tickets to the pool
     * @param vendorName name of the vendor
     * @param count number of tickets to add
     * @param ticketReleaseRate frequency of tickets releasing by vendors
     */
    public synchronized void addTickets(String vendorName, int count, int ticketReleaseRate) {
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
     * @param customerRetrievalRate how often customers remove tickets from the pool
     */
    public synchronized void removeTickets(String customerName, int count, int customerRetrievalRate) {
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

    public synchronized List<Integer> getTicketPool() {
        return new ArrayList<>(tickets);
    }
}
