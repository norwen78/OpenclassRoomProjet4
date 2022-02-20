package model;


import com.example.maru.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {
    private String name;
    private static int color;

    private Room(int roomColor, String roomName) {
        this.name = roomName;
        this.color = roomColor;
    }

    public static int getRoomColor(){return color;}
    private String getRoomName() {
        return name;
    }

    private static List<Room> ListRooms = Arrays.asList(
            new Room(R.color.Bowser,"Bowser"),
            new Room(R.color.Luigi,"Luigi"),
            new Room(R.color.Mario,"Mario"),
            new Room(R.color.Yoshi,"Yoshi"),
            new Room(R.color.Waluigi,"Waluigi")
    );

    static public List<String> getRoom() {
        List<String> roomList = new ArrayList<>();
        for (Room room : ListRooms) {
            roomList.add(room.getRoomName());
        }
        return roomList;
    }
}
