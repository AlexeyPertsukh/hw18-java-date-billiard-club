package prog;

import java.util.Comparator;

//для сортировки по времени
public class ComparatorTimeStart implements Comparator<Record> {
    @Override
    public int compare(Record rec1, Record rec2) {
        return rec1.getTimeStart().compareTo(rec2.getTimeStart());
    }
}
