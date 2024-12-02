import java.util.Scanner;

public class CLIController {
    private TicketPool ticketPool;

    public void startCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Real-Time Event Ticketing System Simulation!");
        System.out.println("Type 'start' to begin or 'stop' to exit.");

        while (true) {
            System.out.println("\nEnter Command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("start")){
                runSimulation();
                break;
            } else if (command.equals("stop")){
                System.out.println("Exiting the simulation...\nGoodbye!");
                break;
            } else {
                System.out.println("Invalid Command. Please, type 'start' to begin or 'stop' to exit.");
            }
        }
        scanner.close();
    }

    public void runSimulation() {
        System.out.println("Starting simulation....");

        Scanner scanner = new Scanner(System.in);

        // Gather configuration inputs
        System.out.println("Enter max ticket capacity in the pool:");
        int maxCapacity = scanner.nextInt();

        System.out.println("Enter vendor release interval (ms):");
        int ticketReleaseRate = scanner.nextInt();

        System.out.println("Enter customer retrieval interval (ms):");
        int customerRetrievalRate = scanner.nextInt();

        scanner.nextLine();

        // Initialize TicketPool
        ticketPool = new TicketPool(maxCapacity);

        // Start Vendor and Customer threads
        Thread vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate));
        Thread customerThread = new Thread(new Customer(ticketPool, customerRetrievalRate));

        vendorThread.start();
        customerThread.start();

        while (true) {
            System.out.print("Type 's' to end the simulation. \n \n");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("s")) {
                vendorThread.interrupt(); // Interrupt threads
                customerThread.interrupt();
                break;
            }
        }

        try {
            vendorThread.join();
            customerThread.join();
            System.out.println("Exiting the simulation....");
            System.out.println("Final Ticket Pool Status: " + ticketPool.getTicketPool());
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }
    }
}