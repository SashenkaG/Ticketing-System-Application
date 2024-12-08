package com.sashenka.OOP.CW.Event_Ticketing;

public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;
    private boolean hasReachedMaxCapacity = false; // Flag to track if max capacity has been reached

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        int ticketsAdded = 0;

        while (ticketsAdded < totalTickets) {
            synchronized (ticketPool) {
                // Only add tickets if max capacity hasn't been reached yet
                if (!hasReachedMaxCapacity) {
                    int availableCapacity = ticketPool.getMaxTicketCapacity() - ticketPool.getCurrentTicketCount();
                    if (availableCapacity > 0) {
                        int ticketsToAdd = Math.min(ticketReleaseRate, totalTickets - ticketsAdded);
                        ticketsToAdd = Math.min(ticketsToAdd, availableCapacity);
                        ticketPool.addTickets(ticketsToAdd);
                        ticketsAdded += ticketsToAdd;
                        System.out.println(Thread.currentThread().getName() + " added " + ticketsToAdd + " tickets. Tickets available: " + ticketPool.getCurrentTicketCount());
                    }

                    // After adding tickets, check if max capacity is reached
                    if (ticketPool.getCurrentTicketCount() == ticketPool.getMaxTicketCapacity()) {
                        hasReachedMaxCapacity = true;
                        System.out.println("Ticket pool has reached maximum capacity.");
                    }
                }
            }

            try {
                Thread.sleep(1000); // Simulating ticket release interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
