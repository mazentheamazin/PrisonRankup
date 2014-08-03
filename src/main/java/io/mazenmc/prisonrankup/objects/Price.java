package io.mazenmc.prisonrankup.objects;

public class Price {

    private String stringRepresentation;
    private double price;

    /**
     * Constuctor for the Price object
     * @param stringRepresentation The string representation of your price
     */
    public Price(String stringRepresentation) {
        try{
            price = Double.parseDouble(stringRepresentation);
        }catch(NumberFormatException ex) {
            throw new IllegalArgumentException("String " + stringRepresentation + " is not a numeric value!");
        }

        this.stringRepresentation = stringRepresentation;
    }

    /**
     * Gets the double value of said price
     * @return Double value of price
     */
    public double getValue() {
        return price;
    }

    /**
     * Returns a string representation of said price
     * @return A string representation of said price
     */
    @Override
    public String toString() {
        return this.stringRepresentation;
    }
}
