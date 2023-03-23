import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Dictionary {

    private static ArrayList<DictionaryEntry> dictionary = new ArrayList<>();

    private static void populateDictionary(String fileName) throws IOException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] entry = line.split(" - ");
            DictionaryEntry newEntry = new DictionaryEntry(entry[0], entry[1]);
            int usedWord = dictionary.indexOf(entry);
            if (usedWord != -1) {
                dictionary.get(usedWord).setDefinition(entry[1]);
            } else {
                dictionary.add(newEntry);
            }
        }
    }

    private static void dictionarySearch(String search) {
        int matches = 0;
        for (int i = 0; i < dictionary.size() - 1; i++) {
            String word = dictionary.get(i).getWordOrPhrase();
            if (word.toLowerCase().startsWith(search.toLowerCase())) {
                matches++;
            }
        }
        if (matches == 0) {
            System.out.println("No phrase starting with " + search + " was found");
        } else {
            System.out.println("Results for search term: \n");
            for (int i = 0; i < dictionary.size() - 1; i++) {
                String word = dictionary.get(i).getWordOrPhrase();
                if (word.toLowerCase().startsWith(search.toLowerCase())) {
                    System.out.println(word + " - " + dictionary.get(i).getDefinition());
                }
            }
        }
    }

    private static void dictionaryDelete(String delete) {
        int matches = 0;
        for (int i = 0; i < dictionary.size() - 1; i++) {
            String word = dictionary.get(i).getWordOrPhrase();
            if (word.equals(delete)) {
                dictionary.remove(i);
                System.out.println("Successfully deleted '" + delete + "' from dictionary");
                return;
            } else if (word.toLowerCase().startsWith(delete.toLowerCase())) {
                matches++;
            }
        }
        if (matches == 0) {
            System.out.println("No matches were found for " + delete);
        } else {
            System.out.println("Multiple matches were found with the phrase: " + delete);
            for (int i = 0; i < dictionary.size() - 1; i++) {
                String word = dictionary.get(i).getWordOrPhrase();
                if (word.toLowerCase().startsWith(delete.toLowerCase())) {
                    System.out.println(word + " - " + dictionary.get(i).getDefinition());
                }
            }
        }
    }

    private static void dictionaryAdd(String add, String definition) {
        DictionaryEntry entry = new DictionaryEntry(add, definition);
        for (int i = 0; i < dictionary.size() - 1; i++) {
            String word = dictionary.get(i).getWordOrPhrase();
            if (word.equalsIgnoreCase(add)) {
                System.out.println("The word '" + word + "' already exists in the dictionary");
                return;
            }
        }
        dictionary.add(entry);
        Collections.sort(dictionary);
        System.out.println("Successfully added '" + entry.getWordOrPhrase() + "' to the dictionary");
    }

    private static void dictionaryUpdate(String update, String definition) {
        for (int i = 0; i < dictionary.size() - 1; i++) {
            String word = dictionary.get(i).getWordOrPhrase();
            if (word.equalsIgnoreCase(update)) {
                dictionary.get(i).setDefinition(definition);
                System.out.println("'" + word + "' successfully updated");
                return;
            }
        }
        System.out.println("No exact match was found for '" + update + "' was found");
    }

    private static void dictionaryClose(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.createNewFile()) {
            System.out.println("File already exists");
            return;
        }
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < dictionary.size() - 1; i++) {
                String word = dictionary.get(i).getWordOrPhrase();
                String def = dictionary.get(i).getDefinition();
                writer.write(word + " - " + def + "\n");

            }
            writer.close();
            System.out.println("Successfully wrote to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        boolean getFiles = false;
        while (!getFiles) {
            System.out.print("Enter Filename or 'continue': ");
            String fileName = input.nextLine();
            if (fileName.equals("continue")) {
                getFiles = true;
            } else {
                try {
                    populateDictionary(fileName);
                } catch (IOException e) {
                    System.out.println("File not found: " + fileName);
                }
            }
        }
        Collections.sort(dictionary);
        boolean done = false;
        while (!done) {
            System.out.print("\nEnter an option S (search), D (delete), A (add), U (update), or C (close): ");
            String choice = input.nextLine();
            switch (choice) {
                case "s":
                case "S":
                    System.out.println("Enter search term: ");
                    String searchTerm = input.nextLine().strip();
                    dictionarySearch(searchTerm);
                    break;
                case "d":
                case "D":
                    System.out.println("Enter word or phrase to delete: ");
                    String deleteTerm = input.nextLine().strip();
                    dictionaryDelete(deleteTerm);
                    break;
                case "a":
                case "A":
                    System.out.println("Enter word or phrase to add: ");
                    String add = input.nextLine().strip();
                    System.out.println("Enter the definition of the word or phrase: ");
                    String def = input.nextLine().strip();
                    dictionaryAdd(add, def);
                    break;
                case "u":
                case "U":
                    System.out.println("Enter word or phrase to be updated: ");
                    String update = input.nextLine().strip();
                    System.out.println("Enter a new definition for the word or phrase: ");
                    String definition = input.nextLine().strip();
                    dictionaryUpdate(update, definition);
                    break;
                case "c":
                case "C":
                    System.out.println("Input a filename (either an existing one to override or a new text file)");
                    System.out.println("Enter filename: ");
                    String fileName = input.nextLine();
                    while (!fileName.endsWith(".txt")) {
                        System.out.println("Invalid filename, please end filename with '.txt': ");
                        fileName = input.nextLine();
                    }
                    dictionaryClose(fileName);
                    done = true;
                    break;
                default:
                    System.out.println("Not a valid option");
                    break;
            }

        }
    }

}
