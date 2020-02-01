/**
 * Action class has all the commands a user can use: add, delete, list, load, save and help
 */

import java.io.*;

public class Action {

    /** Objects of the other classes */
    private final UserDB userDB = new UserDB();
    private final RoomDB roomDB = new RoomDB();
    private final BookingDB bookingDB = new BookingDB();

    private final Add add = new Add();
    private final Delete delete = new Delete();
    private final Show show = new Show();
    private final Save save = new Save();
    private final User mainUser;
    private final String[] commandLineTokens;


    /** Constructor of the class */
    public Action(User mainUser, String[] commandLineTokens){
        this.mainUser = mainUser;
        this.commandLineTokens = commandLineTokens;
    }


    /**
     * List method is used for list command
     */
    public void list(){
        if(mainUser.isAdmin()) {                                                                                        /** Check if mainUser is admin */
            if (commandLineTokens.length != 2)                                                                          /** Check if there are sufficient number of words words */
                System.out.println("Invalid number of arguments for List.");
            else {
                switch (commandLineTokens[1]) {                                                                         /** Check the word after the word "list" */
                    case ("user"):
                        userDB.listUsers();                                                                             /** Call Method listUsers() from UserDB class if command is "list user" */
                        break;

                    case ("room"):
                        roomDB.listRooms();                                                                             /** Call Method listRooms() from RoomDB class if command is "list room" */
                        break;

                    case ("booking"):
                        bookingDB.listBookings();                                                                       /** Call Method listBookings() from BookingDB class if command is "list user" */
                        break;

                    default:
                        System.out.println("Unknown option, \"" + commandLineTokens[1] + "\", for List.");
                        break;
                }
            }
        }else{                                                                                                          /** If mainUser is not admin */
            if (commandLineTokens.length != 1)                                                                          /** Check number of words in command: must be only one "list */
                System.out.println("Invalid number of arguments for List.");
            else
                roomDB.listRooms();                                                                                     /** Call Method listRooms() from RoomDB class if command is "list room" */
        }
    }





    /**
     * add Method is used to add a new user, room or booking
     * Calls methods from Add class for particular cases
     */
    public void add(){
        if(mainUser.isAdmin()) {
            if (commandLineTokens.length < 4)
                System.out.println("Invalid number of arguments for Add.");
            else {
                switch (commandLineTokens[1]) {
                    case ("user"):
                        add.addUserFromInput(commandLineTokens);
                        break;

                    case ("room"):
                        add.addRoomFromInput(commandLineTokens);
                        break;

                    case ("booking"):
                        add.addBookingFromInput(commandLineTokens, mainUser);
                        break;

                    default:
                        System.out.println("Unknown option, \"" + commandLineTokens[1] + "\", for Add.");
                        break;
                }
            }
        }else
            add.addBookingFromInput(commandLineTokens, mainUser);
    }


    /**
     * delete Method is used to delete a user, room or booking
     * Calls methods from Delete class for particular cases
     */
    public void delete(){
        if(mainUser.isAdmin()) {
            if (commandLineTokens.length < 3)
                System.out.println("Invalid number of arguments for Delete.");
            else {
                switch (commandLineTokens[1]) {
                    case ("user"):
                        delete.deleteUserFromInput(commandLineTokens);
                        break;

                    case ("room"):
                        delete.deleteRoomFromInput(commandLineTokens);
                        break;

                    case ("booking"):
                        delete.deleteBookingFromInput(commandLineTokens, mainUser);
                        break;

                    default:
                        System.out.println("Unknown option, \"" + commandLineTokens[1] + "\", for Delete.");
                        break;
                }
            }
        }else{
            if (commandLineTokens.length < 3)
                System.out.println("Invalid number of arguments for Delete.");
            else
                delete.deleteBookingFromInput(commandLineTokens, mainUser);
        }
    }




