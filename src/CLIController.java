import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIController {
    private List<Thread> threads;

    public CLIController() {
        this.threads = new ArrayList<>();
    }

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
        System.out.println("Enter the maximum ticket capacity in the pool:");
        int maxCapacity = scanner.nextInt();

        System.out.println("Enter the vendor ticket release interval (ms):");
        int ticketReleaseRate = scanner.nextInt();

        System.out.println("Enter customer retrieval interval (ms):");
        int customerRetrievalRate = scanner.nextInt();

        scanner.nextLine();

        // Initialize TicketPool
        TicketPool ticketPool = new TicketPool(maxCapacity);

        for (int i = 1; i <= 5; i++) {
            Thread vendorThread = new Thread(new Vendor("Vendor-" + i, ticketPool, ticketReleaseRate));
            Thread customerThread = new Thread(new Customer("Customer-" + i, ticketPool, customerRetrievalRate));
            threads.add(vendorThread);
            threads.add(customerThread);
            vendorThread.start();
            customerThread.start();
        }

        System.out.println("Type 's' to stop the simulation.");
        while (true) {
            String input = scanner.next();
            if (input.equalsIgnoreCase("s")) {
                stopSimulation();
                break;
            }
        }

        System.out.println("Exiting the simulation....");
        System.out.println("Final Ticket Pool Status: " + ticketPool.getTicketPool());
    }

    private void stopSimulation() {
        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (Thread thread : threads) {
            try {
                thread.join(); // Wait for 1 second for each thread to finish
            } catch (InterruptedException e) {
                System.out.println("Interrupted while waiting for " + thread.getName() + " to finish.");
            }
        }

        System.out.println("Simulation stopped.");
    }
}