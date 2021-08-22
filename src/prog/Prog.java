package prog;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Prog {

    private final Scanner sc;
    private String cmd;
    private boolean endProg;
    private final Table[] tables;
    private Table focusTable;

    private final Command cmdHelp;
    private final Command cmdFreeTime;
    private final Command cmdAdd;
    private final Command cmdDel;
    private final Command cmdEnd;
    private final Command cmdAllTables;
    private final Command[] commands;

    public Prog() {
        sc = new Scanner(System.in);
        tables = new Table[10];
        tables[0] = new Table(BilliardType.RUS);
        tables[1] = new Table(BilliardType.SNOOKER);
        tables[2] = new Table(BilliardType.RUS);
        tables[3] = new Table(BilliardType.SNOOKER);
        tables[4] = new Table(BilliardType.SNOOKER);

        tables[5] = new Table(BilliardType.RUS);
        tables[6] = new Table(BilliardType.SNOOKER);
        tables[7] = new Table(BilliardType.RUS);
        tables[8] = new Table(BilliardType.RUS);
        tables[9] = new Table(BilliardType.SNOOKER);

        cmdHelp = Command.HELP;
        cmdFreeTime = Command.FREE_TIME;
        cmdAdd = Command.ADD;
        cmdDel = Command.DEL;
        cmdAllTables = Command.ALL_TABLES;
        cmdEnd = Command.END;

        commands = new Command[] {cmdHelp, cmdFreeTime, cmdAdd, cmdDel, cmdEnd, cmdAllTables };

    }

    //===== ОСНОВНОЙ БЛОК ==========================
    public void go() {
        printOnStart();
        loadSchedulesFromFiles();
        while(!endProg) {
            enableMenuItems();
            printPage();
            inputCommand();
            processCommand();
        }
        saveSchedulesToFiles();
        printOnEnd();
    }
    //==============================================

    private void inputCommand() {
        System.out.print("Ввведите команду: ");
        cmd = sc.next();
    }

    private void processCommand() {
        String key;
        Command command;

        //хэлп
        command =  cmdHelp;
        key = command.getKey();
        if(My.cmpStr(cmd, key) && command.isEnable()) {
            printHelp();
            return;
        }

        //забронировать стол
        command =  cmdAdd;
        key = command.getKey();
        if(My.cmpStr(cmd, key) && command.isEnable()) {
            add();
            return;
        }

        //удалить заказ
        command =  cmdDel;
        key = command.getKey();
        if(My.cmpStr(cmd, key) && command.isEnable()) {
            del();
            return;
        }

        //свободное время столов
        command =  cmdFreeTime;
        key = command.getKey();
        if(My.cmpStr(cmd, key) && command.isEnable()) {
            printFreeTime();
            return;
        }

        //выход
        command =  cmdEnd;
        key = command.getKey();
        if(My.cmpStr(cmd, key) && command.isEnable()) {
            endProg = true;
            return;
        }

        //показать все столы
        command =  cmdAllTables;
        key = command.getKey();
        if(My.cmpStr(cmd, key) && command.isEnable()) {
            resetFocusTable();
            return;
        }

        // выбрать стол по номеру
        if(My.isInteger(cmd)) {
            int num = Integer.parseInt(cmd);
            num--;
            setFocusTable(num);
            if(focusTable == null) {
                My.nextCharLowerCase(sc,"Неправильный номер стола. Введите 'y' для продолжения: ",'y');
            }
            return;
        }

        System.out.println("Неизвестная команда");
    }

    private void printOnStart() {

    }

    private void printOnEnd() {
        System.out.println();
        System.out.println(Const.COPYRIGHT);
        System.out.println(Const.AUTHOR);
    }

    private void printHeader() {
        Color.setTextColor(Const.COLOR_HEADER);
        String strLine = My.getLine(Const.LENGTH_LINE);
        System.out.println(strLine);
        String str = String.format("%s v%s Время работы: %d:00 - %d:00", Const.PROG_NAME, Const.VERSION, Const.WORK_HOUR_START, Const.WORK_HOUR_END);
        System.out.println(str);
        System.out.println(strLine);
        Color.resetTextColor();
    }

    private void printFooter() {

        Color.setTextColor(Const.COLOR_FOOTER);
        String str = "";
        for (Command tmp : commands) {
            if(tmp.isEnable()) {
                str += tmp.getKey() + " " + tmp.getNameRus() + "   ";
            }
        }

        str += String.format("1-%d выбрать стол", Table.getCnt());

        String strLine = My.getLine(Const.LENGTH_LINE);
        System.out.println(strLine);
        System.out.println(str);
        System.out.println(strLine);
        Color.resetTextColor();
    }

    private void printPage() {
        printHeader();

        //выбран стол
        if(isFocusTable()) {
            printFocusTable();
        }
        else   { //не выбран конкретный стол - выводим все имеющиеся столы
            My.printArr(Const.DIST_TABLE, tables[0].getPicture(), tables[1].getPicture(), tables[2].getPicture(), tables[3].getPicture(), tables[4].getPicture());
            My.printArr(Const.DIST_TABLE, tables[5].getPicture(), tables[6].getPicture(), tables[7].getPicture(), tables[8].getPicture(), tables[9].getPicture());
        }

        printFooter();
    }

    //распечатать данные по биллиардному столу
    private void printFocusTable() {
        if(focusTable == null) {
            return;
        }

        System.out.println("Выбран стол:");
        My.printArr(focusTable.getPicture());
        focusTable.printSchedule();
    }

    private void setFocusTable(int num) {
        if(num >= 0 && num < Table.getCnt()) {
            focusTable = tables[num];
        }
        else {
            focusTable = null;
        }
    }

    private void resetFocusTable() {
            focusTable = null;
    }

    private boolean isFocusTable() {
        return (focusTable != null);
    }


    //проверка времени игры на корректность
    private boolean checkTimesForAdd(LocalTime timeStart, LocalTime timeEnd) {
        if(timeStart.isAfter(timeEnd)) {
            System.out.println("Время окончания игры должно быть больше, чем время начала игры");
            return false;
        }

        LocalTime timeWorkStart = LocalTime.of(Const.WORK_HOUR_START, 0);
        LocalTime timeWorkEnd = LocalTime.of(Const.WORK_HOUR_END, 0);

        if(timeWorkStart.isAfter(timeStart)    || timeWorkEnd.isBefore(timeEnd)) {
            System.out.printf("Нерабочее время. Время работы биллиарда: %s - %s \n", ForLocalTime.getStrTime(timeWorkStart), ForLocalTime.getStrTime(timeWorkEnd));
            return false;
        }

        int minutes = ForLocalTime.durationMinutes(timeStart, timeEnd);
        if( minutes <= 0 || minutes % 30 != 0) {
            System.out.println("Время игры должно быть кратно 30 минутам");
            return false;
        }

        return true;
    }


    //удалить заказ игры
    private void del() {
        if(focusTable == null) {
            My.nextCharLowerCase(sc, "Удаление записи невозможно, стол не выбран. Введите 'y' для продолжения: ",'y');
            return;
        }

        int num = My.nextInt(sc, "Введите номер заказа для удаления: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
        num--;
        Record recDel = focusTable.del(num);
        if(recDel == null) {
            My.nextCharLowerCase(sc, "Отсутствует заказ с таким номером. Введите 'y' для продолжения: ",'y');
        }
        else {
            My.nextCharLowerCase(sc, "Заказ удален. Введите 'y' для продолжения: ",'y');
        }
    }

    //добавить заказ игры
    private void add() {
        System.out.println();
        System.out.println("Заказ стола");
        System.out.println("-----------");
        //
        BilliardType billiardType;
        char type = My.nextCharLowerCase(sc, "Тип биллиарда (Р - русский, С - снукер) ",'p','р','c','с');   //русские и английские буквы
        if(type == 'p' ||  type == 'р') {
            billiardType = BilliardType.RUS;
        }
        else {
            billiardType = BilliardType.SNOOKER;
        }

        LocalTime timeStart = null;
        LocalTime timeEnd = null;

        boolean ok = false;

        while (!ok) {
            timeStart = ForLocalTime.nextLocalTime(sc, "Время начала игры(чч:мм) ");
            timeEnd = ForLocalTime.nextLocalTime(sc, "Время окончания игры(чч:мм) ");
            ok = checkTimesForAdd(timeStart, timeEnd);
        }

        //находим столы, подходящие по времени
        Table[] freeTables = freeTables(billiardType, timeStart, timeEnd);
        Table choiceTab = null;
        if(freeTables.length > 0) {
            printFreeTables(freeTables);
            choiceTab = inputTable(freeTables);
        }
        else {
            My.nextCharLowerCase(sc, "Нет доступных столов на это время. Введите 'y' для выхода ",'y');
            return;
        }

        System.out.print("Клиент: ");
        String name = sc.next();
        System.out.printf("Длительность: %s \n", ForLocalTime.strDurationHourMin(timeStart, timeEnd) );
        double price = (Const.PRICE_HOUR / 60) * ForLocalTime.durationMinutes(timeStart, timeEnd);
        System.out.printf("Стоимость: %.2f грн. (%.2f грн/ч)\n", price, Const.PRICE_HOUR);
        System.out.println();
        char saveStatus = My.nextCharLowerCase(sc, "Сохранить заказ? (Y - да, N - нет) ",'y','n');

        //забронировать стол
        if(saveStatus == 'y') {
            choiceTab.add(name, timeStart, timeEnd, price);
            My.nextCharLowerCase(sc, "Стол забронирован. Введите 'y' для продолжения ",'y');
        }
    }

    //распечатать столы, свободные в указанное время
    private void printFreeTables(Table[] arr) {
        System.out.println(".....");
        System.out.println("Свободные столы на указанное время:");
        for (Table tab : arr) {
            System.out.printf("СТОЛ %d (%s) \n", tab.getNum(), tab.getType().getShortName());
        }
        System.out.println(".....");
    }

    //выдает столы, свободные в указанное время
    private Table[] freeTables(BilliardType type, LocalTime timeStart, LocalTime timeEnd) {
        Table[] tmp = new Table[tables.length];
        int cntArr = 0;

        for (Table tab : tables) {
            if(tab.getType() == type && tab.isFreeTime(timeStart, timeEnd)) {
                tmp[cntArr] = tab;
                cntArr++;
            }
        }

        Table[] arr = new Table[cntArr];
        if (cntArr >= 0) System.arraycopy(tmp, 0, arr, 0, cntArr);

        return arr;
    }

    //ввод стола из списка
    private Table inputTable(Table[] tabs) {
        while(true) {
            System.out.print("Введите номер стола: ");
            String str = sc.next();
            if(My.isInteger(str)) {
                int num = Integer.parseInt(str);
                for (Table tmp:tabs) {
                    if(tmp.getNum() == num) {
                        return tmp;
                    }
                }
            }
        }
    }

    //Распечатать свободное время биллиардных столов
    private void printFreeTime() {
        BilliardType billiardType;
        char type = My.nextCharLowerCase(sc,"Тип биллирда (Р - русский, С - снукер) : ",'p','р','c','c');   //русские и англ. буквы
        if(type == 'p' || type == 'р')  //англ и рус
        {
            billiardType = BilliardType.RUS;
        }
        else {
            billiardType = BilliardType.SNOOKER;
        }
        System.out.println();
        for (Table tab : tables) {
            if(tab.getType() == billiardType) {
                System.out.println("-----------------");
                System.out.printf("Стол %d (%s) \n", tab.getNum(), tab.getType().getShortName());
                tab.printFreeTime();
            }
        }
        //
        My.nextCharLowerCase(sc,"Введите 'y' для продолжения: ",'y');
    }


    private void enableMenuItems() {
        //"удалить запись из заказа" - только если выбран стол
        if(focusTable == null) {
            cmdDel.setEnable(Const.OFF);
        }
        else {
            cmdDel.setEnable(Const.ON);
        }

        //показать все столы
        if(focusTable == null) {
            cmdAllTables.setEnable(Const.OFF);
        }
        else {
            cmdAllTables.setEnable(Const.ON);
        }

    }

    //распечатать хелп
    private void printHelp() {
        String fileName = ForFiles.getFilenameWithAbsolutePatch(Const.LOCAL_PATCH, Const.FILENAME_HELP);
        ForFiles.printFromFile(fileName);
        My.nextCharLowerCase(sc, "Введите 'y' для продолжения: ",'y');

    }

    //загрузить заказы из файлов
    private void loadSchedulesFromFiles() {
        for (Table tab : tables) {
            String fileName = String.format(Const.FORMAT_FILENAME_SCHEDULE, tab.getNum());
            fileName = ForFiles.getFilenameWithAbsolutePatch(Const.LOCAL_PATCH, fileName);
            //файл для стола существует?
            if(ForFiles.isFileExists(fileName)) {
                ArrayList<Record> schedule = ForFiles.loadScheduleFromFile(fileName);
                tab.setSchedule(schedule);
            }
        }
    }

    //загрузить заказы из файлов
    private void saveSchedulesToFiles() {
        for (Table tab : tables) {
            String fileName = String.format(Const.FORMAT_FILENAME_SCHEDULE, tab.getNum());
            fileName = ForFiles.getFilenameWithAbsolutePatch(Const.LOCAL_PATCH, fileName);
            ArrayList<Record> schedule = tab.getSchedule();
            ForFiles.saveScheduleToFile(fileName, schedule);
        }
    }

}