    /**
     * show Method is used to show bookings of a room or a user
     * Calls methods from Show class for particular cases
     */
    public void show(){
        if(mainUser.isAdmin()) {
            if (commandLineTokens.length != 3)
                System.out.println("Invalid number of arguments for Show Time Table.");
            else {
                switch (commandLineTokens[1]) {
                    case ("user"):
                        show.showUser(commandLineTokens[2]);
                        break;

                    case ("room"):
                        show.showRoom(commandLineTokens[2]);
                        break;

                    default:
                        System.out.println("Unknown option, \"" + commandLineTokens[1] + "\", for Show.");
                        break;
                }
            }
        }else{
            if (commandLineTokens.length > 2)
                System.out.println("Invalid number of arguments for Show Time Table.");
            else {
                if(commandLineTokens.length == 1)
                    show.showUser(mainUser.getUserID());
                else
                    show.showRoom(commandLineTokens[1]);
            }
        }
    }




    /**
     * load Method is used to load data from input files
     * Calls methods from Add class for particular cases
     */
    public void load(){
        if(mainUser.isAdmin()) {
            if (commandLineTokens.length != 3)
                System.out.println("Invalid number of arguments for Load.");
            else {
                File loadFile = new File(commandLineTokens[2]);
                switch (commandLineTokens[1]) {
                    case ("user"):
                        add.addUsersFromFile(loadFile);
                        break;

                    case ("room"):
                        add.addRoomsFromFile(loadFile);
                        break;

                    case ("booking"):
                        add.addBookingsFromFile(loadFile);
                        break;

                    default:
                        System.out.println("Unknown option, \"" + commandLineTokens[1] + "\", for Load.");
                        break;
                }
            }
        }else
            System.out.println("Unknown command: load");
    }




    /**
     * save Method is used to save data to output file
     * Calls methods from Save class for particular cases
     * It has error handling function
     */
    public void save(){
        try {
            if (mainUser.isAdmin()) {
                if (commandLineTokens.length < 3)
                    System.out.println("Invalid number of arguments for Save.");
                else {
                    PrintWriter out = new PrintWriter(commandLineTokens[2]);
                    switch (commandLineTokens[1]) {
                        case ("user"):
                            save.saveUsersToFile(out);
                            System.out.println(commandLineTokens[2]);
                            break;

                        case ("room"):
                            save.saveRoomsToFile(out);
                            System.out.println(commandLineTokens[2]);
                            break;

                        case ("booking"):
                            save.saveBookingsToFile(out);
                            System.out.println(commandLineTokens[2]);
                            break;

                        default:
                            System.out.println("Unknown option, \"" + commandLineTokens[1] + "\", for Save.");
                            break;
                    }
                    out.close();
                }
            } else
                System.out.println("Unknown command: save");

        }catch (FileNotFoundException e){                                                                               /** If there is an error opening output file */
            System.out.println("Error. File not found.");
        }
    }





