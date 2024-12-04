import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private final int maxCapacity;
    private final List <Integer> tickets;
    private boolean running = true;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = new ArrayList<>();
    }

    public synchronized void addTickets(String vendorName, int count, int ticketReleaseRate) {
        while (tickets.size() + count > maxCapacity){
            System.out.println(vendorName + " is waiting as the pool is full.");
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
        notifyAll();

        try{
            Thread.sleep(ticketReleaseRate);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void removeTickets(String customerName, int count, int customerRetrievalRate) {
        while (tickets.size() < count) {
            System.out.println(customerName + " is waiting as there are not enough tickets.");
            try{
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }

        for (int i = 0; i < count; i++) {
            tickets.removeFirst();
        }

        System.out.println(customerName + " bought " + count + " ticket(s). Tickets remaining: " + tickets.size());
        notifyAll();

        try{
            Thread.sleep(customerRetrievalRate);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized List<Integer> getTicketPool() {
        return new ArrayList<>(tickets);
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void stopSimulation() {
        running = false;
        notifyAll(); // Notify all threads to stop waiting
    }
}
