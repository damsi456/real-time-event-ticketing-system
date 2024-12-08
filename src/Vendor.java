import java.util.Random;

public class Vendor implements Runnable{
    private final String name;
    private final TicketPool ticketPool;
    private final Random random;

    /**
     * Constructor
     * @param name the name of the vendor
     * @param ticketPool the ticket pool object
     */
    public Vendor(String name,TicketPool ticketPool) {
        this.name = name;
        this.ticketPool = ticketPool;
        this.random = new Random();
    }

    /**
     * Simulates the behavior of the vendor
     */
    @Override
    public void run() {
            int ticketsToAdd = random.nextInt(20) + 1; // Assuming that a vendor can add maximum 20 tickets at a
        // time
            ticketPool.addTickets(name, ticketsToAdd);
    }
}
