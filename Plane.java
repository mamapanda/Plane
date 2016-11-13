/**
 * Simulates an airplane reservation system
 * 
 * @author Daniel Phan / Derek Tang
 * @version 11.11.16
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.*;

public class Plane
{
    private AutoSeater autoseater; //the helper to automatically seat passengers
    private Seat[][] seats; //the plane's seats
    private static final int ROWS = 12; //the number of rows in the plane
    private static final int COLUMNS = 8; //the number of columns in the plane
    private static final char[] COLUMN_LETTERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' }; //contains the letter corresponding to each column

    /**
     * Constructs a new plane
     * (Postcondition: autoseater, seats, and seat's elements are initialized)
     * (Precondition: seats and autoseater are declared, and COLUMN_LETTERS is defined)
     */
    public Plane()
    {
        seats = new Seat[ROWS][COLUMNS];
        for(int r = 0; r < ROWS; r++)
        {
            for(int c = 0; c < COLUMNS; c++)
            {
                seats[r][c] = new Seat("" + (r + 1) + COLUMN_LETTERS[c], (r < 4));
            }
        }
        autoseater = new AutoSeater(seats);
    }

    /**
     * Cancels a seat's reservation given a passenger's name
     * (Postcondition: the given passenger's seat is unreserved)
     * @param fName the passenger's first name
     * @param lName the passenger's last name
     * (Precondition: fName is a first name, and lName is a last name)
     */
    public void cancelReservationByName(String fName, String lName)
    {
        String name = String.format("%s, %s", lName, fName);
        try{
            Stream.of(seats)
                .flatMap(Stream::of)
                .filter(s -> s.isReserved() && s.getPassenger().getName().equals(name))
                .findFirst().get().unreserve();
        } catch(Exception e){ }
    }

    /**
     * Cancels a seat reservation given the seat location
     * (Postcondition: the seat with the given location is unreserved)
     * @param seat the seat's location
     * (Precondition: seat follows the form (number + letter))
     */
    public void cancelReservationBySeat(String seat)
    {
        try{
            Stream.of(seats).flatMap(Stream::of).filter(s -> s.getLocation().equals(seat)).findFirst().get().unreserve();
        } catch(Exception e) { }
    }

    /**
     * Fills a given number of seats with people
     * (Postcondition: the given number of seats filled)
     * @param seatCount the number of seats to fill
     * (Precondition: seatCount >= 0)
     */
    public void fillSeatsRandomly(int seatCount)
    {
        for(int i = 0; i < seatCount; i++)
        {
            autoseater.seatOne(new Passenger("John", "Doe" + i));
        }
    }

    /**
     * Reserves a seat automatically based on the passenger's preferences
     * (Postcondition: a seat is reserved for the passenger, if possible)
     * @param passenger the passenger to reserve a seat for
     * @param pref the passenger's preferences
     * (Precondition: passenger != null, pref contains valid preference values)
     */
    public void reserveSeatAuto(Passenger passenger, String[] pref) //one passenger
    {
        autoseater.seatOnePref(passenger, pref);
    }

    /**
     * Reserves a group of seats automatically based on the given preferences
     * (Postcondition: a group of seats are reserved, if possible)
     * @param group the group of passengers to reserve seats for
     * @param pref the group's preferences
     * (Precondition: group != null, pref contains valid preference values)
     */
    public void reserveSeatAutoGroup(Passenger[] group, String cls)
    {
        autoseater.seatGroup(group, cls);
    }

    /**
     * Reserves a seat for a passenger based on the given seat, if the seat is not reserved
     * (Postcondition: the seat is reserved, if possible)
     * @param passenger the passenger for whom to reserve a seat
     * @param seat the desired seat
     * (Precondition: passenger != null && seat follows the form number + letter)
     */
    public void reserveSeatManual(Passenger passenger, String seat)
    {
        for(int r = 0; r < ROWS; r++)
        {
            for(int c = 0; c < COLUMNS; c++)
            {
                if(seats[r][c].getLocation().equals(seat))
                {
                    if(!seats[r][c].isReserved())
                    {
                        seats[r][c].reserve(passenger);
                        System.out.format("Seat %s reserved.\n", seat);
                    }
                    else
                    {
                        System.out.format("Seat %s is already taken.\n", seat);
                    }
                    return;
                }
            }
        }
    }

    /**
     * Prints the plane's seating layout
     * (Postcondition: the plane's seating layout is printed)
     * (Precondition: seats is initialized)
     */
    public void printLayout()
    {  
        int filledSeats = (int)(Stream.of(seats).flatMap(Stream::of).filter(s -> s.isReserved()).count());
        System.out.format("Occupancy: %d / %d\n", filledSeats, seats.length * seats[0].length);
        System.out.println();
        System.out.print("  ");
        for(int i = 1; i <= 9; i++)
        {
            System.out.format(" %d ", i);
            if(i == 4) System.out.format("  ");
        }
        for(int i = 10; i <= ROWS; i++)
        {
            System.out.format("%d ", i);
        }
        System.out.println();
        for(int c = 0; c < COLUMNS; c++)
        {
            System.out.format("%c ", COLUMN_LETTERS[c]);
            for(int r = 0; r < ROWS; r++)
            {
                System.out.print(seats[r][c].isReserved() ? "[X]" : "[ ]");
                if(r == 3) System.out.format("  ");
            }
            System.out.println();
            if(c == 1 || c == 5) System.out.println();
        }
    }

    /**
     * Prints the plane's passengers and their seats, in order of the passenger's name
     * (Postcondition: the passengers and their seats are printed)
     * (Precondition: seats is initialized)
     */
    public void printPassengers()
    {
        Stream.of(seats)
            .flatMap(Stream::of)
            .filter(s -> s.isReserved())
            .sorted((s1, s2) -> s1.getPassenger().getName().compareTo(s2.getPassenger().getName()))
            .forEach(s -> s.printPassengerInfo());
    }

    /**
     * Prints the seats and their passengers' names
     * (Postcondition: the seats and their passengers are printed)
     * (Precondition: seats is initialized)
     */
    public void printSeats()
    {
        Stream.of(seats).flatMap(Stream::of).forEach(s -> s.printInfo());
    }
}