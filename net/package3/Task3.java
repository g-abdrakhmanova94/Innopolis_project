package net.package3;

import java.util.Arrays;

public class Task3 {
    public static void main(String[] args) {

        Item myItem1 = new Item("Платье", "1674648580", 4336.00f,1,150, new String[]{"Красное"});
        Item myItem2 = new Item("Куртка", "1488211782", 1, 300, new String[]{"Голубая"});
        Item myItem3 = new Item("Кепка", "S-1449845013", 2566.00f, 2, 240, new String[]{"Черная", "Синяя"});
        Item myItem4 = new Item("Футболка","R645078427", 3120.00f,5,750, new String[]{"Розовая", "Жёлтая", "Белая", "Синяя", "Фиолетовая"});
        Item myItem5 = new Item("Джинсы", "1897386864E", 4799.00f,1,600, new String[]{"Голубые"});


        System.out.println(myItem1.article + " - " + myItem1.name + " - " + myItem1.price + " руб. - " + myItem1.amount + " шт. - " + myItem1.weight + " гр. - " + Arrays.toString(myItem1.colors));
        System.out.println(myItem2.article + " - " + myItem2.name + " - " + myItem2.price + " руб. - " + myItem2.amount + " шт. - " + myItem2.weight + " гр. - " + Arrays.toString(myItem2.colors));
        System.out.println(myItem3.article + " - " + myItem3.name + " - " + myItem3.price + " руб. - " + myItem3.amount + " шт. - " + myItem3.weight + " гр. - " + Arrays.toString(myItem3.colors));
        System.out.println(myItem4.article + " - " + myItem4.name + " - " + myItem4.price + " руб. - " + myItem4.amount + " шт. - " + myItem4.weight + " гр. - " + Arrays.toString(myItem4.colors));
        System.out.println(myItem5.article + " - " + myItem5.name + " - " + myItem5.price + " руб. - " + myItem5.amount + " шт. - " + myItem5.weight + " гр. - "+ Arrays.toString(myItem5.colors));
    }
}
