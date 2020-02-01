/**
 *
 * Add class is user for all "add" actions received by user: adding user, booking, room and adding data from files
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Add{


    /**  Objects of the other classes  */
    private User user;
    private Room room;
    private Booking booking;
    private Token token;
    private final UserDB userDB = new UserDB();
    private final RoomDB roomDB = new RoomDB();
    private final BookingDB bookingDB = new BookingDB();


    public Add(){}                                                                                                      /**  Default constructor of the class  */




    /**
     * Method used to add new user with parameters written by mainUser in the command line
     * Accepts array of tokens of the text written by mainUser
     * @param commandLineTokens
     */
    public void addUserFromInput(String[] commandLineTokens){
        if (commandLineTokens.length < 5 || commandLineTokens.length > 5)                                               /** Check if number of words are not enough: for adding user there must be words add user userID password userType */
            System.out.println("Invalid number of arguments for Add User.");

        else {
            if(userDB.exists(commandLineTokens[2]))                                                                     /** Check if a user with the same parameters already exists */
                System.out.println("Failed to add user (" + commandLineTokens[2] + ") -- Duplicated user record");

            else {
                if(validUserType(commandLineTokens[4].charAt(0))){                                                      /** Check if userType is valid: 'a' or 'A' for admin, 'u' or 'U' for ordinary user*/
                    user = new User(commandLineTokens[2], commandLineTokens[3], commandLineTokens[4].charAt(0));        /** Create new user with those parameters */
                    System.out.println("User (" + commandLineTokens[2] + ") added successfully");
                }else
                    System.out.println("Failed to add user (" + commandLineTokens[2] + ") -- Invalid user type");
            }
        }
    }





    /**
     * Method used to add new room with parameters written by mainUser in the command line
     * Accepts array of tokens of the text written by mainUser
     * @param commandLineTokens
     */
    public void addRoomFromInput(String[] commandLineTokens){
        if (commandLineTokens.length < 4)                                                                               /** Check if number of words are not enough: for adding room there must be words add room roomID and room description */
            System.out.println("Invalid number of arguments for Add Room.");

        else {
            if(roomDB.exists(commandLineTokens[2].toLowerCase()))                                                       /** Check if a room with the same parameters already exists */
                System.out.println("Failed to add room (" + commandLineTokens[2].toUpperCase()
                        + ") -- Duplicated room record");
            else {
                String roomDesc = "";
                for (int i = 3; i < commandLineTokens.length; i++) {                                                    /** RoomID is one word, and other words(no limit) are for room description */
                    roomDesc += commandLineTokens[i];
                    if (i != commandLineTokens.length - 1)
                        roomDesc += " ";
                }

                room = new Room(commandLineTokens[2].toLowerCase(), roomDesc);                                          /** Create new room with written arguments */
                System.out.println("Room  (" + commandLineTokens[2].toUpperCase() + ") added successfully");
            }
        }
    }





    /**
     * Method used to add new booking with parameters written by mainUser in the command line
     * Accepts array of tokens of the text written by mainUser and mainUser of User type
     * @param commandLineTokens
     * @param mainUser
     */
    public void addBookingFromInput(String[] commandLineTokens, User mainUser){
        if(mainUser.isAdmin()) {                                                                                        /**Check if the mainUser is admin */

            if (commandLineTokens.length != 5)                                                                          /** Check if number of words are not enough: for adding a booking the must be words: add booking userID roomID and timeslot */
                System.out.println("Invalid number of arguments for Add Booking.");

            else if(!bookingDB.hasProblems(mainUser, commandLineTokens)){                                               /** Create new booking if booking parameters does not have any problems*/

                booking = new Booking(commandLineTokens[2], commandLineTokens[3], commandLineTokens[4].toUpperCase());
                System.out.println("Booking (" + commandLineTokens[2] + ", " + commandLineTokens[3]
                                    + ", " + commandLineTokens[4].toUpperCase() + ") added successfully");
            }

        }else{                                                                                                          /** If mainUser is not admin */

            if (commandLineTokens.length != 3)
                System.out.println("Invalid number of arguments for Add.");                                             /** Check if number of words are sufficient: for adding a booking the must be words: add roomID timeslot */

            else if(!bookingDB.hasProblems(mainUser, commandLineTokens)){                                               /** Create new booking if booking parameters does not have any problems*/

                    booking = new Booking(mainUser.getUserID(), commandLineTokens[1], commandLineTokens[2].toUpperCase());
                    System.out.println("Booking (" + mainUser.getUserID() + ", " + commandLineTokens[1]
                                            + ", " + commandLineTokens[1] + ") added successfully");
                }
        }
    }




    /**
     * Boolean value returning method that chekc if userType is valid
     * UserType is valid if it is either a, A, u or U.
     * @param userType
     * @return
     */
    public boolean validUserType(char userType){
        return userType == 'A' || userType == 'a' || userType =='u' || userType == 'U';
    }





    /**
     * Method to add new users from file with database
     * Accepts userFile of File type as a parameter
     * It has error handling function
     * @param userFile
     */
    public void addUsersFromFile(File userFile){
        try {
            Scanner in = new Scanner(userFile);

            int lineCount = 0;
            int loadNum = 0;

            System.out.print("Loading user db from " + userFile.getName() + "... ");

            while (in.hasNextLine()) {
                String line = in.nextLine();

                token = new Token(line);                                                                                /** Create new token object with line */
                String[] userInfo = token.getTokens(line);                                                              /** Get array of tokens */


                if(userInfo.length != 0) {
                    if (userInfo.length != 3)                                                                           /** Check if there are sufficient number of data */
                        System.out.print("\n" + userFile.getName() + ".loadDB: error adding record on line "
                                + (lineCount + 1) + " of " + userFile.getName() + " -- Corrupted user record\n");

                    else if (userDB.exists(userInfo[0]))                                                                /** Check if a user with the same userID already exists */
                        System.out.print("\n" + userFile.getName() + ".loadDB: error adding record on line "
                                + (lineCount + 1) + " of " + userFile.getName() + " -- Duplicated user record\n");

                    else if (!validUserType(userInfo[2].charAt(0)))                                                     /** Check if user type if valid(a, A, u, U) */
                        System.out.print("\n" + userFile.getName() + ".loadDB: error adding record on line "
                                + (lineCount + 1) + " of " + userFile.getName() + " -- Invalid user type\n");

                    else {
                        user = new User(userInfo[0], userInfo[1], userInfo[2].charAt(0));
                        loadNum++;
                    }

                    lineCount++;
                }
            }

            System.out.println(loadNum + " user records loaded.");                                                      /** Show results of the data loading */

        }catch(FileNotFoundException e){                                                                                /** If the system catches FileNotFoundException prompt for Error */
            System.out.println("Loading user db from " + userFile.getName() + "..."
                                + userFile.getName() + ".loadDB failed: File not found ("
                                + userFile.getName() + ")!");
            System.out.println("0 records loaded");
        }
    }




    /**
     * Method to add new rooms from file with database
     * Accepts roomFile of File type as a parameter
     * It has error handling function
     * @param roomFile
     */
    public void addRoomsFromFile(File roomFile){
       try {
           Scanner in = new Scanner(roomFile);

           int lineCount = 0;
           int loadNum = 0;

           System.out.print("Loading room db from " + roomFile.getName() + "... ");

           while (in.hasNextLine()) {
               String line = in.nextLine();

               token = new Token(line);                                                                                 /** Create new token object with line */
               String[] roomInfo = token.getTokens(line);                                                               /** Get array of tokens */

               if(roomInfo.length != 0) {
                   if (roomInfo.length < 2) {                                                                           /** Check if there are sufficient amount of data */
                       System.out.print("\n" + roomFile.getName() + ".loadDB: error adding record on line "
                               + (lineCount + 1) + " of " + roomFile.getName() + " -- Corrupted room record\n");
                   } else if (roomDB.exists(roomInfo[0].toLowerCase()))                                                 /** Check if a room with the same roomID already exists */
                       System.out.print("\n" + roomFile.getName() + ".loadDB: error adding record on line "
                               + (lineCount + 1) + " of " + roomFile.getName() + " -- Duplicated room record\n");
                   else {
                       String roomDesc = "";
                       for (int i = 1; i < roomInfo.length; i++) {                                                      /** Add all words except the first one to the room description */
                           roomDesc += roomInfo[i];
                           if (i != roomInfo.length - 1)
                               roomDesc += " ";
                       }
                       room = new Room(roomInfo[0].toLowerCase(), roomDesc);
                       loadNum++;
                   }

                   lineCount++;
               }
           }

           System.out.println(loadNum + " room records loaded.");                                                       /** Show results of the data loading */

       }catch(FileNotFoundException e){                                                                                 /** If the system catches FileNotFoundException prompt for Error */
           System.out.println("Loading room db from " + roomFile.getName()
                                + "..." + roomFile.getName() + ".loadDB failed: File not found ("
                                + roomFile.getName() + ")!");
           System.out.println("0 records loaded");
       }

    }




    /**
     * Method to add new bookings from file with database
     * Accepts bookingFile of File type as a parameter
     * It has error handling function
     * @param bookingFile
     */
    public void addBookingsFromFile(File bookingFile){
        try {
            Scanner in = new Scanner(bookingFile);

            int lineCount = 0;
            int loadNum = 0;

            System.out.print("Loading booking db from " + bookingFile.getName() + "... ");

            while (in.hasNextLine()) {
                String line = in.nextLine();

                token = new Token(line);                                                                                /** Create new token object with line */
                String[] bookingInfo = token.getTokens(line);                                                           /** Get array of tokens */

                if(bookingInfo.length != 0) {
                    if (!bookingDB.loadHasProblems(bookingInfo, lineCount, bookingFile.getName())) {                    /** Check booking data in the file has problems */
                        booking = new Booking(bookingInfo[0], bookingInfo[1], bookingInfo[2].toUpperCase());
                        loadNum++;
                    }

                    lineCount++;
                }
            }

            System.out.println(loadNum + " booking records loaded.");                                                   /** Show results of the data loading */

        }catch(FileNotFoundException e){                                                                                /** If the system catches FileNotFoundException prompt for Error */
            System.out.println("Loading booking db from " + bookingFile.getName() + "..."
                                + bookingFile.getName() + ".loadDB failed: File not found ("
                                + bookingFile.getName() + ")!");
            System.out.println("0 records loaded");
        }

    }
}
