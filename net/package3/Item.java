package net.package3;

public class Item {String name;
    String article;
    float price;
    int amount;
    float weight;
    String [] colors;

    //конструктор без цены (цена-необязательный параметр, по умолчанию=0)
    public Item(String myName, String myArticle, int myAmount, float myWeight, String [] myColors) {
        name = myName;
        article = myArticle;
        price = 0; //цена не указана
        amount = myAmount;
        weight = myWeight;
        colors = myColors;
    }
    //конструктор с ценой
    public Item(String myName, String myArticle, float myPrice, int myAmount, float myWeight, String [] myColors) {
        name = myName;
        article = myArticle;
        price = myPrice;
        amount = myAmount;
        weight = myWeight;
        colors = myColors;
    }
}
