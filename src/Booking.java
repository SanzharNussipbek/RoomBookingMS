/**
 *
 * Booking class is used to create booking
 * objects with userID, roomID and time slot.
 * All parameters are of String type
 *
 */


public class Booking {

    /**  Variables for a booking object  */
    private final String userID;
    private final String roomID;
    private final String timeslot;


    /**  Object of BookingDB class  */
    private BookingDB bookingDB = new BookingDB();



    /**
     * Constructor of the class:
     * receives userID, roomID and timeslot as parameters and creates booking object
     * Then it calls addBooking() method from BookingDB class to add created booking into the array of bookings
     * @param userID
     * @param roomID
     * @param timeslot
     */
    public Booking(String userID, String roomID, String timeslot){
        this.userID = userID;
        this.roomID = roomID;
        this.timeslot = timeslot;
        bookingDB.addBooking(this);
    }


    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return userID + " " + roomID + " " + timeslot;
    }


    /**
     * equals method compares "this" booking with the booking received
     * as an argument and compares their userID, roomID and time slots
     * @param booking
     * @return
     */
    public boolean equals(Booking booking) {
        return this.getUserID().equalsIgnoreCase(booking.getUserID())
                && this.getRoomID().equalsIgnoreCase(booking.getRoomID())
                && this.getTimeslot().equalsIgnoreCase(booking.getTimeslot());
    }



    /**
     * Getters for booking parameters
     * @return
     */
    public String getUserID() { return userID; }


    public String getRoomID() { return roomID; }


    public String getTimeslot() { return timeslot; }
}
