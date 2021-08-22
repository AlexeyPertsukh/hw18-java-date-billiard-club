package prog;

public enum Command {
    HELP("?","помощь", true ),
    FREE_TIME("FREE","свободное время", true),
    ADD("ADD","добавить заказ", true),
    DEL("DEL","удалить", true),
    ALL_TABLES("0","все столы", true),
    END("END","выход", true),
    ;

    private final String key;
    private final String nameRus;
    private boolean enable;

    Command(String key, String nameRus, boolean enable) {
        this.key = key;
        this.nameRus = nameRus;
        this.enable = enable;
    }

    public String getKey() {
        return key;
    }

    public String getNameRus() {
        return nameRus;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
