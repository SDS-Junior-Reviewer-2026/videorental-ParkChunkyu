package com.videorental;

import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;
    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        String result = getStatementHeader();
        result += getRentalLineReport();
        result += getStatementFooter();

        return result;
    }

    private String getStatementHeader() {
        return "Rental Record for " + getName() + "\n";
    }

    private String getRentalLineReport() {
        String result = "";
        for (Rental each : rentals) {
            result += "\t" + each.getCharge() + "(" + each.getMovie().getTitle() + ")" + "\n";
        }
        return result;
    }

    private String getStatementFooter() {
        String result = "Amount owed is " + getTotalAmount() + "\n";
        result += "You earned " + getFrequentRenterPoints() + " frequent renter pointers";
        return result;
    }

    private double getTotalAmount() {
        double totalAmount = 0;
        for (Rental rental : rentals) {
            totalAmount += rental.getCharge();
        }
        return totalAmount;
    }

    private int getFrequentRenterPoints() {
        int frequentRenterPoints = 0;
        for (Rental rental : rentals) {
            frequentRenterPoints += rental.getFrequentRenterPointsFor();
        }
        return frequentRenterPoints;
    }
}