import java.util.ArrayList;
import java.util.List;
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
        List<Thread> customerThreads = new ArrayList<>();
        List<Thread> vendorThreads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            vendorThreads.add(new Thread(new Vendor(ticketPool, ticketReleaseRate), "Vendor-" + (i + 1)));
        }

        for (Thread vendorThread : vendorThreads) {
            vendorThread.start();
            /*try {
                Thread.sleep(ticketReleaseRate); // Small delay to stagger customer thread starts
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }*/
        }

        for (int i = 0; i < 10; i++) {
            customerThreads.add(new Thread(new Customer(ticketPool, customerRetrievalRate), "Customer-" + (i + 1)));
        }

        for (Thread customerThread : customerThreads) {
            customerThread.start();
            /*try {
                Thread.sleep(customerRetrievalRate); // Small delay to stagger customer thread starts
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }*/
        }

        // Combine vendor and customer threads for stopping later
        List<Thread> threads = new ArrayList<>();
        threads.addAll(vendorThreads);
        threads.addAll(customerThreads);

        while (true) {
            System.out.print("Type 's' to end the simulation. \n \n");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("s")) {
                for (Thread thread : threads) {
                    thread.interrupt();
                }
                break;
            }
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(thread.getName() + " interrupted.");
            }
        }

        System.out.println("Exiting the simulation....");
        System.out.println("Final Ticket Pool Status: " + ticketPool.getTicketPool());

    }
}