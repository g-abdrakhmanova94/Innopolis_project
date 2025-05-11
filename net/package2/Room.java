package net.package2;

public class Room {String name;
    boolean isFurniture;
    boolean isCurtains;
    int windows;
    boolean isWallpaper;
    boolean isCeiling;
    int door;
    double area;


    public Room(String myName, int myWindows, int myDoor, double myArea) {
        name = myName;
        isFurniture = true;
        isCurtains = true;
        windows = myWindows;
        isWallpaper = true;
        isCeiling = true;
        door = myDoor;
        area = myArea;
    }
}
