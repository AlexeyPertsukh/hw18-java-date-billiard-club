package prog;

public class Const {
    public static final double PRICE_HOUR = 120;    //стоимость часа игры
    public static final String TIME_PATERN = "H':'mm";
    public static final String PROG_NAME = "БИЛЛИАРДНЫЙ КЛУБ";
    public static final String VERSION = "1.2";
    public static final String COPYRIGHT = "JAVA A01 \"ШАГ\", Запорожье 2021 ";
    public static final String AUTHOR = "Перцух Алексей";

    public static final int WORK_HOUR_START = 12;     //время начала работы
    public static final int WORK_HOUR_END = 23;    //время конца работы
    public static final int DIST_TABLE = 10;       //количество пробелов между изображениями столов
    public static final int LENGTH_LINE = 116;      //длинна линии подчеркивания в футуру/хедере

    //основные цвета в программе
    public static final String COLOR_HEADER = Color.ANSI_BLUE;
    public static final String COLOR_FOOTER = Color.ANSI_BLUE;
    public static final String COLOR_ON = Color.ANSI_YELLOW;

    public static final boolean ON = true;
    public static final boolean OFF = false;

    public final static String LOCAL_PATCH = "\\src\\prog\\files\\";
    public final static String FILENAME_HELP = "help.txt";
    public final static String FORMAT_FILENAME_SCHEDULE = "Tab%02d.schedule";


    private Const() {
    }
}
