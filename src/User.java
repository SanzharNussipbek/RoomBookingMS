/**
 *
 * User class creates user objects with userID, userPasswd and userType
 * After creating user in the constructor, it calls addUser() method from UserDB class and add the user into the database
 * It checks whether two users are equal just by comparing their userID
 * User is considered as System administrator is user's userType is 'A' or 'a'
 *
 */


public class User {

    /** Variables of a user */
    private final String userID;
    private final String passwd;
    private final char userType;



    /** Object of the UserDB class */
    private UserDB userDB = new UserDB();




    /**
     *
     * Constructor which accepts String userID, user password
     * and char type userType which is either 'a' or 'u'
     * It then adds user to the database in the UserDB class
     *
     * @param userID
     * @param passwd
     * @param userType
     */
    public User(String userID, String passwd, char userType){
        this.userID = userID;
        this.passwd = passwd;
        this.userType = userType;
        userDB.addUser(this);
    }



    /**
     * Copy constructor of the class
     * @param user
     */
    public User(User user){
        this(user.getUserID(), user.getPasswd(), user.getUserType());
    }




    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return userID + " " + passwd + " " + userType;
    }




    /**
     * It checks whether two users are equal just by comparing their userID
     * @param user
     * @return
     */
    public boolean equals(User user) { return this.userID.equalsIgnoreCase(user.userID); }




    /**
     * User is considered as System administrator is user's userType is 'A' or 'a'
     * @return
     */
    public boolean isAdmin(){
        return this.getUserType() == 'A' || this.getUserType() =='a';
    }




    /**
     * Getters
     * @return
     */
    public String getUserID() { return userID; }


    public String getPasswd() { return passwd; }


    public char getUserType() { return userType; }
}
