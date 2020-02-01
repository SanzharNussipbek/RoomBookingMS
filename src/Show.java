/**
 *
 * Show class is user for Show command of the system: show bookings of the user and room
 *
 */

public class Show {

    /** Objects of the other classes */
    private final UserDB userDB = new UserDB();
    private final RoomDB roomDB = new RoomDB();
    private final BookingDB bookingDB = new BookingDB();


    /** Default constructor of the class */
    public Show(){}



    /**
     * This method receives a word(userID or roomID) and
     * creates new String of size 11 with this word in the center
     * We need size 11 because each block in the time table
     * has width of 11 spaces and we need roomID or UserID to be in the center of the block
     * @param word
     * @return
     */
    public String justifiedString(String word){
        int spaceNum = 11 - word.length();                                                                              /** Calculate how many empty spaces we need */

        String justifiedWord = "";

        for(int i = 0; i < spaceNum/2; i++)                                                                             /** Assign half of the empty spaces before word */
            justifiedWord += " ";

        justifiedWord += word;                                                                                          /** Add word */

        for(int i = justifiedWord.length(); i < 11; i++)                                                                /** Assign the rest of the empty spaces */
            justifiedWord += " ";

        justifiedWord += "|";

        return justifiedWord;
    }






    /**
     * Method which turns time slot from format M11 to 01 for example
     * The logic is that each day turns into number: M to 0, T to 1, W to 2, R to 3 and F to 4
     * And the nwe take only third character for the next number:
     * because all hours have only different second number: 10, 11, 12, 13, 14, 15, 16, 17, 18
     * and the first number "1" is the same so we don't need it
     * That's how M11 becomes 01, T17 becomes 17 and F14 becomes 44 as an example
     *
     * Method receives String of time slot and returns changed string;
     * @param s
     * @return
     */
    public String toNumFormat(String s){
        String newString = "";

        switch (s.charAt(0)){
            case('M'):
                newString += '0';
                break;

            case('T'):
                newString += '1';
                break;

            case('W'):
                newString += '2';
                break;

            case('R'):
                newString += '3';
                break;

            case('F'):
                newString += '4';
                break;

        }

        newString += s.charAt(2);                                                                                       /** Get second number from the third character of String s */

        return newString;
    }




    /**
     * Method used to show all bookings of the user in the command
     * Accepts userID as a parameter
     * @param userID
     */
    public void showUser(String userID){
        if(!userDB.exists(userID)) {                                                                                    /** Check if a user with that UserID exists in the database */
            System.out.println("Unknown userID (" + userID + ")");
            return;
        }

        Booking[]userBookings = bookingDB.getAllUserBookings(userID);                                                   /** Get all bookings of that user */
        bookingDB.setUserBookings(new Booking[0]);                                                                      /** Set the size of the userBookings in BookingDB class to zero */


        String[] time = new String[]{"10", "11", "12", "13", "14", "15", "16", "17", "18"};                             /** String array with names of hours for printing */


        String[]timeslotNum = new String[userBookings.length];                                                          /** Create new array of time slots in the number format (explanation below) */

        for(int i = 0; i < timeslotNum.length; i++)
            timeslotNum[i] = toNumFormat(userBookings[i].getTimeslot().toUpperCase());                                  /** Turn all time slots of user bookings into number format */


        boolean[][]hours = new boolean[9][5];
        for(int i = 0; i < hours.length; i++){                                                                          /** Create boolean arrays of size 5(five days in a week: M, T, W, R, F) for all hours in a day (9 arrays) */
            hours[i] = new boolean[5];                                                                                  /** Boolean is used to indicate if there is a booking or not: True if there is, False if not */
        }


        for(int i = 0; i < timeslotNum.length; i++){
            hours[timeslotNum[i].charAt(1) - '0'][timeslotNum[i].charAt(0) - '0'] = true;                               /** Assign Bookings by hours and days to all 9 arrays of hours */
        }


        System.out.println("Time Table for user " + userID + "\n");                                                     /** Start printing the time table */

        System.out.println("        Mon         Tue         Wed         Thu         Fri    \n" +
                "   +-----------+-----------+-----------+-----------+-----------+");

        for(int i = 0; i < hours.length; i++){
            printFromArray(hours[i], time[i], userBookings, "user");                                           /** Send arrays one by one to the printing method */
        }

        System.out.println("        Mon         Tue         Wed         Thu         Fri    ");                          /** Finish the time table */
    }


