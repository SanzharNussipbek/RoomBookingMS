/**
 *
 * Save class is user for Save command of the system and save data to an output file
 *
 */


import java.io.PrintWriter;

public class Save {

    /** Objects of the other classes */
    private final UserDB userDB = new UserDB();
    private final RoomDB roomDB = new RoomDB();
    private final BookingDB bookingDB = new BookingDB();



    /** Default constructor */
    public Save(){}





    /**
     * This method is used to save users from UserDB to an output file
     * Accepts PrintWriter as a parameter
     * Has an error handling function
     * @param out
     */
    public void saveUsersToFile(PrintWriter out){
        User[] usersArray = userDB.getUsersArray();                                                                     /** Array of users */

        for (int i = 0; i < usersArray.length; i++) {                                                                   /** Print users to the output file */
            out.println(usersArray[i]);
        }

        System.out.print(usersArray.length + " records saved to ");
    }





    /**
     * This method is used to save rooms from RoomDB to an output file
     * Accepts PrintWriter as a parameter
     * @param out
     */
    public void saveRoomsToFile(PrintWriter out){
        Room[]roomsArray = roomDB.getRoomsArray();

        for(int i = 0; i < roomsArray.length; i++){
            out.println(roomsArray[i]);
        }

        System.out.print(roomsArray.length + " records saved to ");
    }





    /**
     * This method is used to save bookings from BookingDB to an output file
     * Accepts PrintWriter as a parameter
     * @param out
     */
    public void saveBookingsToFile(PrintWriter out){
        Booking[]bookingsArray = bookingDB.getBookingsArray();

        for(int i = 0; i < bookingsArray.length; i++){
            out.println(bookingsArray[i]);
        }

        System.out.print(bookingsArray.length + " records saved to ");
    }
}
