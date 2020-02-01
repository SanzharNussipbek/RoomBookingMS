/**
 *
 * BookingDB class is user to store array of bookings and manipulate them:
 * Adding, deleting, printing and checking availability of rooms and time slots
 *
 */

public class BookingDB {

    private static Booking[] bookingsArray = new Booking[0];                                                            /**  Array of bookings with initial size 0  */
    private static Booking[] userBookings = new Booking[0];                                                             /**  Array of bookings with the same userID with initial size 0  */
    private static Booking[] roomBookings = new Booking[0];                                                             /**  Array of bookings with the same roomID with initial size 0  */

    private final RoomDB roomDB = new RoomDB();
    private final UserDB userDB = new UserDB();


    public BookingDB(){}                                                                                                /** Default constructor of the class  */



    /**
     * addBooking method receives booking object and adds it to the array of booking appending the array
     * This code uses the same logic as in the Token class we made during the lectures
     * @param booking
     */
    public void addBooking(Booking booking){

        Booking[] newBookingsArray = new Booking[bookingsArray.length+1];                                               /** Creating new array with size of bookingsArray.length + 1 */

        for(int i = 0; i < bookingsArray.length; i++)                                                                   /** Copying all array elements into the new array */
            newBookingsArray[i] = bookingsArray[i];

        newBookingsArray[newBookingsArray.length - 1] = booking;                                                        /** Saving received booking to the new array */

        bookingsArray = newBookingsArray;                                                                               /** Copying all new array elements back to the original array */

    }





    /**   listBooking method prints out all bookings from the array   */
    public void listBookings(){
        for(int i = 0; i < bookingsArray.length; i++)
            System.out.println(bookingsArray[i]);
    }





    /**
     * deleteBooking method receives booking object
     * and deletes it from the array by creating a new array without this booking
     * @param booking
     */
    public void deleteBooking(Booking booking){
        Booking[] newBookingsArray = new Booking[bookingsArray.length - 1];                                             /** Creating new array with size of bookingsArray.length - 1 */

        int j = 0;                                                                                                      /** Index indicator for newBookingsArray[] */

        for(int i = 0; i < bookingsArray.length; i++){                                                                  /** Copying all array elements into the new array */
            if(!booking.equals(bookingsArray[i]))
                newBookingsArray[j++] = bookingsArray[i];                                                               /** Do not copy the input booking to the new array */
        }

        bookingsArray = newBookingsArray;                                                                               /** Copying all new array elements back to the original array */
    }





    /**
     * deleteBookingUser is used to delete all the bookings of the user which was deleted
     * It receives userID and checks the number of bookings of that user
     * Then it creates new array of size bookingsArray.length - number of bookings of that user
     * and copies everything to new array without bookings of that user
     * and then copies back
     * @param userID
     */
    public void deleteBookingUser(String userID){
        Booking[] newBookingsArray = new Booking[bookingsArray.length - userAppearNum(userID)];                         /** Creating new array with size of bookingsArray.length - No. og bookings of that user */
        int j = 0;
        for(int i = 0; i < bookingsArray.length; i++){                                                                  /** Copying all array elements into the new array */
            if(!userID.equalsIgnoreCase(bookingsArray[i].getUserID()))                                                  /** Do not copy the bookings of that particular user to the new array */
                newBookingsArray[j++] = bookingsArray[i];
        }
        bookingsArray = newBookingsArray;                                                                               /** Copying all new array elements back to the original array */
    }






    /**
     * userAppearNum value returning method returns
     * the number of appearances of the user in the bookings array
     * @param userID
     * @return
     */
    public int userAppearNum(String userID){
        int appearNum = 0;
        for(int i = 0; i < bookingsArray.length; i++){
            if(userID.equalsIgnoreCase(bookingsArray[i].getUserID())){                                                  /**  Check if the userID of that booking is equal to the input userID  */
                appearNum++;
            }
        }

        return appearNum;                                                                                               /**  Return the number of appearances to the deleteBookingUser method  */
    }




