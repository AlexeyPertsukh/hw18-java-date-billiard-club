package prog;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Table implements Serializable {
    private static int cnt;

    private static final char CHAR_FOR_CHANGE = '#';
    private static final String[] PIC_POOL_TABLE = {
            " СТОЛ #  ",
            "┌───────┐",
            "│о     о│",
            "│       │",
            "│о     о│",
            "│       │",
            "│о     о│",
            "└───────┘",
            "         ",
            "         ",
    };
    private final int num;
    private final BilliardType type;
    ArrayList<Record> schedule; //расписание

    public Table(BilliardType type, int num) {
        this.num = num;
        this.type = type;
        schedule = new ArrayList<Record>();
    }

    public Table(BilliardType type) {
        this(type, ++cnt);
    }

    public String[] getPicture() {
        String[] pic;
        char numChar = My.intToChar(num);
        pic = PIC_POOL_TABLE.clone();
        int length = pic[0].length();
        pic[0] = My.getStrAlignCenter( "СТОЛ " + num, length);
        pic[pic.length - 2] = My.getStrAlignCenter( type.getShortName(), length);
        My.changeCharInArr(pic, CHAR_FOR_CHANGE, numChar);

        return pic;
    }

    public String getColor() {
        return null;
    }

    public static int getCnt() {
        return cnt;
    }

    public int getNum() {
        return num;
    }

    public BilliardType getType() {
        return type;
    }

    public void printSchedule() {
        int num = 1;
        if(schedule.isEmpty()) {
            System.out.println("НЕТ ЗАПИСЕЙ");
        }
        else {
            System.out.printf("%-3s   %-30s   %-6s   %-6s   %12s   %14s  \n", "N", "КЛИЕНТ", "НАЧАЛО", "КОНЕЦ", "ДЛИТЕЛЬНОСТЬ", "СТОИМОСТЬ(грн)");
            for (Record rec : schedule) {

                String name = rec.getName();
                String strTimeStart = ForLocalTime.getStrTime(rec.getTimeStart());
                String strTimeEnd = ForLocalTime.getStrTime(rec.getTimeEnd());
                String strGameTime = ForLocalTime.strDurationHourMin(rec.getTimeStart(), rec.getTimeEnd());
                String strPrice = String.format("%.2f",rec.getPrice());
                String strNum = String.format("%d.",num);

                System.out.printf("%-3s   %-30s   %-6s   %-6s   %12s   %14s  \n", strNum, name, strTimeStart, strTimeEnd, strGameTime, strPrice);
                num++;
            }
        }
    }

    @Override
    public String toString() {
        String str = "СТОЛ #" + num + "\n";
        str += type.getName();
    return str;
    }

    //время свободно?
    public boolean isFreeTime(LocalTime timeStart, LocalTime timeEnd) {
        for (Record rec : schedule) {
            LocalTime recTimeStart = rec.getTimeStart();
            LocalTime recTimeEnd = rec.getTimeEnd();

            if( (ForLocalTime.isRange(timeStart, timeEnd, recTimeStart) && ForLocalTime.isEqual(timeEnd, recTimeStart)) ||
                (ForLocalTime.isRange(recTimeStart, recTimeEnd, timeStart) && ForLocalTime.isEqual(recTimeEnd, timeStart))
              )
            {
                return false;
            }

        }

        return true;
    }


    //распечатать свободное время
    public void printFreeTime() {
        LocalTime timeFreeStart = LocalTime.of(Const.WORK_HOUR_START,0);
        LocalTime localTimeWorkOff = LocalTime.of(Const.WORK_HOUR_END,0);

        System.out.println("Свободное время:");
        for (Record rec : schedule) {
            if(timeFreeStart.isBefore(rec.getTimeStart())) {
                String str = String.format("%s - %s ", ForLocalTime.getStrTime(timeFreeStart), ForLocalTime.getStrTime(rec.getTimeStart()));
                System.out.println(str);
            }
            timeFreeStart = rec.getTimeEnd();
        }

        if(timeFreeStart.isBefore(localTimeWorkOff)) {
            String str = String.format("%s - %s ", ForLocalTime.getStrTime(timeFreeStart), ForLocalTime.getStrTime(localTimeWorkOff));
            System.out.println(str);
        }

    }

    public void add(String name, LocalTime timeStart, LocalTime timeEnd, double price) {
        schedule.add(new Record(name, timeStart, timeEnd, price));
        schedule.sort(new ComparatorTimeStart());   //сортировка по времени
    }


    public Record del(int num) {
        if(num >= 0 && num < schedule.size()) {
            return schedule.remove(num);
        }
        return null;
    }

    public void setSchedule(ArrayList<Record> schedule) {
        this.schedule = schedule;
    }

    public ArrayList<Record> getSchedule() {
        return schedule;
    }
}
