import java.util.Random;

public class Vendor implements Runnable{
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final Random random;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(ticketReleaseRate); // Sleep may throw InterruptedException
                int ticketsToAdd = random.nextInt(5) + 1;
                ticketPool.addTickets(ticketsToAdd);

                } catch (InterruptedException e) {
                System.out.println("Vendor thread interrupted.");
                break; // Exit the loop if interrupted
            }
        }
        System.out.println("Vendor stopped.");
    }
}
