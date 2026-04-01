package com.videorental;

import java.util.ArrayList;
import java.util.Iterator;
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
        Iterator<Rental> iterator = rentals.iterator();
        String result = "Rental Record for " + getName() + "\n";

        while (iterator.hasNext()) {
            Rental each = (Rental) iterator.next();

            // show figures
            result += "\t" + String.valueOf(each.getCharge()) + "(" + each.getMovie().getTitle() + ")" + "\n";
        }

        result += "Amount owed is " + getTotalAmount() + "\n";
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
            frequentRenterPoints += getFrequentRenterPointsFor(rental);
        }
        return frequentRenterPoints;
    }

    private int getFrequentRenterPointsFor(Rental rental) {
        // add bonus for a two day new release rental
        if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && rental.getDaysRented() > 1)
            return 2;
        else
            return 1;
    }
}