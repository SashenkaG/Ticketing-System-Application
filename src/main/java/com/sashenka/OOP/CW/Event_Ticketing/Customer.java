package com.sashenka.OOP.CW.Event_Ticketing;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int maxAttempts;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int maxAttempts) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void run() {
        int attempts = 0;

        // Continue buying tickets until the pool is empty
        while (ticketPool.getCurrentTicketCount() > 0) {
            synchronized (ticketPool) {
                if (ticketPool.getCurrentTicketCount() > 0) {
                    int ticketsBought = ticketPool.retrieveTickets(customerRetrievalRate);
                    System.out.println(Thread.currentThread().getName() + " bought " + ticketsBought + " tickets. Tickets available: " + ticketPool.getCurrentTicketCount());
                } else {
                    System.out.println(Thread.currentThread().getName() + " found no tickets to buy. Tickets available: " + ticketPool.getCurrentTicketCount());
                }
            }

            attempts++;
            try {
                Thread.sleep(1000); // Simulating retrieval interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
