import java.util.Random;

public class Customer implements Runnable{
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private Random random;

    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.random = new Random();
    }

    @Override
    public void run() {
        int iterations = random.nextInt(10) + 1;
        for(int i=0; i < iterations; i++) {
            int ticketsToRemove = random.nextInt(3) + 1;
            ticketPool.removeTickets(ticketsToRemove);
            try{
                Thread.sleep(customerRetrievalRate);
            } catch (InterruptedException e){
                System.out.println();
                break;
            }
        }
    }
}


