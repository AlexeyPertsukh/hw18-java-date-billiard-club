package prog;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ForFiles {
    private ForFiles() {
    }

    public static boolean printFromFile(String filename) {
        try
        {
            FileReader fr= new FileReader(filename);
            Scanner scan = new Scanner(fr);
            System.out.println();
            while (scan.hasNextLine()) {
                System.out.println(scan.nextLine());
            }
            System.out.println();
            System.out.println(filename);
            fr.close();
            return true;
        }
        catch(IOException ex){

            System.out.println("Ошибка при открытии файла " + filename);
        }
        return false;
    }


    public static String getFilenameWithAbsolutePatch(String localPatch, String fileName) {
        String path = new java.io.File(".").getAbsolutePath();
        return path + localPatch + fileName;
    }


    //файл существует?
    public static boolean isFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }


    public static void saveScheduleToFile(String filename, ArrayList<Record> list)  {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            objectOutputStream.writeObject(list);

        }
        catch (IOException ex) {
            System.err.println("Ошибка сохранения в файл " + filename);
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Record> loadScheduleFromFile(String filename) {
        ArrayList<Record> list;
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename)))
        {
            list = (ArrayList<Record>) objectInputStream.readObject();
            return list;
        }
        catch (Exception ex) {
            System.err.println("Не удалось прочитать файл " + filename);
            return null;
        }
    }



}
