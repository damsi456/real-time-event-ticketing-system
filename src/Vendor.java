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
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int ticketsToAdd = random.nextInt(10) + 1;

                boolean success = ticketPool.addTickets(ticketsToAdd);
                if (!success) {
                    System.out.println("Vendor stopping as ticket limit reached.");
                    break;
                }

                Thread.sleep(ticketReleaseRate); // Wait based on ticket release rate
            }
        } catch (InterruptedException e){
            System.out.println("");
        }
    }
}
