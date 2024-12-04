package com.sashenka.OOP.CW.Event_Ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.*;

@SpringBootApplication
public class EventTicketingApplication {
	private static final Logger logger = Logger.getLogger(EventTicketingApplication.class.getName());

	public static void main(String[] args) {
		configureLogger(); // Configure logging to a file
		SpringApplication.run(EventTicketingApplication.class, args);
		logger.info("Event Ticketing Application started.");

		Configuration config = new Configuration();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Do you want to load the previous configuration? (yes/no)");
			String userChoice = scanner.nextLine().trim().toLowerCase();

			if (userChoice.equals("yes")) {
				try {
					config = Configuration.loadFromJson("config.json");
					System.out.println("Configuration loaded successfully from config.json.");
					displayConfiguration(config);
					logger.info("Configuration loaded successfully.");
					break;
				} catch (RuntimeException e) {
					logger.warning("Failed to load configuration from config.json: " + e.getMessage());
					System.out.println("Failed to load configuration. Creating a new one.");
				}
			} else if (userChoice.equals("no")) {
				promptForConfiguration(config, scanner);
				config.saveToJson("config.json");
				logger.info("New configuration saved to config.json.");
				break;
			} else {
				System.out.println("Invalid input. Please type 'yes' or 'no'.");
				logger.warning("Invalid input for configuration choice: " + userChoice);
			}
		}

		TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());
		Simulator simulator = new Simulator(); // Instantiate Simulator
		simulator.initializeVendors(config, ticketPool);
		simulator.initializeCustomers(config, ticketPool);
	}

	private static void configureLogger() {
		try {
			FileHandler fileHandler = new FileHandler("event_ticketing.log", true);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			logger.setLevel(Level.ALL);
		} catch (IOException e) {
			System.err.println("Failed to set up logging to file: " + e.getMessage());
		}
	}

	private static void promptForConfiguration(Configuration config, Scanner scanner) {
		System.out.println("Let's set up your configuration:");
		config.setTotalTickets(promptForValidInt(scanner, "Enter total number of tickets: "));
		config.setTicketReleaseRate(promptForValidInt(scanner, "Enter ticket release rate: "));
		config.setCustomerRetrievalRate(promptForValidInt(scanner, "Enter customer retrieval rate: "));
		config.setMaxTicketCapacity(promptForValidInt(scanner, "Enter maximum ticket capacity: "));
		logger.info("Configuration setup complete.");
	}

	private static int promptForValidInt(Scanner scanner, String prompt) {
		while (true) {
			System.out.println(prompt);
			String input = scanner.nextLine();

			try {
				int value = Integer.parseInt(input);
				if (value <= 0) {
					System.out.println("Error: Value must be a positive integer. Please try again.");
					logger.warning("Invalid input (non-positive integer): " + input);
				} else {
					System.out.println("Accepted: " + value);
					logger.info("Valid integer input: " + value);
					return value;
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Invalid input. Please enter a valid integer.");
				logger.warning("Invalid input (not an integer): " + input);
			}
		}
	}

	private static void displayConfiguration(Configuration config) {
		System.out.println("Current Configuration:");
		System.out.println("Total Tickets: " + config.getTotalTickets());
		System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
		System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
		System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
		logger.info("Displayed current configuration.");
	}
}