    /**
     * deleteBookingRoom is used to delete all the bookings of the room which was deleted
     * It receives roomID and checks the number of bookings of that room
     * Then it creates new array of size bookingsArray.length - number of bookings of that room
     * and copies everything to new array without bookings of that room
     * and then copies back
     * @param roomID
     */
    public void deleteBookingRoom(String roomID){
        Booking[] newBookingsArray = new Booking[bookingsArray.length - roomAppearNum(roomID)];                         /** Creating new array with size of bookingsArray.length - No. og bookings of that room */
        int j = 0;
        for(int i = 0; i < bookingsArray.length; i++){                                                                  /** Copying all array elements into the new array */
            if(!roomID.equalsIgnoreCase(bookingsArray[i].getRoomID()))                                                  /** Do not copy the bookings of that particular room to the new array */
                newBookingsArray[j++] = bookingsArray[i];
        }
        bookingsArray = newBookingsArray;                                                                               /** Copying all new array elements back to the original array */
    }




    /**
     * roomAppearNum value returning method returns
     * the number of appearances of the room in the bookings array
     * @param roomID
     * @return
     */
    public int roomAppearNum(String roomID){
        int appearNum = 0;
        for(int i = 0; i < bookingsArray.length; i++){
            if(roomID.equalsIgnoreCase(bookingsArray[i].getRoomID())){                                                  /**  Check if the roomID of that booking is equal to the input roomID  */
                appearNum++;
            }
        }

        return appearNum;                                                                                               /**  Return the number of appearances to the deleteBookingRoom method  */
    }






