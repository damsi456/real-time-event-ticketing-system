import java.util.Random;

public class Customer implements Runnable{
    private final String name;
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final Random random;

    /**
     * Constructor
     * @param name the name of the customer
     * @param ticketPool the ticket pool object
     * @param customerRetrievalRate how often customers remove tickets from the pool
     */
    public Customer(String name, TicketPool ticketPool, int customerRetrievalRate) {
        this.name = name;
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.random = new Random();
    }

    /**
     * Simulates the behavior of the customer
     */
    @Override
    public void run() {
            int ticketsToBuy = random.nextInt(5) + 1; // Assuming that a customer can buy max 5 tickets at a time
            ticketPool.removeTickets(name, ticketsToBuy, customerRetrievalRate);
    }
}


