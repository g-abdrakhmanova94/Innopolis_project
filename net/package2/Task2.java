package net.package2;

public class Task2 {
    public static void main(String[] args) {
Room [] rooms = {
        new Room("Кухня", 2, 1, 11.5),
        new Room("Гостиная", 1, 1, 20.2),
        new Room("Туалет",1, 1, 5.8),
        new Room("Ванная",2, 1, 10.1),
};
int i = 0;

System.out.println("Название комнаты: "+ rooms[i].name + ", " + "Окна: " + rooms[i].windows + " шт., " + "Дверь: " + rooms[i].door + " шт., " + "Площадь: "+ rooms[i].area + " м2 ");

i++;

System.out.println("Название комнаты: "+ rooms[i].name + ", "  + "Окна: " + rooms[i].windows + " шт., "  + "Дверь: " + rooms[i].door + " шт., " + "Площадь: "+ rooms[i].area + " м2 ");

i = i + 1;

System.out.println("Название комнаты: "+ rooms[i].name + ", "  + "Окна: " + rooms[i].windows + " шт., "  + "Дверь: " + rooms[i].door + " шт., " + "Площадь: "+ rooms[i].area + " м2 ");

i = i + 1;

System.out.println("Название комнаты: "+ rooms[i].name + ", "  + "Окна: " + rooms[i].windows + " шт., "  + "Дверь: " + rooms[i].door + " шт., " + "Площадь: "+ rooms[i].area + " м2 ");


Flat myFlat = new Flat("ул. Комсомольская, д.2,", 15, rooms);

System.out.println("Квартира: " + "Адрес: " + myFlat.address + " Этаж: " + myFlat.floor + " ");
    }
}
