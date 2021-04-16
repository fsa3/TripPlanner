
package hotelSystem.controllers;

import hotelSystem.entities.Accommodation;
import hotelSystem.entities.Room;
import hotelSystem.entities.User;

import java.sql.Date;
import java.util.ArrayList;


public class AccommodationController {
    private Accommodation theAccommodation;

    public AccommodationController(Accommodation accommodation) {
        this.theAccommodation = accommodation;
    }

    public void addManagerId(User user) {

    }

    public void addRoom(Room room) {

    }

    public void removeRoom(Room room) {

    }

    public void changeRoomPrice(Room room, double price) {

    }

   // public void changeRoomType(RoomType roomType) {

   // }

    public void changeRoomCapacity(Room room, int cap) {

    }

    public void changeLocation(Accommodation acc, String Location) {

    }

    public void changeFacilities(Accommodation acc, String facilities) { //hvernig geyma facilities?

    }
}

