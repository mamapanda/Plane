/**
 * Utility to help Plane randomly seat airplane passengers
 * 
 * @author Daniel Phan
 * @version 11.11.16
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
                if(!seats[r][c].isReserved() && tryGroupSeat(group, r, c, endRow))
                {
                    System.out.println("Seats reserved.");
                    return;
                }
            }
        }
        System.out.println("Not possible to reserve seats.");
    }
    private boolean tryGroupSeat(Passenger[] group, int currentRow, int currentColumn, int endRow)
    {
        int passengerCount = group.length;
        int finishColumn = 0; //last vacant column in the row
        ArrayList<Seat> possible = new ArrayList<Seat>();
        for(int c = currentColumn; c < 8; c++) //initial check
        {
            if(!seats[currentRow][c].isReserved())
            {
                passengerCount--;
                possible.add(seats[currentRow][c]);
            }
            else{
                finishColumn = c - 1;
                break;
            }
        }
        loop: while(true) 
        {
            currentRow++;
            if(passengerCount <= 0){ //aka there are enough seats
                for(int i = 0; i < group.length; i++){
                    possible.get(i).reserve(group[i]);
                }
                return true;
            }else if(currentRow > endRow){ //aka there are not enough seats
                break;
            }
            ArrayList<Seat> temp = new ArrayList<Seat>();
            int emptyCounter = 0;
            if(passengerCount > finishColumn - currentColumn)
            {
                for(int c = currentColumn; c <= finishColumn; c++)
                {
                    if(!seats[currentRow][c].isReserved())
                    {
                        emptyCounter++;
                    }
                }
            }
        }
        return false;
    }
}