    /**
     * hasProblems method returns true if booking with specified parameters has problems
     * It is true if: user or room doesn't exist, time slot is not in valid format,
     * booking is not available or user has another booking on that time
     * false if booking parameters have no problems
     * @param mainUser
     * @param commandLineTokens
     * @return
     */
    public boolean hasProblems(User mainUser, String[]commandLineTokens){
        if(mainUser.isAdmin()){
            if(!userDB.exists(commandLineTokens[2])) {                                                                  /** Check if specified user exists */
                System.out.println("Failed to add booking (" + commandLineTokens[2]
                        + ", " + commandLineTokens[3] + ", " + commandLineTokens[4]
                        + ") -- User (" + commandLineTokens[2] + ") not found");
                return true;
            }

            else if(!roomDB.exists(commandLineTokens[3])) {                                                             /** Check if specified room exists */
                System.out.println("Failed to add booking (" + commandLineTokens[2]
                        + ", " + commandLineTokens[3] + ", " + commandLineTokens[4]
                        + ") -- Room (" + commandLineTokens[3] + ") not found");
                return true;
            }
            else if(commandLineTokens[4].length() != 3) {                                                               /** Check if specified time slot is 3 characters long */
                System.out.println("Failed to add booking (" + commandLineTokens[2]
                        + ", " + commandLineTokens[3] + ", " + commandLineTokens[4]
                        + ") -- Corrupted time slot (" + commandLineTokens[4] + ")");
                return true;
            }

            else if(!validDay(commandLineTokens[4])) {                                                                  /** Check if specified time slot has valid day */
                System.out.println("Failed to add booking (" + commandLineTokens[2]
                        + ", " + commandLineTokens[3] + ", " + commandLineTokens[4]
                        + ") -- Invalid day for time slot (" + commandLineTokens[4].charAt(0) + ")");
                return true;
            }

            else if(!validTime(commandLineTokens[4])) {                                                                 /** Check if specified time slot has valid hour */
                System.out.println("Failed to add booking (" + commandLineTokens[2]
                        + ", " + commandLineTokens[3] + ", " + commandLineTokens[4]
                        + ") -- Invalid hour for time slot (" + commandLineTokens[4].charAt(1)
                        + commandLineTokens[4].charAt(2) + ")");
                return true;
            }

            else if (exists(commandLineTokens[2], commandLineTokens[3], commandLineTokens[4])) {                        /** Check if booking with the same parameters already exists */
                System.out.println("Failed to add booking (" + commandLineTokens[2]
                        + ", " + commandLineTokens[3] + ", " + commandLineTokens[4]
                        + ") -- Duplicated booking record");
                return true;
            }

            else if (hasAnotherBooking(commandLineTokens[2], commandLineTokens[4])) {                                   /** Check if that user has another booking on that time slot */
                System.out.println("Failed to add booking (" + commandLineTokens[2]
                        + ", " + commandLineTokens[3] + ", " + commandLineTokens[4]
                        + ") -- User (" + commandLineTokens[2] + ") trying to book multiple rooms for "
                        + commandLineTokens[4].toUpperCase() + ".\n");
                return true;
            }

            else if (!isAvailable(commandLineTokens[3], commandLineTokens[4])) {                                        /** Check if the room is available on that time slot */
                System.out.println("Failed to add booking (" + commandLineTokens[2]
                        + ", " + commandLineTokens[3] + ", " + commandLineTokens[4]
                        + ") -- Room (" + commandLineTokens[3] + ") not available for "
                        + commandLineTokens[4].toUpperCase() + ".");
                return true;
            }

        }else{

            if(!roomDB.exists(commandLineTokens[1])) {                                                                  /** Check if specified room exists */
                System.out.println("Failed to add booking (" + mainUser.getUserID()
                        + ", " + commandLineTokens[1] + ", " + commandLineTokens[2]
                        + ") -- Room (" + commandLineTokens[1] + ") not found");
                return true;
            }
            else if(commandLineTokens[2].length() != 3) {                                                               /** Check if specified time slot is 3 characters long */
                System.out.println("Failed to add booking (" + mainUser.getUserID()
                        + ", " + commandLineTokens[1] + ", " + commandLineTokens[2]
                        + ") -- Corrupted time slot (" + commandLineTokens[2] + ")");
                return true;
            }

            else if(!validDay(commandLineTokens[2])) {                                                                  /** Check if specified time slot has valid day */
                System.out.println("Failed to add booking (" + mainUser.getUserID()
                        + ", " + commandLineTokens[1] + ", " + commandLineTokens[2]
                        + ") -- Invalid day for time slot (" + commandLineTokens[2].charAt(0) + ")");
                return true;
            }

            else if(!validTime(commandLineTokens[2])) {                                                                 /** Check if specified time slot has valid hour */
                System.out.println("Failed to add booking (" + mainUser.getUserID()
                        + ", " + commandLineTokens[1] + ", " + commandLineTokens[2]
                        + ") -- Invalid hour for time slot (" + commandLineTokens[2].charAt(1)
                        + commandLineTokens[2].charAt(2) + ")");
                return true;
            }

            else if (exists(mainUser.getUserID(), commandLineTokens[1], commandLineTokens[2])) {                        /** Check if booking with the same parameters already exists */
                System.out.println("Failed to add booking (" + mainUser.getUserID()
                        + ", " + commandLineTokens[1] + ", " + commandLineTokens[2]
                        + ") -- Duplicated booking record");
                return true;
            }

            else if (hasAnotherBooking(mainUser.getUserID(), commandLineTokens[2])) {                                   /** Check if the mainUser has another booking on that time slot */
                System.out.println("Failed to add booking (" + mainUser.getUserID()
                        + ", " + commandLineTokens[1] + ", " + commandLineTokens[2]
                        + ") -- User (" + mainUser.getUserID() + ") trying to book multiple rooms for "
                        + commandLineTokens[4].toUpperCase() + ".\n");
                return true;
            }

            else if (!isAvailable(commandLineTokens[1], commandLineTokens[2])) {                                        /** Check if the room is available on that time slot */
                System.out.println("Failed to add booking (" + mainUser.getUserID()
                        + ", " + commandLineTokens[1] + ", " + commandLineTokens[2]
                        + ") -- Room (" + mainUser.getUserID() + ") not available for "
                        + commandLineTokens[2].toUpperCase() + ".");
                return true;
            }

        }

        return false;
    }





