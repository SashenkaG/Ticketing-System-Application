package com.sashenka.OOP.CW.Event_Ticketing;

public class TicketPool {
    private final int maxTicketCapacity;
    private int currentTicketCount;

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.currentTicketCount = 0;
    }

    public synchronized int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public synchronized int getCurrentTicketCount() {
        return currentTicketCount;
    }

    // Method to add tickets, ensuring the count never exceeds max capacity
    public synchronized void addTickets(int count) {
        currentTicketCount += count;
        if (currentTicketCount > maxTicketCapacity) {
            currentTicketCount = maxTicketCapacity;  // Ensure it doesn't exceed max capacity
        }
        System.out.println("TicketPool: " + count + " tickets added. Current count: " + currentTicketCount);
    }

    // Method to retrieve tickets, reducing the pool count accordingly
    public synchronized int retrieveTickets(int count) {
        int ticketsToRetrieve = Math.min(count, currentTicketCount);
        currentTicketCount -= ticketsToRetrieve;
        System.out.println("TicketPool: " + ticketsToRetrieve + " tickets bought. Current count: " + currentTicketCount);
        return ticketsToRetrieve;
    }
}
