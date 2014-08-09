/**
 PrisonRankup, the most feature-packed rankup plugin out there.
 Copyright (C) 2014 Mazen K.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
