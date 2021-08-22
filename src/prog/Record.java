package prog;/*
https://javarush.ru/groups/posts/1941-kak-ne-poterjatjhsja-vo-vremeni--datetime-i-calendar
 */

import java.io.Serializable;
import java.time.LocalTime;

public class Record implements Serializable {
    private final String name;    //имя клиента
    private final LocalTime timeStart;
    private final LocalTime timeEnd;
//    private int gameMinute; //время игры в минутах
    private final double price;

    public Record(String name, LocalTime timeStart, LocalTime timeEnd, double price) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.price = price;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
