/**
 * Contains information about a seat
 * 
 * @author Daniel Phan / Derek Tang
 * @version 11.11.16
 */
public class Seat
{
    private boolean isFirstClass; //whether or not the seat is first class
    private final String location; //the seat's location ID
    private Passenger passenger; //the seat's passenger

    /**
     * Constructs a new seat with the given location and class (first/economy)
     * (Postcondition: isFirstClass and location are initialized)
     * @param location the seat's location
     * @param isFirstClass whether or not the seat is first class
     * (Precondition: location follows the form (number + letter)
     */
    public Seat(String location, boolean isFirstClass)
    {
        this.location = location;
        this.isFirstClass = isFirstClass;
        this.passenger = null;
    }

    /**
     * Returns the seat's location
     * (Postcondition: the seat's location is returned)
     * @return the seat's location
     * (Precondition: location follows the form (number + letter))
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Returns the passenger of this seat
     * (Postcondition: passenger is returned)
     * @return the passenger of the seat
     * (Precondition: passenger is defined)
     */
    public Passenger getPassenger()
    {
        return passenger;
    }

    /**
     * Returns whether or not the seat is first class
     * (Postcondition: isFirstClass is returned)
     * @return whether or not the seat is first class
     * (Precondition: isFirstClass is defined)
     */
    public boolean isFirstClass()
    {
        return isFirstClass;
    }

    /**
     * Returns whether or not the seat is reserved
     * (Postcondition: the seat's vacancy status is returned)
     * @return the seat's vacancy status
     * (Precondition: passenger is defined)
     */
    public boolean isReserved()
    {
        return (passenger != null);
    }

    /**
     * Reserves the seat for the passenger if it is vacant
     * (Postcondition: the seat is reserved, if it is vacant)
     * @param passenger the passenger for whom to reserve the seat
     * (Precondition: passenger != null)
     */
    public void reserve(Passenger passenger)
    {
        if(!isReserved())
        {
            this.passenger = passenger;
        }
    }

    /**
     * Unreserves the seat
     * (Postcondition: passenger == null)
     * (Precondition: passenger is declared)
     */
    public void unreserve()
    {
        passenger = null;
    }

    /**
     * Prints the seat info and passenger
     * (Postcondition: the seat info and passenger are printed to the screen)
     * (Precondition: the seat location is initialized)
     */
    public void printInfo()
    {
        if(isReserved())
            System.out.format("Seat: %s; Passenger: %s\n", getLocation(), passenger.getName());
        else
            System.out.format("Seat: %s; Passenger: n/a\n", getLocation());
    }

    /**
     * Prints the seat passenger and seat location
     * (Postcondition: the passenger and seat are printed to the screen)
     * (Precondition: passenger != null && get location is initialized)
     */
    public void printPassengerInfo()
    {
        System.out.format("Name: %s; Seat: %s\n", passenger.getName(), getLocation());
    }
}