    /**
     * Method used to show all bookings of the room in the command
     * Accepts roomID as a parameter
     * @param roomID
     */
    public void showRoom(String roomID){
        if(!roomDB.exists(roomID)) {                                                                                    /** Check if a room with that RoomID exists in the database */
            System.out.println("Unknown roomID (" + roomID + ")");
            return;
        }

        Booking[]roomBookings = bookingDB.getAllRoomBookings(roomID);                                                   /** Get all bookings of that room */
        bookingDB.setRoomBookings(new Booking[0]);                                                                      /** Set the size of the roomBookings in BookingDB class to zero */


        String[] time = new String[]{"10", "11", "12", "13", "14", "15", "16", "17", "18"};                             /** String array with names of hours for printing */


        String[]timeslotNum = new String[roomBookings.length];                                                          /** Create new array of timeslots in the number format (explanation below) */

        for(int i = 0; i < timeslotNum.length; i++)
            timeslotNum[i] = toNumFormat(roomBookings[i].getTimeslot());                                                /** Turn all time slots of room bookings into number format */


        boolean[][]hours = new boolean[9][5];
        for(int i = 0; i < hours.length; i++){                                                                          /** Create boolean arrays of size 5(five days in a week: M, T, W, R, F) for all hours in a day (9 arrays) */
            hours[i] = new boolean[5];                                                                                  /** Boolean is used to indicate if there is a booking or not: True if there is, False if not */
        }


        for(int i = 0; i < timeslotNum.length; i++){
            hours[timeslotNum[i].charAt(1) - '0'][timeslotNum[i].charAt(0) - '0'] = true;                               /** Assign Bookings by hours and days to all 9 arrays of hours */
        }


        System.out.println("Time Table for room " + roomID + "\n");                                                     /** Start printing the time table */

        System.out.println("        Mon         Tue         Wed         Thu         Fri    \n" +
                "   +-----------+-----------+-----------+-----------+-----------+");

        for(int i = 0; i < hours.length; i++){
            printFromArray(hours[i], time[i], roomBookings, "room");                                           /** Send arrays one by one to the printing method */
        }

        System.out.println( "        Mon         Tue         Wed         Thu         Fri    ");                         /** Finish the time table */
    }





    /**
     * This method prints the block in the time table with a word in the central row
     * It receives array of hours with information about existing bookings,
     * time to be printed, array of bookings and dataType(User or Room)
     * @param timeArray
     * @param time
     * @param bookings
     * @param dataType
     */
    public void printFromArray(boolean[] timeArray, String time, Booking[] bookings, String dataType){
        System.out.println("   |           |           |           |           |           |");                         /** Print the first row of the block */
        System.out.print(time +" |");                                                                                   /** Print the time in the second central row */
        for(int i = 0; i < 5; i++){
            if(timeArray[i] == true){                                                                                   /** Check if there is a booking */
                String timeslotNum = Integer.toString(i) + "" + time.charAt(1);                                  /** Create time slot with recreating the number format of the time slot */

                for(int j = 0; j < bookings.length; j++){
                    if(timeslotNum.equals(toNumFormat(bookings[j].getTimeslot()))) {                                    /** Get the booking with the same time slot */
                        if(dataType.equalsIgnoreCase("user"))                                              /** Print out roomID if the data type is User */
                            System.out.print(justifiedString(bookings[j].getRoomID().toUpperCase()));
                        else                                                                                            /** Print out UserID if the data type is Room */
                            System.out.print(justifiedString(bookings[j].getUserID()));
                    }
                }

            }else                                                                                                       /** If there is no booking, print empty string of size 11 by using justifiedString method */
                System.out.print(justifiedString(""));

        }

        System.out.println(" " + time);                                                                                 /** Print the time */
        System.out.println("   |           |           |           |           |           |\n" +                       /** Print the third row of the block */
                "   +-----------+-----------+-----------+-----------+-----------+");
    }
}
