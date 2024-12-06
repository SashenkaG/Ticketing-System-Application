package com.sashenka.OOP.CW.Event_Ticketing;

import java.util.logging.Logger;

public class Simulator {
    private static final Logger logger = Logger.getLogger(Simulator.class.getName());

    public void initializeVendors(Configuration config, TicketPool ticketPool) {
        System.out.println("Initializing vendors...");
        logger.info("Initializing vendors.");
        Vendor[] vendors = new Vendor[10];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendors[i], "Vendor ID-" + i);
            vendorThread.start();
        }
        System.out.println("Vendors initialized.");
        logger.info("Vendors initialized.");
    }
    public void initializeCustomers(Configuration config, TicketPool ticketPool) {
        System.out.println("Initializing customers...");
        logger.info("Initializing customers.");
        Customer[] customers = new Customer[10];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(ticketPool, config.getCustomerRetrievalRate(), 5);
            Thread customerThread = new Thread(customers[i], "Customer ID-" + i);
            customerThread.start();
        }
        System.out.println("Customers initialized.");
        logger.info("Customers initialized.");
    }
}