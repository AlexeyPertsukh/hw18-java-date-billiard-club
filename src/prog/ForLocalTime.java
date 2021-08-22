package prog;/*
Дата и время в Java 8. Сравнение даты и времени
http://www.seostella.com/ru/article/2014/07/08/data-i-vremya-v-java-8-sravnenie-daty-i-vremeni.html
 */

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

//вспомогательный класс для операций c LocalTime
public class ForLocalTime {
    private ForLocalTime() {
    }


    //время попадает в диапазон?
    public static boolean isRange(LocalTime timeStart, LocalTime timeEnd, LocalTime time) {
        return (isMaxEqual(time,timeStart) && isMinEqual(time,timeEnd));
    }

    //прибавить секунды к LocalTime
    public static LocalTime localTimeAddSec(LocalTime time, int sec) {
        int hour = time.getHour();
        int minute = time.getMinute();
        LocalTime lt = LocalTime.of(hour, minute);
        lt.plusSeconds(sec);
        return lt;
    }

    //прибавить 1 секунду к LocalTime
    public static LocalTime localTimeAdd1Sec(LocalTime time) {
        return localTimeAddSec(time, 1);
    }

    //распечатать время
    public static String getStrTime(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern(Const.TIME_PATERN));
    }


    //перевод LocalTime в секунды, начиная с 00:00
    public static int localTimeToSec(LocalTime localTime) {
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int sec = localTime.getSecond();
        return (hour * 3600) + (minute * 60) + sec;
    }


    //ввод времени
    public static LocalTime nextLocalTime(Scanner sc, String text) {
        LocalTime localTime = null;
        while(true) {
            System.out.print(text);
            String cmd = sc.next();

            try {
                localTime = LocalTime.parse(cmd, DateTimeFormatter.ofPattern("H:mm"));
                return localTime;
            }
            catch (DateTimeException ex) {
                System.out.println("неправильный формат времени");
            }
        }
    }


    //ввод времени, не меньше localTimeMin
    public static LocalTime nextLocalTime(Scanner sc, String text, LocalTime timeMin) {
        LocalTime localTime = null;
        while(true) {
            localTime = nextLocalTime(sc, text);
            if(localTime.isAfter(timeMin)) {
                return localTime;
            }
            else {
                System.out.println("вы не можете ввести время, меньше чем " + getStrTime(timeMin));
            }
        }
    }

    //длительность временного отрезка в минутах
    public static int durationMinutes(LocalTime timeStart, LocalTime timeEnd) {
        long diffInMinutes = java.time.Duration.between(timeStart, timeEnd).toMinutes();
        return (int)diffInMinutes;
    }


    //длительность временного отрезка - [0] = часы, [1] = минуты
    public static int[] durationHourMin(LocalTime timeStart, LocalTime timeEnd) {
        int minutes = durationMinutes(timeStart, timeEnd);
        int hour = minutes / 60;
        minutes %= 60;
        return new int[] {hour, minutes};
    }


    //длительность временного отрезка - строка "HH:MM"
    public static String strDurationHourMin(LocalTime timeStart, LocalTime timeEnd) {
        int[] hm = durationHourMin(timeStart, timeEnd);
        return String.format("%02d:%02d", hm[0], hm[1]);
    }

    //time1 <= time2 = true
    public static boolean isMinEqual(LocalTime time1, LocalTime time2) {
        int n = time1.compareTo(time2);
        return (n <= 0);
    }

    //time1 >= time2 = true
    public static boolean isMaxEqual(LocalTime time1, LocalTime time2) {
        int n = time1.compareTo(time2);
        return (n >= 0);
    }

    //time1 == time2 = true
    public static boolean isEqual(LocalTime time1, LocalTime time2) {
        int n = time1.compareTo(time2);
        return (n != 0);
    }


}
