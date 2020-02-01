/**
 *
 * Room class creates room objects with roomID and room description
 * It then stores the rooms inside the roomDB
 *
 *
 */

public class Room {

    /** Room parameters */
    private final String roomID;
    private final String roomDescription;


    /** Object of the RoomDB class */
    private RoomDB roomDB = new RoomDB();




    /**
     *
     * Constructor of the Room class
     * Receives roomID and roomDescription string and creates a room
     * then calls addRoom() method from RoomDB class to add the room into the array
     *
     * @param roomID
     * @param roomDescription
     */
    public Room(String roomID, String roomDescription){
        this.roomID = roomID;
        this.roomDescription = roomDescription;
        roomDB.addRoom(this);
    }



    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return roomID + " -- " + roomDescription;
    }


    /**
     *
     * Boolean value returning method Equals
     * which receives room and checks "this" room with the inout room
     * by comparing their ID and description
     * (used in Delete room method in the RoomDB class)
     *
     * @param room
     * @return
     */
    public boolean equals(Room room) {
        return this.roomID.equalsIgnoreCase(room.getRoomID())
                && this.roomDescription.equalsIgnoreCase(room.getRoomDescription());
    }



    /**
     *
     * Getters for the room parameters
     *
     * @return
     */
    public String getRoomID() { return roomID; }

    public String getRoomDescription() { return roomDescription; }
}
