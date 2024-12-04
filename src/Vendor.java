import java.util.Random;

public class Vendor implements Runnable{
    private final String name;
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final Random random;

    public Vendor(String name,TicketPool ticketPool, int ticketReleaseRate) {
        this.name = name;
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            int ticketsToAdd = random.nextInt(5) + 1;
            ticketPool.addTickets(name, ticketsToAdd, ticketReleaseRate);
        }
    }
}
