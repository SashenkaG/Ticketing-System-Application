package com.sashenka.OOP.CW.Event_Ticketing;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    //Getters and setters
    public int getTotalTickets() {
        return totalTickets;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
    //Saving the configuration to JSON
    public void saveToJson(String filename){
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(this,writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Load the configuration from JSON
    public static Configuration loadFromJson(String filename){
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

