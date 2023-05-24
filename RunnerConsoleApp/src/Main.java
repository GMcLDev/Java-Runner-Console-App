import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

@SuppressWarnings("SpellCheckingInspection")
public class Main {
    public static Scanner in = new Scanner(System.in);
    public static String[][] runners = new String[16][3];

    public static void main(String[] args) {

        login();
    }
    private static void login() {

        // Set Password
        String password = "clyderunners";

        // User password attempts
        int attempts = 3;


        // Welcome Message
        System.out.println("Welcome to Glasgow Clyde Runners Club.");
        System.out.println("Please enter your password to continue: ");

        // User password validation loop
        do {
            System.out.println("Enter Password: ");
            String passwordAttempt = in.nextLine();

            if (passwordAttempt.equals(password)) {
                System.out.println("Password Validated");

                // Reads txt file then readFile calls menu
                readFile();

                // Reduce attempts if password incorrect
            } else {
                attempts--;
                System.out.println("Your Password is incorrect");
                System.out.println("You have: " + attempts + " attempts left.");

            }
        } while (attempts != 0);

        // If attempt exceeded exit program
        System.out.println("Number of attempts exceeded. You are now locked out.");
        System.exit(0);
    }
    private static void readFile() {

        // Path of txt file
        String File_Path = "src/raceResults1.txt";

        // File exception handling
        try {

            // Retrieve file
            Scanner in = new Scanner(new File(File_Path));

            // Iterate each line in file
            while (in.hasNextLine()) {
                // Creates 'runner' for each index of runners and adds each value in each line
                for (String[] runner : runners) {
                    String[] line = in.nextLine().split(" ");
                    System.arraycopy(line, 0, runner, 0, line.length);
                }
            }

            for (int i = 0; i < runners.length; i++) {
                // Sorts runners array from fastest to slowest
                for (int j = 1; j < runners.length; j++) {
                    if (parseInt(runners[j-1][2]) > parseInt(runners[j][2])) {
                        String[] temp = runners[j-1];
                        runners[j-1] = runners[j];
                        runners[j] = temp;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        menu();

    }
    @SuppressWarnings("InfiniteRecursion")
    private static void menu() {

        // Menu options
        System.out.println("Enter Choice: ");
        System.out.println("1: Names");
        System.out.println("2: Times");
        System.out.println("3: Fastest and Slowest Time");
        System.out.println("4: Search");
        System.out.println("5: Display all data and Create a sorted txt file");
        System.out.println("6: Exit");

        // User input: menu option selection
        String menuOption;

        // Input validation, checks if integer
        do {
            menuOption = in.nextLine();
            if (!menuOption.matches("^[0-9]\\d*$")) {
                System.out.println("Invalid input. Please enter a Number");
                menuOption = "";
            } else if (parseInt(menuOption) > 6 || parseInt(menuOption) < 1) {
                System.out.println("Please choose options 1-6");
                menuOption = "";
            }
        } while (menuOption.equals(""));

        // Menu options
        switch (Integer.parseInt(menuOption)) {
            case 1 -> getNames();
            case 2 -> getTimes();
            case 3 -> fastestandSlowest();
            case 4 -> search();
            case 5 -> output();
            case 6 -> System.exit(0);
        }
        System.out.println("\n");
        menu();
    }
    private static void getNames() {

        // Iterates runners and prints each first and last name
        for (String[] runner : runners) {
            System.out.println(runner[0] + " " + runner[1]);

        }
    }
    private static void getTimes() {

        // Iterates runners and prints each time
        for (String[] runner : runners) {
            System.out.println(runner[2]);
        }
    }
    private static void fastestandSlowest() {

        // I have combined menu options 3 and 4 from the requirements to one option
        // runners array has been sorted so prints the first (fastest) runners name and time
        System.out.println("Fastest Time: " + Arrays.toString(runners[0]));
        // runners array has been sorted so calls the last (slowest) runners name and time
        System.out.println("Slowest Time: " + Arrays.toString(runners[15]));

    }
    @SuppressWarnings("InfiniteRecursion")
    private static void search() {
        // Search menu choice
        String choice;

        // Search menu options
        System.out.println("1: Search Name");
        System.out.println("2: Search Time");
        System.out.println("3: Back");

        // User input validation
        do {
            choice = in.nextLine();
            if (!choice.matches("^[1-9]\\d*$")) {
                System.out.println("Invalid input. Please enter a Number");
                choice = "";
            } else if (parseInt(choice) > 3) {
                System.out.println(" Make a selection between 1-3");
                choice = "";
            }
        } while (choice.equals(""));


        // Pattern Occurrences
        int occurrences = 0;

        // search names
        if (parseInt(choice) == 1) {

            // Name pattern
            String pattern;
            System.out.println("Enter letters from first OR last name (Not case sensitive): ");

            // Input validation for name pattern loop
            do {
                pattern = in.nextLine();
                if (!pattern.matches("[a-zA-Z]+$")) {
                    System.out.println("Invalid input. Please enter a string.");
                    pattern = "";
                }
            } while (pattern.isEmpty());

            // Matches pattern to first and last name but isn't case-sensitive so can search partial name
            for (String[] runner : runners) {
                if (runner[0].toLowerCase().contains(pattern.toLowerCase()) || runner[1].toLowerCase().contains(pattern.toLowerCase())) {
                    occurrences++;
                    System.out.println("Occurence " + occurrences + " : " + Arrays.toString(runner));
                }
            }

            // search times
        } else if (parseInt(choice) == 2) {

            // Number Pattern
            String numberPattern;
            System.out.println("Enter time in seconds: ");

            // Search times input validation loop
            do {
                numberPattern = in.nextLine();
                if (!numberPattern.matches("^[1-9]\\d*$")) {
                    System.out.println("Invalid input. Please enter a Number");
                    numberPattern = "";
                }
            } while (numberPattern.equals(""));


            // Iterate and compare number pattern through times
            for (String[] runner: runners) {

                if (numberPattern.equals(runner[2])) {
                    occurrences++;
                    System.out.println("occurrence: " + occurrences + " : " + Arrays.toString(runner));
                }
            }

        } else if (parseInt(choice) == 3) {
            menu();
        }
        System.out.println("Total Occurrences: " + occurrences);
        System.out.println("\n");
        search();


    }
    public static void output() {

        // exception handling
        try {

            // Name location and name of file to create
            PrintWriter out = new PrintWriter("src/runnersSorted.txt");

            // Print to file
            out.print("Clyde Runners sorted from fastest to slowest \n");

            out.print("\n");
            out.print("The fastest person is: " + Arrays.toString(runners[0]));
            out.print("\n");
            out.print("\n");

            // Get and print Date
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();

            out.print("\n");
            out.print("Created on: " + formatter.format(date));
            out.print("\n");


            // Print to console
            System.out.print("Clyde Runners sorted from fastest to slowest \n");
            System.out.print("\n");
            System.out.print("The fastest person is: " + Arrays.toString(runners[0]));
            System.out.print("\n");
            System.out.print("\n");

            //Print date to console
            System.out.print("\n");
            System.out.print("Created on: " + formatter.format(date));


            // Prints details for each runner in the sorted array to file and console
            for (String[] runner : runners) {
                out.print(Arrays.toString(runner) + "\n");

                System.out.println(Arrays.toString(runner));
            }

            out.close();
        } catch (Exception e) {
            // output message to the console if error occurs
            System.out.println("Unexpected error: Please run the program again.");
        }
    }
}