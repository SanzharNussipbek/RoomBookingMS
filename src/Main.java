/**
 * Student: Sanzhar Nussipbek
 * ID: 18200257
 * E-mail: 18200257@life.hkbu.edu.hk
 * Program description: This program is called RoomBookingMS and it performs Room Booking Management System operations.
 *                      There are two types of users: system administrators(variable admin in code) and ordinary user.
 *                      System administrator has the following options: add, delete and see all users, rooms and bookings.
 *                      Also admin can load data from input files and store data in output file as well.
 *                      Ordinary user can only see his/her own data(userID and booking) and also available rooms and time slots for bookings
 *                      The program has 13 classes in total and this one is the Main class which performs all operations and calls methods from other classes.
 *                      The system also has Error Handling ability(README file attached).
 */



import java.io.*;
import java.util.*;

public class Main{

    /**
     *  Creating objects from another classes
     */
    private User mainUser;                                                                                              /** mainUser is the user which logged into the system(he can be either admin or simple user) */
    private Token token;                                                                                                /** token object to create tokens of the lines read from the files  */
    private final UserDB userDB = new UserDB();                                                                         /** Database storing array of users */
    private Action action;                                                                                              /** Object of action class where all operations like add, delete, save, show, load and help are written */
    private final Add add = new Add();                                                                                  /** add object from Add class where all operations like add users, rooms, bookings and data from files are written */






    /**
     *  Variables for the later use
     */
    private boolean active = false;                                                                                     /** boolean variable to distinguish logged in user and not logged in user */
    private String[] commandLineTokens;                                                                                 /** array of strings to store tokens of the lines from files and user inputs
                                                                                                                        (commands - that's why it is called tokens of the line with a command) */
    Scanner in = new Scanner(System.in);                                                                                /** Scanner to read user input */





    /**
     * main method which receives command line arguments and sends them to the runApp method
     * if number of arguments are not 3, it exits the system
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)throws Exception{
        if(args.length != 3){
            System.out.println("Invalid number of arguments.");
            System.exit(0);
        }else
            new Main().runApp(args);
    }






    /**
     * runApp method which receives command line arguments from main method and begins the
     * Room Booking Management System by sending the files to the Add class to add data from the files
     * Then it shows welcoming message and prompts for command by calling getCommand() method
     * @param args
     * @throws Exception
     */
    private void runApp(String[] args) throws Exception{
        System.out.println("\n*** System Startup: begin ***\n");

        add.addUsersFromFile(new File(args[1]));
        add.addRoomsFromFile(new File(args[0]));
        add.addBookingsFromFile(new File(args[2]));

        System.out.println("\n*** System Startup: done! ***\n");

        welcome();
        getCommand();
    }





    /**
     * Method which prints welcoming message and shows available commands for the user
     */
    private void welcome(){
        System.out.println("+------------------------------------+\n" +
                           "|                                    |\n" +
                           "|   Room Booking Management System   |\n" +
                           "|                                    |\n" +
                           "+------------------------------------+\n" +
                           "\n" +
                           "Available commands:\n" +
                           "  Login User\n" +
                           "  Quit");
    }





    /**
     * Method which prompts for command until the user inputs something
     * Then it creates token object and cuts the line of the command into tokens to for the following checks
     * It stores all tokens in the commandLineTokens array using setter for that array
     * then is checks if the user is logged in and redirects to the next method accordingly
     *
     * @throws Exception
     */
    private void getCommand()throws Exception{
        String commandLine;

        System.out.println();
        do {
            System.out.print("ready> ");
            commandLine = in.nextLine();
        }while (commandLine.length() == 0);

        token = new Token(commandLine);
        setCommandLineTokens(token.getTokens(commandLine));


        if(!active)
            checkCommandWhenInactive();
        else
            checkCommandWhenActive();
    }





