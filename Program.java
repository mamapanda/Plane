/**
 * The main class to run the application
 * 
 * @author Daniel Phan / Derek Tang
 * @version 11.11.16
 */

import java.util.Scanner;

public class Program
{
    private static Scanner in;
    private static Plane plane;

    public static void main(String[] args) 
    {
        plane = new Plane();
        in = new Scanner(System.in);
        mainloop: while(true)
        {
            System.out.println("1 - Seat One Automatically");
            System.out.println("2 - Seat One Manually");
            System.out.println("3 - Seat Group");
            System.out.println("4 - Cancel Reservation");
            System.out.println("5 - Randomly Fill a Number of Seats");
            System.out.println("6 - Print Passengers");
            System.out.println("7 - Print Seats");
            System.out.println("8 - Print Plane Layout");
            System.out.println("0 - Exit");
            System.out.print("> ");
            int choice = in.nextInt(); 
            in.nextLine();
            switch(choice)
            {
                case 1:
                    seatOneAutomatically();
                    break;
                case 2:
                    seatOneManually();
                    break;
                case 3:
                    seatGroup();
                    break;
                case 4:
                    cancelReservation();
                    break;
                case 5:
                    System.out.print("Number of seats to fill: ");
                    plane.fillSeatsRandomly(in.nextInt()); 
                    in.nextLine();
                    break;
                case 6: 
                    plane.printPassengers();
                    break;
                case 7:
                    plane.printSeats();
                    break;
                case 8:
                    plane.printLayout();
                    break;
                case 0:
                    break mainloop;
            }
            System.out.println();
        }
    }

    private static void seatOneAutomatically()
    {
        System.out.print("Enter your name: ");
        Passenger p = new Passenger(in.next(), in.next());
        in.nextLine();
        plane.reserveSeatAuto(p, getPreference());
    }
    private static void seatOneManually()
    {
        System.out.print("Enter your name: ");
        Passenger r = new Passenger(in.next(), in.next());
        in.nextLine();
        plane.printLayout();
        System.out.print("Enter the seat: ");
        plane.reserveSeatManual(r, in.next());
        in.nextLine();
    }
    private static void seatGroup()
    {
        System.out.format("How many people are in the group?: ");
        int count = in.nextInt();
        in.nextLine();
        Passenger[] group = new Passenger[count];
        for(int i = 0; i < count; i++)
        {
            System.out.format("Enter the passenger's name: ");
            group[i] = new Passenger(in.next(), in.next());
            in.nextLine();
        }
        String cls = "";
        while(!cls.equals("1") && !cls.equals("e") && !cls.equals("any"))
        {
            System.out.print("Economy or First Class? (e, 1, any): ");
            cls = in.nextLine();
        }
        plane.reserveSeatAutoGroup(group, cls);
    }
    private static void cancelReservation()
    {
        System.out.println();
        System.out.println("1 - Cancel by Name");
        System.out.println("2 - Cancel by Seat");
        System.out.print("> ");
        int cancelChoice = in.nextInt(); 
        in.nextLine();
        if(cancelChoice == 1){
            System.out.print("Enter your name: ");
            plane.cancelReservationByName(in.next(), in.next()); 
            in.nextLine();
        }
        else if(cancelChoice == 2){
            System.out.print("Enter the seat: ");
            plane.cancelReservationBySeat(in.next());
            in.nextLine();
        }
        System.out.println("Reservation cancelled.");
    }
    private static String[] getPreference()
    {
        String[] pref = new String[2];
        String cls = "";
        while(!cls.equals("1") && !cls.equals("e") && !cls.equals("any"))
        {
            System.out.print("Economy or First Class? (e, 1, any): ");
            cls = in.nextLine();
        }
        pref[0] = cls;
        String location = "";
        while(!location.equals("w") && !location.equals("a") && !location.equals("m") && !location.equals("any"))
        {
            System.out.print("Window/Aisle/Middle/Any Preference? (w, a, m, or any): ");
            location = in.nextLine();
        }
        pref[1] = location;
        return pref;
    }
}