import java.util.Random;

public class Customer implements Runnable{
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final Random random;

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) { // Check interruption status
            try {
                int ticketsToBuy = random.nextInt(3) + 1;
                ticketPool.removeTickets(ticketsToBuy);

                Thread.sleep(customerRetrievalRate); // Sleep may throw InterruptedException
            } catch (InterruptedException e) {
                System.out.println("Customer thread interrupted.");
                break; // Exit the loop if interrupted
            }
        }
        System.out.println("Customer stopped.");
    }
}


