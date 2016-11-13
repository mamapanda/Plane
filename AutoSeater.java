/**
 * Utility to help Plane randomly seat airplane passengers
 * 
 * @author Daniel Phan
 * @version 11.13.16
 */

import java.util.ArrayList;
import java.util.Random;

public class AutoSeater
{
    private Random random; //the random object to generate random numbers
    private Seat[][] seats; //the array of seats in the airplane

    /**
     * Constructs a new instance of AutoSeater with the given seat array
     * (Postcondition: random and seats are initialized)
     * @param seats the seats of the airplane
     * (Precondition: random and seats are declared)
     */
    public AutoSeater(Seat[][] seats)
    {
        random = new Random();
        this.seats = seats; //actually collection of pointers
    }

    /**
     * Seats one passenger in any random vacant seat
     * (Postcondition: passenger is seated, is possible)
     * @param passenger the passenger to seat
     * (Precondition: passenger != null)
     */
    public void seatOne(Passenger passenger)
    {
        Seat seat = null;
        int row, column;
        do
        {
            row = random.nextInt(seats.length);
            column = random.nextInt(seats[0].length);
            seat = seats[row][column];
        }while(seat.isReserved());
        seat.reserve(passenger); 
    }

    /**
     * Seats a person based on their preferences
     * (Postcondition: the passenger is seated, if possible)
     * @param passenger the passenger to seat
     * @param pref the passenger's seating preferences
     * (Precondition: passenger != null && pref != null)
     */
    public void seatOnePref(Passenger passenger, String[] pref)
    {
        int row = -1;
        int column = -1;
        Seat seat = null;
        for(int i = 0; i < 20000; i++)
        {
            if(pref[0].equals("1"))
                row = random.nextInt(4);
            else if(pref[0].equals("e"))
                row = random.nextInt(8) + 4;
            else if(pref[0].equals("any"))
                row = random.nextInt(12);
            switch(pref[1])
            {
                case "w":
                    column = random.nextInt(2) * 7;
                    break;
                case "a":
                    int[] aisles = {1, 2, 5, 6};
                    column = aisles[random.nextInt(4)];
                    break;
                case "m":
                    column = random.nextInt(2) + 3;
                    break;
                case "any":
                    column = random.nextInt(8);
                    break;
            }
            seat = seats[row][column];
            if(!seat.isReserved())
            {
                seat.reserve(passenger);
                System.out.format("Seat %s reserved.\n", seat.getLocation());
                return;
            }
        }
        System.out.println("All possible seats are already taken.");
    }

    /**
     * Automatically reserves a seat for group, if possible
     * (Postcondition: the seats are reserved, if possible)
     * @param group the array of passengers to be seated
     * (Precondition: group.length > 0 && cls == "1" || cls == "e" || cls == "any")
     */
    public void seatGroup(Passenger[] group, String cls)
    {
        if(group.length == 0) return;
        int startRow, endRow;
        switch(cls)
        {
            case "1":
                startRow = 0;
                endRow = 3;
                break;
            case "e":
                startRow = 4;
                endRow = 11;
                break;
            case "any":
                startRow = 0;
                endRow = 11;
                break;
            default:
                System.out.println("Invalid preference.");
                return;
        }
        for(int r = startRow; r <= endRow; r++)
        {
            for(int c = 0; c < 8; c++)
            {
                if(!seats[r][c].isReserved() && (tryGroupSeat(group, r, c, endRow, 1) || tryGroupSeat(group, r, c, endRow, -1)))
                {
                    return;
                }
            }
        }
        System.out.println("Not possible to reserve seats.");
    }

    /**
     * Seats a group starting at a given seat location and snake direction, if possible
     * (Postcondition: the group is seated, if possible)
     * @param group the group of passengers to be seated
     * @param currentRow the row of the starting seat  
     * @param currentColumn the column of the starting seat
     * @param endRow the last possible row to check
     * @param direction the direction to start the initial snake
     * @return true if the seating was successful, false otherwise
     * (Precondition: group.length > 0 && currentRow, currentColumn, and endRow are between 0 and 11, inclusive && direction != 0)
     */
    private boolean tryGroupSeat(Passenger[] group, int currentRow, int currentColumn, int endRow, int direction)
    {
        int columnIncrement = (int)Math.signum(direction); //just in case
        ArrayList<Seat> possibleSeats = new ArrayList<Seat>();
        int r = currentRow;
        int c = currentColumn;
        while(possibleSeats.size() < group.length)
        {
            if(c < 0 || c > 7 || seats[r][c].isReserved())
            {
                c -= columnIncrement; //because we're 1 away from empty seat
                r++;
                columnIncrement *= -1;
                if(r > endRow || seats[r][c].isReserved()) return false;
            }
            else
            {
                possibleSeats.add(seats[r][c]);
                c += columnIncrement;
            }
        }
        if(possibleSeats.size() >= group.length)
        {
            for(int i = 0; i < group.length; i++)
            {
                possibleSeats.get(i).reserve(group[i]);
                System.out.format("Seat %s reserved\n", possibleSeats.get(i).getLocation());   
            }
            return true;
        }
        return false; //aka the code fucked up bc theoretically it should never get here
    }
}