    /**
     * This method checks commands when the user is not logged in
     * There are two commands: login and quit and it
     *
     * @throws Exception
     */
    private void checkCommandWhenInactive()throws Exception{
        switch (commandLineTokens[0].toLowerCase()) {                                                                   /** check the first word of the command */
            case ("login"):
                if (commandLineTokens.length == 2) {                                                                    /** check the number of input words */
                    loginUser(commandLineTokens[1].toLowerCase());                                                      /** send second word(userID) to login method */
                    break;
                } else {
                    System.out.println("Login: missing userID");                                                        /** Prompt error message */
                    welcome();
                    getCommand();
                    break;
                }
            case ("quit"):
                if (commandLineTokens.length == 1) {
                    System.out.println("Goodbye!");
                    System.exit(0);                                                                              /** Exit the system */
                    break;
                } else {
                    System.out.println("Quit: too many arguments");                                                     /** If more than one word in command with Quit then try again */
                    welcome();
                    getCommand();
                    break;
                }

            default:
                System.out.println("Unknown command: " + commandLineTokens[0]);                                         /** If command is neither login nor quit */
                welcome();
                getCommand();
                break;
        }
    }





    /**
     * This method checks commands when the user is logged in
     * It checks command for three types of input: logout, quit and other commands
     * If logout, system sets mainUser of the system to null and changes active value to false;
     * User cannot quit before logout
     * If the command is other than logout and quit, the method redirects to checkCommandForUser() method
     *
     * @throws Exception
     */
    private void checkCommandWhenActive() throws Exception{
        switch (commandLineTokens[0].toLowerCase()) {
            case ("logout"):
                if (commandLineTokens.length == 1) {
                    setMainUser(null);
                    active = false;
                    welcome();
                }else {
                    System.out.println("Logout: too many arguments");
                }

                getCommand();
                break;

            case ("quit"):
                System.out.println("Unknown command: quit");
                getCommand();


            default:
                checkCommandForUser();
                break;
        }
    }





    /**
     * This method accepts String variable userID and prompts for password
     * Then it sets mainUser to the user got by entered useID and Pasword
     * if there is no such user: prompt for error
     * login otherwise and change active to true
     * Then start to get commands from user
     *
     * @param userID
     * @throws Exception
     */
    private void loginUser(String userID)throws Exception{
        System.out.print("Password: ");
        String userPasswd = in.nextLine();

        setMainUser(userDB.getUserByUserIDandPasswd(userID, userPasswd));
        if(mainUser != null) {
            active = true;
            System.out.println("\nLogin success!  Welcome to Room Booking Management System.");
        }else{
            System.out.println("Invalid UserID or Password.");
            welcome();
        }

        getCommand();
    }





    /**
     * Method to check input commands by checking the first word of the input
     * Then it calls method from Action class according to the first word of the input
     * If there is no such command: prompt for error
     * then prompt for input command again
     * @throws Exception
     */
    private void checkCommandForUser() throws Exception{
        action = new Action(mainUser, commandLineTokens);                                                               /** Create Action class object */
        switch (commandLineTokens[0].toLowerCase()) {                                                                   /** Check first word */
            case("list"):
                action.list();                                                                                          /** Call list method from Action class */
                break;

            case("add"):
                action.add();                                                                                           /** Call add method from Action class */
                break;

            case("delete"):
                action.delete();                                                                                        /** Call delete method from Action class */
                break;

            case("show"):
                action.show();                                                                                          /** Call show method from Action class */
                break;

            case("load"):
                action.load();                                                                                          /** Call load method from Action class */
                break;

            case("save"):
                action.save();                                                                                          /** Call save method from Action class */
                break;

            case("help"):
                action.help();                                                                                          /** Call help method from Action class */
                break;

            default:
                System.out.println("Unknown command: " + commandLineTokens[0]);
                break;
        }
        getCommand();
    }



    /**
     * Setter for commanlineTokens
     * @param commandLineTokens
     */
    public void setCommandLineTokens(String[] commandLineTokens) {
        this.commandLineTokens = commandLineTokens;
    }




    /**
     * Setter for mainUser
     * @param user
     */
    public void setMainUser(User user){ this.mainUser = user; }
}