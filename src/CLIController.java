import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIController {
    private static final int numberOfThreads = 5;

    public CLIController() {
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
        System.out.println("Starting simulation....\n");

        Scanner scanner = new Scanner(System.in);

        // Get configuration inputs
        int totalTickets = getInput(scanner, "Enter total tickets available in the system: ");
        int maxCapacity = getInput(scanner, "Enter the maximum ticket capacity in the pool: ");
        int ticketReleaseRate = getInput(scanner, "Enter the vendor ticket release rate (ms): ");
        int customerRetrievalRate = getInput(scanner, "Enter customer retrieval rate (ms): ");
        scanner.nextLine();
        int vendorCount = getInput(scanner, "Enter the number of vendors: ");
        int customerCount = getInput(scanner, "Enter the number of customers: ");

        // Initialize TicketPool
        TicketPool ticketPool = new TicketPool(maxCapacity, totalTickets);

        // Initialize lists for vendor and customer threads
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        // Create threads for vendors and customers
        Thread vendorManager = new Thread(() -> {
            for (int i = 1; i <= vendorCount; i++) {
                Thread vendorThread = new Thread(new Vendor("Vendor- " + i, ticketPool));
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
            for (int i = 1; i <= customerCount; i++) {
                Thread customerThread = new Thread(new Customer("Customer- " + i, ticketPool));
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
                thread.interrupt();
                thread.join();
            }
            for (Thread thread : customerThreads) {
                thread.interrupt();
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted while waiting for threads to finish.");
        }

        System.out.println("Simulation completed.");
        System.out.println("Final Ticket Pool Status: " + ticketPool.getTicketPool());
    }

    private int getInput(Scanner scanner, String prompt) {
        System.out.println(prompt);
        while (!scanner.hasNextInt()){
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}