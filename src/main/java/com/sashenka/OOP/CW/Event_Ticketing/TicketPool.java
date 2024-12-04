package com.sashenka.OOP.CW.Event_Ticketing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private int maximumTicketCapacity;
    private List<Ticket> ticketQueue; // Using synchronizedList for thread safety

    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = Collections.synchronizedList(new ArrayList<>());
    }

    // Vendor who is the Producer will call the addTicket() method
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maximumTicketCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace(); // For command line interface (CLI)
                throw new RuntimeException(e.getMessage()); // For Client-Server application
            }
        }
        this.ticketQueue.add(ticket);
        notifyAll(); // Notify all the waiting threads
        System.out.println("Ticket added by - " + Thread.currentThread().getName() + " - current size is - " + ticketQueue.size());
    }

    // Customer who is the Consumer will call the buyTicket() method
    public synchronized Ticket removeTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait(); // If queue is empty, add Customer to waiting status
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        Ticket ticket = ticketQueue.remove(0); // Remove the first element
        notifyAll();
        System.out.println("Ticket bought by - " + Thread.currentThread().getName() + " - current size is - " + ticketQueue.size() + " - Ticket is - " + ticket);
        return ticket;
    }
}
