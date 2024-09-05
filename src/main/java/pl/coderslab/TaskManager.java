package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static void main(String[] args) {
        printOptions(OPTIONS);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "Exiting the program...Nara");
                    return;
                default:
                    System.out.println("Please select a correct option.");
            }
        }


    }

    private static void listTasks() {

    }

    private static void removeTask() {

    }

    private static void addTask() {

    }
}