    /**
     * help Method is user to print all available commands for a user
     */
    public void help(){
        if(mainUser.isAdmin()) {                                                                                        /** Check if the mainUser is admin */
            if(commandLineTokens.length == 1) {                                                                         /** Check if there is only one word */
                System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                System.out.println("Available commands:\n" +
                        "  load   [ room | user | booking ]\n" +
                        "  save   [ room | user | booking ]\n" +
                        "  list   [ room | user | booking ]\n" +
                        "  show   [ room | user ]\n" +
                        "  add    [ room | user | booking ]\n" +
                        "  delete [ room | user | booking ]\n" +
                        "  help   [ command ]\n" +
                        "  logout\n");
            }else if(commandLineTokens.length == 2){                                                                    /** Check if there are two words */
                switch (commandLineTokens[1]){
                    case("list"):
                        System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                        System.out.println("\"list\" -- lists all rooms, users, or booking in the system.\n" +
                                "Usage of \"list\":\n" +
                                "  list room\n" +
                                "  list user\n" +
                                "  list booking");
                        break;

                    case("add"):
                        System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                        System.out.println("\"add\" -- adds a new room, user or booking to the system.\n" +
                                "Usage of \"add\":\n" +
                                "  add room roomID description...\n" +
                                "  add user userID passwd type\n" +
                                "  add booking userID roomID timeslot\n");
                        break;

                    case("delete"):
                        System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                        System.out.println("\"delete\" -- delete a room, user or booking from the system.\n" +
                                "Usage of \"delete\":\n" +
                                "  delete room roomID\n" +
                                "  delete user userID\n" +
                                "  delete booking userID roomID timeslot");
                        break;

                    case("save"):
                        System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                        System.out.println("\"save\" -- saves all rooms, users, or booking in the system to a file.\n" +
                                "Usage of \"save\":\n" +
                                "  save room fName\n" +
                                "  save user fName\n" +
                                "  save booking fName");
                        break;

                    case("load"):
                        System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                        System.out.println("\"load\" -- loads rooms, users, or booking from a file to the system.\n" +
                                "Usage of \"load\":\n" +
                                "  load room fName\n" +
                                "  load user fName\n" +
                                "  load booking fName");
                        break;

                    case("logout"):
                        System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                        System.out.println("\"logout\" -- logout from Room Booking System.\n" +
                                "Usage of \"logout\":\n" +
                                "  logout");
                        break;

                    case("show"):
                        System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                        System.out.println("\"show\" -- shows the timetable of a user or a room.\n" +
                                "Usage of \"show\":\n" +
                                "  show room roomID\n" +
                                "  show user userID");
                        break;

                    case("help"):
                        System.out.println("User: " + mainUser.getUserID() + " *** ADMIN ***\n");
                        System.out.println("\"help\" -- shows this help message.\n" +
                                "Usage of \"help\":\n" +
                                "  help\n" +
                                "  help command");
                        break;

                    default:
                        System.out.println("Unknown command: " + commandLineTokens[1]);
                        break;
                }
            }else                                                                                                       /** Check if there are more than two words */
                System.out.println("help: invalid number of arguments.");

        }else {                                                                                                         /** If the main user is not admin */
            if(commandLineTokens.length == 1) {
                System.out.println("User: " + mainUser.getUserID() + "\n");
                System.out.println("Available commands:\n" +
                        "  list\n" +
                        "  show   [ room ]\n" +
                        "  add    [ booking ]\n" +
                        "  delete [ booking ]\n" +
                        "  help   [ command ]\n" +
                        "  logout");
            }else if(commandLineTokens.length == 2){
                switch (commandLineTokens[1]){
                    case("list"):
                        System.out.println("User: " + mainUser.getUserID() + "\n");
                        System.out.println("\"list\" -- lists all rooms available in the system.\n" +
                                "Usage of \"list\":\n" +
                                "  list");
                        break;

                    case("add"):
                        System.out.println("User: " + mainUser.getUserID() + "\n");
                        System.out.println("\"add\" -- adds a new room booking.\n" +
                                "Usage of \"add\":\n" +
                                "  add roomID timeslot");
                        break;

                    case("delete"):
                        System.out.println("User: " + mainUser.getUserID() + "\n");
                        System.out.println("\"delete\" -- deletes an existing room booking.\n" +
                                "Usage of \"delete\":\n" +
                                "  delete roomID timeslot");
                        break;

                    case("show"):
                        System.out.println("User: " + mainUser.getUserID() + "\n");
                        System.out.println("\"show\" -- shows the timetable of the user, or the timetable of a room.\n" +
                                "Usage of \"show\":\n" +
                                "  show\n" +
                                "  show roomID");
                        break;

                    case("help"):
                        System.out.println("User: " + mainUser.getUserID() + "\n");
                        System.out.println("\"help\" -- shows this help message.\n" +
                                "Usage of \"help\":\n" +
                                "  help\n" +
                                "  help command");
                        break;

                    case("logout"):
                        System.out.println("User: " + mainUser.getUserID() + "\n");
                        System.out.println("\"logout\" -- logout from Room Booking System.\n" +
                                "Usage of \"logout\":\n" +
                                "  logout");
                        break;
                    default:
                        System.out.println("Unknown command: " + commandLineTokens[1]);
                        break;
                }
            }else{
                System.out.println("help: invalid number of arguments.");
            }
        }
    }
}