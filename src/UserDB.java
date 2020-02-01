/**
 *
 * UserDB class is the class where all users are stored in an array of User type
 * In this class there are add, delete and list void methods
 * and boolean method exists with other methods returning a users by their parameters
 *
 */


public class UserDB {

    /** Static array for storing users with initial size of 0 which will be appended afterwards */
    private static User[] usersArray = new User[0];


    /** Default constructor for UserDB class */
    public UserDB(){}

    

    /**
     *
     * addUser method accepts user of User type and adds it to the array
     * appending array at the same time
     * This code uses the same logic as in the Token class we made during the lectures
     *
     * @param user
     */
    public void addUser(User user){

        User[] newUsersArray = new User[usersArray.length + 1];                                                         /** Creating new array with size of usersArray.length + 1 */

        for (int i = 0; i < usersArray.length; i++)                                                                     /** Copying all array elements into the new array */
            newUsersArray[i] = usersArray[i];

        newUsersArray[newUsersArray.length - 1] = user;                                                                 /** Saving received user to the new array */

        usersArray = newUsersArray;                                                                                     /** Copying all new array elements back to the original array */
    }




    /**
     *
     * Boolean value returning method which receives userID
     * and compares it with userIDs of all usersArray elements
     * returns true if there is a user with same ID
     * returns false if there is not
     *
     * @param userID
     * @return
     */
    public boolean exists(String userID){
        for(int i = 0; i < usersArray.length; i++){
            if(userID.equalsIgnoreCase(usersArray[i].getUserID()))
                return true;
        }
        return false;
    }




    /**
     *
     * deleteUser method which receives User type user
     * and deletes it from the array by creating a new array without this particular user
     *
     * @param user
     */
    public void deleteUser(User user){
        User[] newUsersArray = new User[usersArray.length - 1];                                                         /** Creating new array with size of usersArray.length - 1 */

        int j = 0;                                                                                                      /** Index indicator for newUsersArray[] */

        for(int i = 0; i < usersArray.length; i++){                                                                     /** Copying all array elements into the new array */
            if(!user.equals(usersArray[i]))
                newUsersArray[j++] = usersArray[i];                                                                     /** Do not copy the input user to the new array */
        }

        usersArray = newUsersArray;                                                                                     /** Copying all new array elements back to the original array */
    }




    /**
     *
     * listUser method prints out all users with a particular format
     * It prints out "*** ADMIN ***" word if the user is admin
     *
     */
    public void listUsers(){
        for(int i = 0; i < usersArray.length; i++){
            if(usersArray[i].isAdmin())
                System.out.printf("%-9s %-8s    *** ADMIN *** \n", usersArray[i].getUserID(), usersArray[i].getPasswd());
            else
                System.out.printf("%-9s %-8s \n", usersArray[i].getUserID(), usersArray[i].getPasswd());
        }
    }




    /**
     *
     * Method used for checking userID and password when user tries to log in
     * Receive userID and password and returns the user with the same parameters if there is one
     * returns null if there isn't any
     *
     * @param userID
     * @param userPasswd
     * @return
     */
    public User getUserByUserIDandPasswd(String userID, String userPasswd){
        for(int i = 0; i < usersArray.length; i++){
            if(usersArray[i].getUserID().equalsIgnoreCase(userID) && usersArray[i].getPasswd().equals(userPasswd))      /** We use equalsIgnoreCase for userID because userID can be written in either upperCase or lowerCase */
                return usersArray[i];                                                                                   /** But we use only equals because password must be exactly the same and it is case sensitive */
        }

        return null;
    }




    /**
     *
     * Method used for getting a user (used in Delete class)
     * Receives userID and returns a user with the same ID if there is one
     * returns null if there isn't any
     *
     * @param userID
     * @return
     */
    public User getUserByID(String userID){
        for(int i = 0; i < usersArray.length; i++){
            if(usersArray[i].getUserID().equalsIgnoreCase(userID))
                return usersArray[i];
        }

        return null;
    }




    /**
     * Getter for usersArray
     * @return
     */
    public User[] getUsersArray(){ return usersArray; }
}
