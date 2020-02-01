/**
 *
 * RoomDB class for storing an array of rooms
 * It has add, delete and list void methods
 * and boolean value returning exists method and getters
 *
 */

public class RoomDB {
    /** Array of rooms with initial size 0 */
    public static Room[] roomsArray = new Room[0];


    /** Default constructor for RoomDB class */
    public RoomDB(){}




    /**
     *
     * addRoom method receives a room
     * and adds it to the array of rooms appending the array
     * This code uses the same logic as in the Token class we made during the lectures
     *
     * @param room
     */
    public void addRoom(Room room){

        Room[] newRoomsArray = new Room[roomsArray.length+1];                                                           /** Creating new array with size of roomsArray.length + 1 */

        for(int i = 0; i < roomsArray.length; i++)                                                                      /** Copying all array elements into the new array */
            newRoomsArray[i] = roomsArray[i];

        newRoomsArray[newRoomsArray.length - 1] = room;                                                                 /** Saving received room to the new array */

        roomsArray = newRoomsArray;                                                                                     /** Copying all new array elements back to the original array */

    }




    /**
     *
     * boolean value returning method exists which receives roomID
     * and checks it with all rooms roomIDs
     * returns true if there is a room with the same roomID
     * returns false if there isn't any
     *
     * @param roomID
     * @return
     */
    public boolean exists(String roomID){
        for(int i = 0; i < roomsArray.length; i++){
            if(roomID.equalsIgnoreCase(roomsArray[i].getRoomID()))
                return true;
        }

        return false;
    }




    /**
     *
     * delete method which receives room of Room type
     * and deletes it from array by creating new array without this room
     *
     * @param room
     */
    public void deleteRoom(Room room){
        Room[] newRoomsArray = new Room[roomsArray.length - 1];                                                         /** Creating new array with size of roomsArray.length - 1 */

        int j = 0;                                                                                                      /** Index indicator for newRoomsArray[] */

        for(int i = 0; i < roomsArray.length; i++){                                                                     /** Copying all array elements into the new array */
            if(!room.equals(roomsArray[i]))
                newRoomsArray[j++] = roomsArray[i];                                                                     /** Do not copy the input room to the new array */
        }

        roomsArray = newRoomsArray;                                                                                     /** Copying all new array elements back to the original array */
    }




    /**
     * This method prints out roomID and room description of all rooms in the array
     */
    public void listRooms(){
        for(int i = 0; i < roomsArray.length; i++)
            System.out.printf("%-7s -- %s\n", roomsArray[i].getRoomID(), roomsArray[i].getRoomDescription());
    }




    /**
     * getRoomByID accepts roomID as an argument
     * and returns the room with the same roomID
     * returns null if there isn't any room with that ID
     * @param roomID
     * @return
     */
    public Room getRoomByID(String roomID){
        for(int i = 0; i < roomsArray.length; i++){
            if(roomsArray[i].getRoomID().equalsIgnoreCase(roomID))
                return roomsArray[i];
        }

        return null;
    }


    /**
     * Getter for the array of rooms
     * @return
     */
    public static Room[] getRoomsArray() {
        return roomsArray;
    }
}
