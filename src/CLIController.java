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
        System.out.println("-----------------------------------------------------------");

        // Running until the user starts or stops
        while (true) {
            System.out.println("Type 'start' to begin or 'stop' to exit.");
            System.out.println("\nEnter Command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("start")){
                runSimulation();
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

        // Initialize lists for vendor and customer threads
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        // Create threads for vendors and customers
        Thread vendorManager = new Thread(() -> {
            int vendorCount = 3; // Number of vendor threads
            for (int i = 1; i <= vendorCount; i++) {
                Thread vendorThread = new Thread(new Vendor("Vendor- " + i, ticketPool, ticketReleaseRate));
                vendorThreads.add(vendorThread);
                vendorThread.start();
                try {
                    Thread.sleep(ticketReleaseRate); // Delay between vendor threads
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Thread.currentThread().interrupt();
            }
        });

        Thread customerManager = new Thread(() -> {
            int customerCount = 3; // Number of customer threads
            for (int i = 1; i <= customerCount; i++) {
                Thread customerThread = new Thread(new Customer("Customer- " + i, ticketPool, customerRetrievalRate));
                customerThreads.add(customerThread);
                customerThread.start();
                try {
                    Thread.sleep(customerRetrievalRate); // Delay between customer threads
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Thread.currentThread().interrupt();
            }
        });

        vendorManager.start();
        customerManager.start();

        // Wait for manager threads to finish
        try {
            vendorManager.join();
            customerManager.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Simulation interrupted.");
        }

        // Wait for vendor and customer threads to finish
        try {
            for (Thread thread : vendorThreads) {
                thread.join();
            }
            for (Thread thread : customerThreads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted while waiting for threads to finish.");
        }

        System.out.println("Simulation completed.");
    }
}