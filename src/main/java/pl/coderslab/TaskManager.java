package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) {
        loadDataToTab(FILE_NAME);
        choseOption();
    }

    public static void loadDataToTab(String fileName) {
        Path filePath = Paths.get(fileName);

        // Sprawdzanie, czy plik istnieje
        if (!Files.exists(filePath)) {
            System.out.println("File does not exist: " + fileName);
            return;  // Zwracamy, jeśli plik nie istnieje
        }

        try {
            // Odczytanie wszystkich linii z pliku
            List<String> lines = Files.readAllLines(filePath);
            // Tworzenie tablicy o odpowiednim rozmiarze
            tasks = new String[lines.size()][];
            // Przetwarzanie każdej linii
            for (int i = 0; i < lines.size(); i++) {
                // Rozdzielenie linii na elementy (kolumny)
                tasks[i] = lines.get(i).split(",");  // Używamy od razu do przypisania
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static void choseOption() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printOptions(OPTIONS); // Wyświetl menu z opcjami
            String input = scanner.nextLine().trim();

            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    listTasks(tasks);
                    break;
                case "exit":
                    if (tasks != null) {
                        saveTabToFile(FILE_NAME, tasks);
                    }
                    System.out.println(ConsoleColors.RED + "Exiting the program...Nara");
                    return;
                default:
                    System.out.println(ConsoleColors.RED + "Please select a correct option.");
            }
        }
    }

    public static void saveTabToFile(String fileName, String[][] tab) {
        Path path = Paths.get(fileName);

        // Przygotowanie danych do zapisania
        String[] lines = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }

        // Zapis do pliku z użyciem BufferedWriter
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println(ConsoleColors.BLUE + "Dane zostały zapisane do pliku: " + fileName);
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisywania do pliku: " + e.getMessage());
        }
    }

    public static void listTasks(String[][] tab) {
        if (tab == null || tab.length == 0) {
            System.out.println("No tasks to display.");
            return;
        }

        for (int i = 0; i < tab.length; i++) {
            System.out.println(i + " : " + String.join(" ", tab[i]));
        }
    }

    private static void removeTask() {
        if (tasks == null || tasks.length == 0) {
            System.out.println("No tasks to remove.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the task number to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index < 0 || index >= tasks.length) {
            System.out.println("Invalid index. Please provide a valid task number.");
            return;
        }

        // Usunięcie elementu o określonym indeksie
        tasks = ArrayUtils.remove(tasks, index);
        System.out.println("Task removed successfully.");
    }

    private static void addTask() {
        Scanner scanner = new Scanner(System.in);

        // Pobieranie danych za pomocą osobnej metody
        String description = getInput(scanner, "Please enter the task description: ");
        String dueDate = getInput(scanner, "Please enter the task due date (yyyy-mm-dd): ");
        String important = getInput(scanner, "Is your task important (true/false): ");

        // Rozszerzenie tablicy i dodanie nowego zadania
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[]{description, dueDate, important};
    }

    // Pomocnicza metoda do pobierania danych
    private static String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim(); // Usunięcie zbędnych spacji
    }
}