    public boolean loadHasProblems(String[]bookingInfo, int lineCount, String filename){
        if(!userDB.exists(bookingInfo[0].toLowerCase())) {                                                              /** Check if specified user exists */
            System.out.print("\n" + filename + ".loadDB: error adding record on line "
                    + (lineCount + 1) + " of " + filename + " -- User ("
                    + bookingInfo[0] + ") not found\n");
            return true;
        }

        else if(!roomDB.exists(bookingInfo[1])) {                                                                       /** Check if specified room exists */
            System.out.print("\n" + filename + ".loadDB: error adding record on line "
                    + (lineCount + 1) + " of " + filename + " -- Room ("
                    + bookingInfo[1] + ") not found\n");
            return true;
        }
        else if(bookingInfo[2].length() != 3) {                                                                         /** Check if specified time slot is 3 characters long */
            System.out.print("\n" + filename + ".loadDB: error adding record on line "
                    + (lineCount + 1) + " of " + filename + " -- Corrupted time slot ("
                    + bookingInfo[2] + "\n");
            return true;
        }

        else if(!validDay(bookingInfo[2])) {                                                                            /** Check if specified time slot has valid day */
            System.out.print("\n" + filename + ".loadDB: error adding record on line "
                    + (lineCount + 1) + " of " + filename + " -- Invalid day for time slot ("
                    + bookingInfo[2].charAt(0) + ")\n");
            return true;
        }

        else if(!validTime(bookingInfo[2])) {                                                                           /** Check if specified time slot has valid hour */
            System.out.print("\n" + filename + ".loadDB: error adding record on line "
                    + (lineCount + 1) + " of " + filename + " -- Invalid hour for time slot ("
                    + bookingInfo[2].charAt(1)
                    + bookingInfo[2].charAt(2) + ")\n");
            return true;
        }

        else if (exists(bookingInfo[0], bookingInfo[1], bookingInfo[2])) {                                              /** Check if booking with the same parameters already exists */
            System.out.print("\n" + filename + ".loadDB: error adding record on line "
                    + (lineCount + 1) + " of " + filename + ") -- Duplicated booking record\n");
            return true;
        }

        else if (hasAnotherBooking(bookingInfo[0], bookingInfo[2])) {                                                   /** Check if that user has another booking on that time slot */
            System.out.print("\n" + filename + ".loadDB: error adding record on line "
                    + (lineCount + 1) + " of " + filename + " -- User (" + bookingInfo[0] + ") trying to book multiple rooms for "
                    + bookingInfo[2].toUpperCase() + ".\n");
            return true;
        }

        else if (!isAvailable(bookingInfo[1], bookingInfo[2])) {                                                        /** Check if the room is available on that time slot */
            System.out.print("\n" + filename + ".loadDB: error adding record on line "
                    + (lineCount + 1) + " of " + filename + " -- Room (" + bookingInfo[1]
                    + ") not available for " + bookingInfo[2].toUpperCase() + ".\n");
            return true;
        }



        return false;
    }




    /**
     * Boolean value returning method exists accepts parameters of booking
     * and checks if there is a booking with the same parameters
     * returns true if there is one
     * returns false if there isn't any
     * @param userID
     * @param roomID
     * @param timeslot
     * @return
     */
    public boolean exists(String userID, String roomID, String timeslot){
        for(int i = 0; i < bookingsArray.length; i++){
            if(userID.equalsIgnoreCase(bookingsArray[i].getUserID())
                    && roomID.equalsIgnoreCase(bookingsArray[i].getRoomID())
                    && timeslot.equalsIgnoreCase(bookingsArray[i].getTimeslot()))
                return true;
        }

        return false;
    }


    /**
     * isAvailable method receives roomID and time slot
     * and checks if this room is available on that time slot
     * returns true if it is available
     * returns false if it isn't
     * @param roomID
     * @param timeslot
     * @return
     */
    public boolean isAvailable(String roomID, String timeslot){
        for(int i = 0; i < bookingsArray.length; i++){
            if(roomID.equalsIgnoreCase(bookingsArray[i].getRoomID())
                    && timeslot.equalsIgnoreCase(bookingsArray[i].getTimeslot()))
                return false;
        }

        return true;
    }




    /**
     * hasAnotherBooking method receives userID and time slot
     * and chekcs if that user has another booking on that time slot
     * returns true if it has
     * returns false it it doesn't have
     * @param userID
     * @param timeslot
     * @return
     */
    public boolean hasAnotherBooking(String userID, String timeslot){
        for(int i = 0; i < bookingsArray.length; i++){
            if(userID.equalsIgnoreCase(bookingsArray[i].getUserID())
                    && timeslot.equalsIgnoreCase(bookingsArray[i].getTimeslot()))
                return true;
        }

        return false;
    }




