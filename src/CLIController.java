import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIController {
    private final List<Thread> threads;
    private static final int numberOfThreads = 5;

    public CLIController() {
        this.threads = new ArrayList<>();
    }

    /**
     * Start the CLI for real-time event ticketing system
     */
    public void startCLI() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to the Real-Time Event Ticketing System Simulation!");
        System.out.println("Type 'start' to begin or 'stop' to exit.");

        // Running until the user starts or stops
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


    /**
     * Starts the simulation
     */
    public void runSimulation() {
        System.out.println("Starting simulation....");

        Scanner scanner = new Scanner(System.in);

        // Get configuration inputs
        System.out.println("Enter the maximum ticket capacity in the pool:");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next();
        }
        int maxCapacity = scanner.nextInt();

        System.out.println("Enter the vendor ticket release interval (ms):");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next();
        }
        int ticketReleaseRate = scanner.nextInt();

        System.out.println("Enter customer retrieval interval (ms):");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
        }
        int customerRetrievalRate = scanner.nextInt();
        scanner.nextLine();

        // Initialize TicketPool
        TicketPool ticketPool = new TicketPool(maxCapacity);

        // Start threads
        for (int i = 1; i <= numberOfThreads; i++) {
            Thread vendorThread = new Thread(new Vendor("Vendor-" + i, ticketPool, ticketReleaseRate));
            Thread customerThread = new Thread(new Customer("Customer-" + i, ticketPool, customerRetrievalRate));
            threads.add(vendorThread);
            threads.add(customerThread);
            vendorThread.start();
            customerThread.start();
        }

        // Call stop simulation when user types stop
        System.out.println("Type 'stop' to stop the simulation.");
        while (true) {
            String input = scanner.next();
            if (input.equalsIgnoreCase("stop")) {
                stopSimulation();
                break;
            }
        }

        System.out.println("Exiting the simulation...\nGoodbye!");
        System.out.println();
        System.out.println("Final Ticket Pool Status: " + ticketPool.getTicketPool());
    }

    /**
     * Interrupt all the threads
     */
    private void stopSimulation() {
        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted while waiting for " + thread.getName() + " to finish.");
            }
        }

        System.out.println("Simulation stopped.");
    }
}