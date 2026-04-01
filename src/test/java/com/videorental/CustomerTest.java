package com.videorental;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    public static final String NAME = "NAME_NOT_IMPORTANT";
    public static final String TITLE = "TITLE_NOT_IMPORTANT";

    Customer customer = new Customer(NAME);

    private Rental createRentalFor(int daysRented, int priceCode) {
        Movie movie = getMovie(priceCode);
        return new Rental(movie, daysRented);
    }

    private Movie getMovie(int priceCode) {
        switch (priceCode) {
            case Movie.REGULAR:
                return new RegularMovie(TITLE);
            case Movie.NEW_RELEASE:
                return new NewReleaseMovie(TITLE);
            case Movie.CHILDRENS:
                return new ChildrenMovie(TITLE);
            default:
                return new Movie(TITLE, priceCode);
        }
    }

    @Test
    public void setPriceCodeForMovie() {
        Movie movie = new Movie(TITLE, Movie.NEW_RELEASE);
        assertThat(movie.getPriceCode()).isEqualTo(Movie.NEW_RELEASE);

        movie.setPriceCode(Movie.REGULAR);
        assertThat(movie.getPriceCode()).isEqualTo(Movie.REGULAR);
    }

    @Test
    public void returnNewCustomer() {
        assertThat(customer).isNotNull();
    }

    @Test
    public void statementForNoRental() {
        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n"
                + "Amount owed is 0.0\n"
                + "You earned 0 frequent renter pointers");
    }

    @Test
    public void statementForRegularMovieRentalForLessThan3Days() {
        Rental rental = createRentalFor(2, Movie.REGULAR);
        customer.addRental(rental);

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t2.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 2.0\n"
                + "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForRegularMovieRentalForMoreThan2Days() {
        Rental rental = createRentalFor(3, Movie.REGULAR);
        customer.addRental(rental);

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t3.5(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 3.5\n"
                + "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForNewReleaseMovie() {
        Rental rental = createRentalFor(1, Movie.NEW_RELEASE);
        customer.addRental(rental);

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t3.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 3.0\n"
                + "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForChildrenMovieRentalMoreThan3Days() {
        Rental rental = createRentalFor(4, Movie.CHILDRENS);
        customer.addRental(rental);

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t3.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 3.0\n"
                + "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForChildrenMovieRentalLessThan4Days() {
        Rental rental = createRentalFor(3, Movie.CHILDRENS);
        customer.addRental(rental);

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t1.5(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 1.5\n"
                + "You earned 1 frequent renter pointers");
    }

    @Test
    public void statementForNewReleaseMovieRentalMoreThan1Day() {
        Rental rental = createRentalFor(2, Movie.NEW_RELEASE);
        customer.addRental(rental);

        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t6.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 6.0\n"
                + "You earned 2 frequent renter pointers");
    }

    @Test
    public void statementForFewMovieRental() {
        Movie regularMovie = new Movie(TITLE, Movie.REGULAR);
        Movie newReleaseMovie = new Movie(TITLE, Movie.NEW_RELEASE);
        Movie childrensMovie = new Movie(TITLE, Movie.CHILDRENS);
        customer.addRental(new Rental(regularMovie, 1));
        customer.addRental(new Rental(newReleaseMovie, 4));
        customer.addRental(new Rental(childrensMovie, 4));
        assertThat(customer.statement()).isEqualTo("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t2.0(TITLE_NOT_IMPORTANT)\n"
                + "\t12.0(TITLE_NOT_IMPORTANT)\n"
                + "\t3.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 17.0\n"
                + "You earned 4 frequent renter pointers");
    }
}
