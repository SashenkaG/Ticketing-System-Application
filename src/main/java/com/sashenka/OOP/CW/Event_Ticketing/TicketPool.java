package com.sashenka.OOP.CW.Event_Ticketing;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class TicketPool {
    private final int maxTicketCapacity;
    private int currentTicketCount;
    private final List<String> transactionLogs; // Synchronized list for logging

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.currentTicketCount = 0;
        this.transactionLogs = Collections.synchronizedList(new ArrayList<>());
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
            currentTicketCount = maxTicketCapacity;
        }
        logTransaction("Added " + count + " tickets. Current count: " + currentTicketCount);
        System.out.println("TicketPool: " + count + " tickets added. Current count: " + currentTicketCount);
    }

    // Method to retrieve tickets, reducing the pool count accordingly
    public synchronized int retrieveTickets(int count) {
        int ticketsToRetrieve = Math.min(count, currentTicketCount);
        currentTicketCount -= ticketsToRetrieve;
        logTransaction("Retrieved " + ticketsToRetrieve + " tickets. Current count: " + currentTicketCount);
        System.out.println("TicketPool: " + ticketsToRetrieve + " tickets bought. Current count: " + currentTicketCount);
        return ticketsToRetrieve;
    }

    // Add log entry to synchronized list
    private void logTransaction(String message) {
        transactionLogs.add(message);
    }

    // Retrieve logs safely
    public void printTransactionLogs() {
        synchronized (transactionLogs) {
            System.out.println("Transaction Logs:");
            for (String log : transactionLogs) {
                System.out.println(log);
            }
        }
    }
}
