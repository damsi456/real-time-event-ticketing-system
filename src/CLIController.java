import java.util.Scanner;

public class CLIController {
    private TicketPool ticketPool;
    private Thread vendorThread;
    private Thread customerThread;

    public void startCLI() {
        Scanner scanner = new Scanner(System.in);

        // Gather configuration inputs
        System.out.println("Enter max ticket capacity in the pool:");
        int maxCapacity = scanner.nextInt();

        System.out.println("Enter vendor release interval (ms):");
        int ticketReleaseRate = scanner.nextInt();

        System.out.println("Enter customer retrieval interval (ms):");
        int customerRetrievalRate = scanner.nextInt();

        // Get the input for no. of iterations for vendor and customer
        System.out.println("Enter number of vendors who would release tickets: ");
        int vendorIterations = scanner.nextInt();

        System.out.println("Enter number of customers who would buy tickets: ");
        int customerIterations = scanner.nextInt();

        // Initialize TicketPool
        ticketPool = new TicketPool(maxCapacity);

        // Start Vendor and Customer threads
        vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate, vendorIterations));
        customerThread = new Thread(new Customer(ticketPool, customerRetrievalRate, customerIterations));

        vendorThread.start();
        customerThread.start();

        try {
            vendorThread.join();
            customerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("System execution completed.");
    }
}