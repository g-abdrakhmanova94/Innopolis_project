package net.proselyte.tutorials;

public class Homework1 {
    public static void main(String[] args) {
        //Возвращает длину строки (количество символов)
        String city = "Москва";
        int result = city.length();
        System.out.println(result); //6

        //Проверяет, пустая ли строка
        String animals = "Кошка";
        boolean animals2 = animals.isEmpty();
        System.out.println(animals2); //false

        //Проверяет, содержит ли строка только пробелы или она пуста
        String country = " ";
        boolean country2 = country.isBlank();
        System.out.println(country2); //true

        //Получаем подстроку (часть строки) начиная с одного индекса и до другого
        String name = "Гульнара";
        String name2 = name.substring(1,7);
        System.out.println(name2); //"ульнар"

        //Происходит поиск подстроки в строке, если строка найдена, то возвращает номер первого индекса, если нет-то -1
        String color = "Жёлтый";
        int color2 = color.indexOf("Р");
        System.out.println(color2); //-1

        //Происходит поиск подстроки в строке с конца, если строка найдена, то возвращает номер первого индекса, если нет-то -1
        String a = "Завтра в Казани снова пойдет снег!";
        int a2 = a.lastIndexOf("ег");
        System.out.println(a2); //31

        //Получаем копию строки, все символы которые - маленькие(нижний регистр)
        String flowers = "ГЛАДИОЛУСЫ";
        String flowers2 = flowers.toLowerCase();
        System.out.println(flowers2); //"гладиолусы"

        //Получаем копию строки, все символы которые - большие (верхний регистр)
        String c = "Мама мыла раму";
        String c2 = c.toUpperCase();
        System.out.println(c2); //"МАМА МЫЛА РАМУ"

        //Получаем замену часть строки на другую
        String d = "The weather is nice, but I forgot my umbrella";
        String d2 = d.replace("umbrella", "telephone");
        System.out.println(d2); //"The weather is nice, but I forgot my telephone"

        //Проверяет, начинается ли строка с указанного префикса
        String e = "The book that I'm reading is very interesting";
        boolean e2 = e.startsWith("book");
        System.out.println(e2); //false

        //Проверяет, заканчивается ли строка указанным суффиксом
        String f = "The book that I'm reading is very interesting";
        boolean f2 = f.endsWith("interesting");
        System.out.println(f2); //true

        //Происходит повторение текущей строки 3 раза
        String g = "Он любит играть на гитаре в свободное время";
        String g2 = g.repeat(3);
        System.out.println(g2); //"Он любит играть на гитаре в свободное времяОн любит играть на гитаре в свободное времяОн любит играть на гитаре в свободное время"

        //Проверяет вхождение подстроки в строку
        String h = "Она решила пойти по магазинам после работы";
        boolean h2 = h.contains("Она");
        System.out.println(h2); //true

        //Получаем новую строку,которая является объединением исходной строки и добавленной фразы, затем выводим её
        String i = "If it rains, we won't go to the park";
        String i2 = i.concat(" and I'll go home");
        System.out.println(i2); //"If it rains, we won't go to the park and I'll go home"

        //Происходит удаление лишних пробелов в начале и в конце строки
        String j = "  В этой строке лишние пробелы!  ";
        String j2 = j.trim();
        System.out.println(j2); //"В этой строке лишние пробелы!"

        //Происходит сравнение строк без пустых символов в начале и в конце
        String k = "  В этой строке лишние пробелы!  ";
        boolean k2 = k.equals("В этой строке лишние пробелы!");
        System.out.println(k2); //false
    }
}




