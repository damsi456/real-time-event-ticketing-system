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
        int iterations = random.nextInt(10) + 1;
        for(int i=0; i < iterations; i++) {
            int ticketsToAdd = random.nextInt(10) + 1;
            ticketPool.addTickets(ticketsToAdd);
            try {
                Thread.sleep(ticketReleaseRate);// Wait based on ticket release rate
            } catch (InterruptedException e){
                System.out.println();
                break;
            }
        }
        System.out.println(iterations + " vendors have added their tickets.");
    }
}
