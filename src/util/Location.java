/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2002-2006 College Entrance Examination Board 
 * (http://www.collegeboard.com).
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Alyce Brady
 * @author Chris Nevison
 * @author APCS Development Committee
 * @author Cay Horstmann
 */

/**
 * A <code>Location</code> object represents the row and column of a location
 * in a two-dimensional grid. <br />
 * The API of this class is testable on the AP CS A and AB exams.
 */
 package util;
public class Location implements Comparable
{
    private int row; // row location in grid
    private int col; // column location in grid

    /**
     * Constructs a location with given row and column coordinates.
     * @param r the row
     * @param c the column
     */
    public Location(int r, int c)
    {
        row = r;
        col = c;
    }

    /**
     * Gets the row coordinate.
     * @return the row of this location
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Gets the column coordinate.
     * @return the column of this location
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Indicates whether some other <code>Location</code> object is "equal to"
     * this one.
     * @param other the other location to test
     * @return <code>true</code> if <code>other</code> is a
     * <code>Location</code> with the same row and column as this location;
     * <code>false</code> otherwise
     */
    public boolean equals(Object other)
    {
        if (!(other instanceof Location))
            return false;

        Location otherLoc = (Location) other;
        return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
    }

    /**
     * Generates a hash code.
     * @return a hash code for this location
     */
    public int hashCode()
    {
        return getRow() * 3737 + getCol();
    }
	 public Location add(int row, int col)
	 {
	     return new Location(getRow()+row,getCol()+col);
	 }
    /**
     * Compares this location to <code>other</code> for ordering. Returns a
     * negative integer, zero, or a positive integer as this location is less
     * than, equal to, or greater than <code>other</code>. Locations are
     * ordered in row-major order. <br />
     * (Precondition: <code>other</code> is a <code>Location</code> object.)
     * @param other the other location to test
     * @return a negative integer if this location is less than
     * <code>other</code>, zero if the two locations are equal, or a positive
     * integer if this location is greater than <code>other</code>
     */
    public int compareTo(Object other)
    {
        Location otherLoc = (Location) other;
        if (getRow() < otherLoc.getRow())
            return -1;
        if (getRow() > otherLoc.getRow())
            return 1;
        if (getCol() < otherLoc.getCol())
            return -1;
        if (getCol() > otherLoc.getCol())
            return 1;
        return 0;
    }

    /**
     * Creates a string that describes this location.
     * @return a string with the row and column of this location, in the format
     * (row, col)
     */
    public String toString()
    {
        return "(" + getRow() + ", " + getCol() + ")";
    }
}
