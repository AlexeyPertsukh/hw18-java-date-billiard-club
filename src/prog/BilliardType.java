package prog;

public enum BilliardType {
    RUS("РУС", "русский биллиард"),
    SNOOKER("СНУКЕР", "снукер"),
    ;
    private final String shortName;
    private final String name;


    BilliardType(String shortName, String name) {
        this.shortName = shortName;
        this.name = name;

    }

    public String getShortName() {
        return shortName;
    }

    public String getName() {
        return name;
    }
}
