package com.sashenka.OOP.CW.Event_Ticketing;

public class Customer implements Runnable{
    private TicketPool ticketPool;
    private int customerRetrivelRate;
    private int quantity;

    public Customer(TicketPool ticketPool, int customerRetrivelRate, int quantity) {
        this.ticketPool = ticketPool;
        this.customerRetrivelRate = customerRetrivelRate;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = ticketPool.removeTicket(); // Call method to buyTickets
            System.out.println("Ticket is - " + ticket + " - Customer name is - " + Thread.currentThread().getName());
            try {
                Thread.sleep(customerRetrivelRate * 1000); // Retieving delay
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
