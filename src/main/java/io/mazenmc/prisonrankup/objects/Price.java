package io.mazenmc.prisonrankup.objects;

public class Price {

    private String stringRepresentation;
    private double price;

    public Price(String stringRepresentation) {
        try{
            price = Double.parseDouble(stringRepresentation);
        }catch(NumberFormatException ex) {
            throw new IllegalArgumentException("String " + stringRepresentation + " is not a numeric value!");
        }

        this.stringRepresentation = stringRepresentation;
    }

    public double getValue() {
        return price;
    }

    @Override
    public String toString() {
        return this.stringRepresentation;
    }
}
