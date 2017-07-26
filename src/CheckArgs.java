
public class CheckArgs {
    private boolean numArgs(String[] args) { //проверка на кол-во введенных параметров
        if (args.length != 4) {
            return false;
        } else {
            return true;
        }
    }

    private boolean zeroArgs(String[] args) { //проверка на корректность 0 параметра
        if (!args[0].equals("in.txt")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean firstArgs(String[] args) {//проверка на корректность 1 параметра
        if (!args[1].equals("out.txt")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean secondArgs(String[] args) {//проверка на корректность 2 параметра
        if ((!args[2].equals("-i")) && (!args[2].equals("-s"))) {
            return false;
        }  else {
            return true;
        }
    }

    private boolean thirdArgs(String[] args) {//проверка на корректность 3 параметра
        if ((!args[3].equals("-a")) && (!args[3].equals("-d"))) {
            return false;
        }
        return true;
    }

    public boolean check(String[] args) {
        if (numArgs(args)) {
            if (zeroArgs(args)) {
                if (firstArgs(args)) {
                    if (secondArgs(args)) {
                        if (thirdArgs(args)) {
                            return true;
                        } else {
                            System.out.println("Неверно указан режим сортировки файла : " + args[3]);
                        }
                    } else {
                        System.out.println("Неверно указан тип содержимого файла : " + args[2]);
                    }
                } else {
                    System.out.println("Неверно указано имя выходного файла : " + args[1]);
                }
            } else {
                System.out.println("Неверно указано имя входного файла : " + args[0]);
            }
        } else {
            System.out.println("Некорректное число входных параметров");
        }
        return false;
    }
}
