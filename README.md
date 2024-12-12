# Real-Time Event Ticketing System

This project simulates a real-time event ticketing system using a command-line interface (CLI). The system allows users to configure and run a simulation involving vendors and customers interacting with a ticket pool.

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A terminal or command-line interface

### Running the Application

1. **Compile the Java files**: Ensure all Java files are compiled. You can do this using the following command in the terminal:

    ```bash
    javac -d bin src/*.java
    ```

2. **Run the Main class**: Execute the compiled `Main` class to start the application.

    ```bash
    java -cp bin Main
    ```

### Using the CLI

Once the application is running, you will be greeted with a welcome message and prompted to enter commands.

- **Start the Simulation**: Type `start` to begin the simulation. You will be asked to provide several configuration inputs:
  - Total tickets available in the system
  - Maximum ticket capacity in the pool
  - Vendor ticket release rate (in milliseconds)
  - Customer retrieval rate (in milliseconds)
  - Number of vendors
  - Number of customers

- **Stop the Simulation**: Type `stop` to exit the application.
