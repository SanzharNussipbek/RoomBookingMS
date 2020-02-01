# Room Booking Management System

RoomBookingMS is the program used to manipulate with users, rooms and bookings of the room booking management system.

## Getting Started

There are 13 classes in total.
 1. Main class: has main method and starts the program
 2. User class: to create user objects
 3. UserDB class: to store users in user array
 4. Room class: to create room objects
 5. RoomDB class: to store rooms in room array
 6. Booking class: to create booking objects
 7. BookingDB class: to store bookings in room array
 8. Action class: deals with reading and executing available commands
 9. Add class: deals with methods associated with add command
 10. Delete class: deals with methods associated with delete command
 11. Save class:  deals with methods associated with save command
 12. Load class:  deals with methods associated with load command
 13. Token class: used to tokenize the user input


## Running the program


### CMD running

The following line should be used to run the program

```
javac Main.java
java Main Room.db User.db Booking.db
```

### Error handling

The program has error handling function and can deal with errors like: 
-   Failing to open an input file: FileNotFoundException in Add class: 
    
-   Failing to open an output file: Exception in Action class 
    
-   Errors happening when performing file IO operations: if-else statements for all input cases
    
-   Corrupted data in the input files: if-else statement for all file reading cases



## Built With

* IntelliJ IDEA CE


## Authors

* **Sanzhar Nussipbek** - *18200257* - * 18200257@life.hkbu.edu.hk *


## Acknowledgments

* Department of Computer Science, Hong Kong Baptist University
* Dr. Joe CK Yau

