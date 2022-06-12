package dev.volcent.secondLab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;


public class Main {
    static String workingDirectory = System.getProperty("user.dir");
    static String resourcePath = workingDirectory + "/src/main/resources/";

    public static void main(String[] args) throws FileNotFoundException {
        int numberOfThreads = Integer.parseInt(args[0]);

        // Initialising operation manager and reading input files to get initial data to process.
        ResultManager resultManager = new ResultManager();
        OperationManager operationManager = new OperationManager();
        setOperationsFromFile(resourcePath + "test1.txt", operationManager);
        setOperationsFromFile(resourcePath + "test2.txt", operationManager);

        List<Thread> threads = new ArrayList<>();
        List<DivisionRunner> threadRunners = new ArrayList<>();
        for (int i = 0; i <numberOfThreads; i += 1) {
            // Creating thread runnable and thread itself
            DivisionRunner divisionRunner = new DivisionRunner(operationManager, resultManager);
            threadRunners.add(divisionRunner);
            Thread thread = new Thread(divisionRunner);
            threads.add(thread);
            thread.start();
        }

        // Main Loop of program
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Please enter command you wish to execute.");
            System.out.println("N -> Get divisors of number.");
            System.out.println("E -> Exit the program.");
            String userInput = scanner.next();
            switch (userInput.toUpperCase(Locale.ROOT)) {
                case "N" -> {
                    System.out.println("Please enter a number you wish to get divisors of: ");
                    try {
                        long user_number = scanner.nextLong();
                        operationManager.setOperation(user_number);
                    } catch (Exception e) {
                        System.out.println("Wrong number has been passed!");
                    }
                }
                case "E" -> {
                    System.out.println("Exiting program");
                    running = false;
                }
                default -> System.out.println("Please enter correct command.");
            }
        }

        for (DivisionRunner threadRunner : threadRunners) {
            threadRunner.stop();
        }
        for (Thread thread : threads) {
            thread.interrupt();
        }

        saveResults(resultManager.getResultsMap());
    }

    public static void setOperationsFromFile(String filepath, OperationManager manager) {
        List<Long> file_input = getContentsOf(filepath);
        for (long input : file_input)
            manager.setOperation(input);
    }

    public static List<Long> getContentsOf(String filepath) {
        try {
            Scanner scanner = new Scanner(new File(filepath));
            List<Long> fileNumbers = new ArrayList<>();
            while (scanner.hasNextLong())
                fileNumbers.add(scanner.nextLong());
            return fileNumbers;
        } catch(Exception e) {
            System.err.println("No file found at path: " + filepath);
            return new ArrayList<>();
        }
    }

    public static void saveResults(Map<Long, List<Long>> results) {
        try {
            String filename = "results.txt";
            File saveFile = new File(filename);
            if (saveFile.createNewFile()) {
                System.out.println("File created: " + saveFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter fileWriter = new FileWriter(filename);
            StringBuilder lineContents = new StringBuilder("");
            for (Map.Entry<Long, List<Long>> entry : results.entrySet()) {
                lineContents.append(entry.getKey()).append(": ");
                for (Long result : entry.getValue()) {
                    lineContents.append(result).append(", ");
                }
                lineContents.append("\n");
                fileWriter.append(lineContents);
                lineContents = new StringBuilder("");
            }
            fileWriter.close();
        } catch(Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
