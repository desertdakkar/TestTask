import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {

    private String inPath = "txtFiles//in.txt";
    private String outPath = "txtFiles//out.txt";
    private ArrayList<Long> integerArrayList = new ArrayList<>();
    private ArrayList symbolsArray = new ArrayList();

    CheckArgs checkArgs = new CheckArgs();

    public Controller (String[] args) {
        if (checkArgs.check(args)) {
            if (checkFile()) {
                if (((args[2].equals("-i") && args[3].equals("-a")) || (args[2].equals("-i") && args[3].equals("-d")))) {
                    sortIntegers(args);
                } else if (args[2].equals("-s") && args[3].equals("-a")) {
                    sortSymbols(args);
                } else {
                    System.out.println("Неверно указан режим сортировки для строк : " + args[3]);
                }
            }
        }
    }

    private boolean checkFile() {
        File file = new File(inPath);
        if (!file.exists()) {
            System.out.println("Файла не существует по адрессу : " + inPath);
            return false;
        } else {
            if (file.length() == 0) {
                System.out.println("Текстовый файл пуст");
                return false;
            } else {
                if (!checkSpacesInFile()) {
                    System.out.println("В текстовом файле есть пробелы");
                    return false;
                } else return true;
            }
        }
    }

    private boolean checkSpacesInFile() {
        try {
            FileReader fileReader = new FileReader(inPath);
            int ch;
            while ((ch = fileReader.read()) != -1) {
                if ((char)ch == ' ') {
                    return false;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    private boolean checkIntegerInTxt() {
        try {
            FileReader fileReader = new FileReader(inPath);
            int ch;
            while ((ch = fileReader.read()) != -1) {
                if (!Character.isDigit(ch) && (char)ch != 13 && (char)ch != 10) {
                    return false;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    private void addSymbolsInArray() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inPath));
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                symbolsArray.add(string);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void addIntegersInArray() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inPath));
            String string;
            long temp;
            while ((string = bufferedReader.readLine()) != null) {
                temp = Long.parseLong(string);
                integerArrayList.add(temp);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void insertSort(String[] args) {
        if ( (args[2].equals("-i") && args[3].equals("-a")) || (args[2].equals("-i") && args[3].equals("-d")) ) {
            int out, in;
            for (out = 1; out < integerArrayList.size(); out++) {
                long temp = integerArrayList.get(out);
                in = out;
                if (args[2].equals("-i") && args[3].equals("-a")) {
                    while (in > 0 && integerArrayList.get(in - 1) >= temp) {
                        integerArrayList.set(in, integerArrayList.get(in-1));
                        in--;
                    }
                    integerArrayList.set(in, temp);
                } else if (args[2].equals("-i") && args[3].equals("-d")) {
                    while (in > 0 && integerArrayList.get(in - 1) <= temp) {
                        integerArrayList.set(in, integerArrayList.get(in - 1));
                        in--;
                    }
                    integerArrayList.set(in, temp);
                }
            }
        } else if (args[2].equals("-s") && args[3].equals("-a")) {
            Collections.sort(symbolsArray);
        }

    }

    private void writeIntInTxt() { //запись отсортированных целых чисел в текстовый файл
        File file =  new File(outPath);
        try {
            if(file.exists()){
                file.delete();
                file.createNewFile();
            } else file.createNewFile();
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                for (int i = 0; i < integerArrayList.size(); i++) {
                    if (i == integerArrayList.size() - 1) {
                        out.print(integerArrayList.get(i));
                    } else {
                        out.println(integerArrayList.get(i));
                    }
                }
            } finally {
                out.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void writeSymbInTxt() { //запись отсортированных строк в текстовый файл
        File file =  new File(outPath);
        try {
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else file.createNewFile();
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                for (int i = 0; i < symbolsArray.size(); i++) {
                    if (i == symbolsArray.size() - 1) {
                        out.print(symbolsArray.get(i));
                    } else {
                        out.println(symbolsArray.get(i));
                    }
                }
            } finally {
                out.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void sortIntegers(String[] args) {//запуск сортировки для целых чисел по возрастанию
        if (checkIntegerInTxt()) {
            addIntegersInArray();
            insertSort(args);
            writeIntInTxt();
            System.out.println("Сортировка прошла успешно");
        } else System.out.println("В текстовом файле кроме целых чисел, присутствуют другие символы");
    }

    private void sortSymbols(String[] args) {
        addSymbolsInArray();
        insertSort(args);
        writeSymbInTxt();
        System.out.println("Сортировка прошла успешно");
    }


}
