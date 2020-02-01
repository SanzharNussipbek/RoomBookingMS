/**
 *
 * Delete class is used for Delete command of the system: delete user, room or booking
 *
 */

public class Delete {

    /** Objects of the other classes */
    private User user;
    private Room room;
    private Booking booking;
    private final UserDB userDB = new UserDB();
    private final RoomDB roomDB = new RoomDB();
    private final BookingDB bookingDB = new BookingDB();

    public Delete(){}           /** Default constructor of the class */




    /**
     * This method deletes user from the command
     * Accepts array of tokens of the command written by the mainUser
     * @param commandLineTokens
     */
    public void deleteUserFromInput(String[] commandLineTokens){
        if (commandLineTokens.length < 3 || commandLineTokens.length > 3)                                               /** Check if the number of words in the command is correct */
            System.out.println("Invalid number of arguments for Delete User.");
        else {
            user = userDB.getUserByID(commandLineTokens[2]);                                                            /** Get user by his userID */
            if(user == null)                                                                                            /** Check if this user exists and is not null */
                System.out.println("User (" + commandLineTokens[2] + ") does not exist");
            else {
                userDB.deleteUser(user);                                                                                /** Delete user from UserDB class */
                bookingDB.deleteBookingUser(user.getUserID());                                                          /** Delete all bookings of that user */
                System.out.println("User (" + commandLineTokens[2] + ") deleted successfully!");
            }
        }
    }





    /**
     * This method deletes room from the command
     * Accepts array of tokens of the command written by the mainUser
     * @param commandLineTokens
     */
    public void deleteRoomFromInput(String[] commandLineTokens){
        if (commandLineTokens.length != 3)                                                                              /** Check if the number of words in the command is correct */
            System.out.println("Invalid number of arguments for Delete Room.");
        else {
            room = roomDB.getRoomByID(commandLineTokens[2].toUpperCase());                                              /** Get room by his roomID */
            if(room == null)                                                                                            /** Check if this room exists and is not null */
                System.out.println("Room (" + commandLineTokens[2].toUpperCase() + ") does not exist");
            else {
                roomDB.deleteRoom(room);                                                                                /** Delete room from RoomDB class */
                bookingDB.deleteBookingRoom(room.getRoomID());                                                          /** Delete all bookings of that room */
                System.out.println("Room (" + commandLineTokens[2].toUpperCase() + ") deleted successfully!");
            }
        }
    }





    /**
     * This method deletes user from the command
     * Accepts array of tokens of the command written by the mainUser
     * @param commandLineTokens
     * @param mainUser
     */
    public void deleteBookingFromInput(String[] commandLineTokens, User mainUser){
        if(mainUser.isAdmin()){                                                                                         /** Check if mainUser is Admin */
            if (commandLineTokens.length != 5)                                                                          /** Check if the number of words in the command is correct */
                System.out.println("Invalid number of arguments for Add Booking.");
            else {
                if(bookingDB.exists(commandLineTokens[2], commandLineTokens[3], commandLineTokens[4])) {                /** Check if booking exists */
                    booking = bookingDB.getBookingByParameters(commandLineTokens[2],                                    /** Get booking by its parameters */
                            commandLineTokens[3], commandLineTokens[4]);
                    bookingDB.deleteBooking(booking);                                                                   /** Delete booking from the database */
                    System.out.println("Booking (" + commandLineTokens[2] + "," + commandLineTokens[3] +
                                        "," + commandLineTokens[4] + ") deleted successfully");
                }else
                    System.out.println("Cannot find the booking (" + commandLineTokens[2] +
                                        ","+ commandLineTokens[3] + "," + commandLineTokens[4] + ").");
            }
        }else{                                                                                                          /** If the mainUser is not admin */
            if(bookingDB.exists(mainUser.getUserID(), commandLineTokens[1], commandLineTokens[2])) {
                booking = bookingDB.getBookingByParameters(mainUser.getUserID(),
                        commandLineTokens[1], commandLineTokens[2]);
                bookingDB.deleteBooking(booking);
                System.out.println("Booking (" + mainUser.getUserID() + "," + commandLineTokens[1] +
                                    "," + commandLineTokens[2] + ") deleted successfully");
            }else
                System.out.println("Cannot find the booking (" + mainUser.getUserID() +
                                    ","+ commandLineTokens[1] + "," + commandLineTokens[2] + ").");
        }
    }

}
