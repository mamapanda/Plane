/**
 * Contains information about a passenger
 * 
 * @author Daniel Phan / Derek Tang
 * @version 11.11.16
 */
public class Passenger
{
    private String fName; //the passenger's first name
    private String lName; //the passenger's last name

    /**
     * Constructs a new passenger with the given first name and last name
     * (Postcondition: fName and lName are initialized)
     * @param fName the passenger's first name
     * @param lName the passenger's last name
     * (Precondition: fName != null && lName != null)
     */
    public Passenger(String fName, String lName)
    {
        this.fName = fName;
        this.lName = lName;
    }

    /**
     * Returns the passenger name in the format last, first
     * (Postcondition: the passenger's name is returned in the format last, first)
     * @return the passenger name in the format last, first
     * (Precondition: lName and fName are initialized)
     */
    public String getName()
    {
        return String.format("%s, %s", lName, fName);
    }
}