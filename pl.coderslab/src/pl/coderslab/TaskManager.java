package pl.coderslab;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasksManager;

    public static void main(String[] args) throws IOException {

        tasksManager = getDirectory("/home/myszczur/Workshop-1/Java/src/pl/coderslab/tasks.csv");
        Scanner scan = new Scanner(System.in);
        String scan2;
        do {
            selectOption();
            scan2 = scan.nextLine();
            switch (scan2) {
                case "add" -> addTasks();

                case "remove" -> {
                    tasksDel(tasksManager, getNumber());
                    System.out.println("Value was successfully deleted.");
                }
                case "list" -> {
                    tasksList(tasksManager);
                }
                default -> System.out.println("Please select a correct option.");
            }
        } while (!"exit".equals(scan2));
        exitAll(tasksManager, "/home/myszczur/Workshop-1/Java/src/pl/coderslab/tasks.csv");

        System.err.println("Bye Bye :(");
    }

    public static boolean numberZero(String index) {
        if (NumberUtils.isParsable(index)) {
            return Integer.parseInt(index) >= 0;
        }
        return false;
    }

    public static void tasksDel(String[][] gettDirectory, int indexofTab) {
        try {
            if (indexofTab < gettDirectory.length) {
                tasksManager = ArrayUtils.remove(gettDirectory, indexofTab);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Element not exist in tab");
        }
    }

    public static int getNumber() {
        Scanner scanner = new Scanner(System.in);
        System.err.println("Please select number to remove.");
        String number = scanner.nextLine();

        while (!numberZero(number)) {
            System.err.println("Incorrect argument passed. Please give number greater or equal 0");
            scanner.nextLine();
        }
        return Integer.parseInt(number);
    }

    public static void tasksList(String[][] gettDirectory) {

        for (int i = 0; i < gettDirectory.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < gettDirectory[i].length; j++) {
                System.out.print(gettDirectory[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void exitAll(String[][] table, String file) {

        Path exitFile = Paths.get(file);
        String[] exitTable = new String[tasksManager.length];

        for (int i = 0; i < table.length; i++) {
            exitTable[i] = String.join(",", table[i]);
        }
        try {
            Files.write(exitFile, Arrays.asList(exitTable));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void addTasks() {
        Scanner scan = new Scanner(System.in);
        tasksManager = Arrays.copyOf(tasksManager, tasksManager.length + 1);
        tasksManager[tasksManager.length - 1] = new String[3];
        System.out.println("Please add task description");
        String addTask = scan.nextLine();
        tasksManager[tasksManager.length - 1][0] = addTask;
        System.out.println("Please add task due data");
        String addData = scan.nextLine();
        tasksManager[tasksManager.length - 1][1] = addData;
        System.err.println("Is your task is imoprtant: true/false");
        String addImportant = scan.nextLine();
        tasksManager[tasksManager.length - 1][2] = addImportant;
        //System.out.println(Arrays.toString(tasksManager));
    }


    public static String[][] getDirectory(String directory) throws IOException {

        Path path = Paths.get(directory);
        String[][] gettDirectory = null;

        if (Files.exists(path)) {
            List<String> strings = Files.readAllLines(path);
            gettDirectory = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                System.arraycopy(split, 0, gettDirectory[i], 0, split.length);
            }
        } else {
            System.err.println("File does not exist");
        }
        return gettDirectory;
    }

    public static void selectOption() {

        System.out.print(ConsoleColors.BLUE + "Please select an option:\n" +
                ConsoleColors.RESET + "add\n" + "remove\n" + "list\n" + "exit\n");
    }
}

