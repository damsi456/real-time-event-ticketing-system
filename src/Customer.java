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
        try{
            while (!Thread.currentThread().isInterrupted()){
                int ticketsToRemove = random.nextInt(3) + 1;
                boolean success = ticketPool.removeTickets(ticketsToRemove);

                if(!success){
                    System.out.println("There's not enough tickets to buy.");
                }

                Thread.sleep(customerRetrievalRate);
            }
        } catch (InterruptedException e){
            System.out.println("");
        }
    }
}
