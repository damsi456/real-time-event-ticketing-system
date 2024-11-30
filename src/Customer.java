import java.util.Random;

public class Customer implements Runnable{
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int iterations;
    private Random random;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int iterations) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.iterations = iterations;
        this.random = new Random();
    }

    @Override
    public void run() {
            for (int i=0; i < iterations; i++){
                int ticketsToRemove = random.nextInt(3) + 1;
                ticketPool.removeTickets(ticketsToRemove);
                try{
                Thread.sleep(customerRetrievalRate);
                } catch (Exception e){
                    System.out.println();
                    break;
            }
        }
        System.out.println(iterations + " customers have bought tickets.");
    }
}