    /**
     * validDay checks if time slot entered by the mainUser has valid day of the week (e.g. M, T, W, R or F)
     * returns true if it is valid
     * @param timeslot
     * @return
     */
    public boolean validDay(String timeslot){
        return timeslot.toUpperCase().charAt(0) == 'M'
                || timeslot.toUpperCase().charAt(0) == 'T'
                || timeslot.toUpperCase().charAt(0) == 'W'
                || timeslot.toUpperCase().charAt(0) == 'R'
                || timeslot.toUpperCase().charAt(0) == 'F';
    }




    /**
     * validTime checks if time slot entered by the mainUser has valid hours(e.g. 10, 11, 12, 13, 14, 15, 16, 17, 18)
     * returns true if it is valid
     * @param timeslot
     * @return
     */
    public boolean validTime(String timeslot){
        return  timeslot.charAt(1) == '1' && (timeslot.charAt(2) == '0'
                                            || timeslot.charAt(2) == '1'
                                            || timeslot.charAt(2) == '2'
                                            || timeslot.charAt(2) == '3'
                                            || timeslot.charAt(2) == '4'
                                            || timeslot.charAt(2) == '5'
                                            || timeslot.charAt(2) == '6'
                                            || timeslot.charAt(2) == '7'
                                            || timeslot.charAt(2) == '8');
    }





    /**
     * Getter for all bookings of the same user
     * Accepts userID as a parameter and
     * calls addUserBooking method to add a booking with the same userID
     * returns array of bookings of that user
     * @param userID
     * @return
     */
    public Booking[] getAllUserBookings(String userID){
        for(int i = 0; i < bookingsArray.length; i++){
            if(userID.equalsIgnoreCase(bookingsArray[i].getUserID()))
                addUserBooking(bookingsArray[i]);
        }
        return userBookings;
    }





    /**
     * this method adds booking received as a parameter to the array of bookings of the same user
     * @param booking
     */
    public void addUserBooking(Booking booking){
        Booking[] newUserBookings = new Booking[userBookings.length+1];

        for(int i = 0; i < userBookings.length; i++)
            newUserBookings[i] = userBookings[i];

        newUserBookings[newUserBookings.length - 1] = booking;

        userBookings = newUserBookings;
    }


    /**
     * Getter for all bookings of the same room
     * Accepts roomID as a parameter and
     * calls addRoomBooking method to add a booking with the same roomID
     * returns array of bookings of that room
     * @param roomID
     * @return
     */
    public Booking[] getAllRoomBookings(String roomID){
        for(int i = 0; i < bookingsArray.length; i++){
            if(roomID.equalsIgnoreCase(bookingsArray[i].getRoomID()))
                addRoomBooking(bookingsArray[i]);
        }
        return roomBookings;
    }



    /**
     * this method adds booking received as a parameter to the array of bookings of the same room
     * This code uses the same logic as in the Token class we made during the lectures
     * @param booking
     */
    public void addRoomBooking(Booking booking){
        Booking[] newRoomBookings = new Booking[roomBookings.length+1];

        for(int i = 0; i < roomBookings.length; i++)
            newRoomBookings[i] = roomBookings[i];

        newRoomBookings[newRoomBookings.length - 1] = booking;

        roomBookings = newRoomBookings;
    }


    /**
     * Setter for the array of bookings of the same user: used to reset array after getting it in Show class
     * @param newUserBookings
     */
    public void setUserBookings(Booking[] newUserBookings){ this.userBookings = newUserBookings; }





    /**
     * Setter for the array of bookings of the same room: used to reset array after getting it in Show class
     * @param newRoomBookings
     */
    public void setRoomBookings(Booking[] newRoomBookings){ this.roomBookings = newRoomBookings; }




    /**
     * Getter for booking with the same received parameters
     * returns booking with the same parameters
     * returns null if there isn't any room with those parameters
     * @param userID
     * @param roomID
     * @param timeslot
     * @return
     */
    public Booking getBookingByParameters(String userID, String roomID, String timeslot){
        for(int i = 0; i < bookingsArray.length; i++){
            if(userID.equalsIgnoreCase(bookingsArray[i].getUserID())
                    && roomID.equalsIgnoreCase(bookingsArray[i].getRoomID())
                    && timeslot.equalsIgnoreCase(bookingsArray[i].getTimeslot()))
                return bookingsArray[i];
        }

        return null;
    }




    /**
     * Getter for the array of bookings
     * @return
     */
    public static Booking[] getBookingsArray() {
        return bookingsArray;
    }

}
