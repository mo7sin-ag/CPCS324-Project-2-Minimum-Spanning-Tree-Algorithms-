import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class G3 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner userInput = new Scanner(System.in);

        File inputFile = new File("input.txt");
        Scanner fileInput = new Scanner(inputFile);

        File outputFile = new File("output.txt");
        PrintWriter output = new PrintWriter(outputFile);

        System.out.println("How many lines do you want to read from the text file?");
        int lineCount = userInput.nextInt();
        System.out.println();

        System.out.println("How many patterns to be generated?");
        int patternCount = userInput.nextInt();
        System.out.println();

        System.out.println("What is the length of each pattern?");
        int patternLength = userInput.nextInt();

        StringBuilder textBuilder = new StringBuilder();
        while (fileInput.hasNext() && lineCount != 0) {
            textBuilder.append(fileInput.nextLine().toLowerCase());
            lineCount--;
        }

        String text = textBuilder.toString();
        HashSet<Character> uniqueChars = getUniqueCharacters(text);

        String[] patterns = new String[patternCount];
        for (int i = 0; i < patternCount; i++) {
            int randomIndex = (int) (Math.random() * (text.length() - patternLength));
            patterns[i] = text.substring(randomIndex, randomIndex + patternLength);
            output.println(patterns[i]);
        }
        System.out.println();
        output.close();

        System.out.println(patternCount + " patterns, each of length " + patternLength + " have been generated in a file " + outputFile.getPath());
        System.out.println();
        
        
        for (int i = 0; i < patternCount; i++) {
            System.out.println("Pattern " + i + " is " + patterns[i] + ", its shift table is:");
            printShiftTable(shiftTable(patterns[i], uniqueChars));
        }


        long totalTimeBruteForce = 0,averageTimeBruteForce;
        for (int i = 0; i < patternCount; i++) {
            long start = System.nanoTime();
            bruteForceStringMatch(text, patterns[i]);
            long end = System.nanoTime();
            totalTimeBruteForce += (end - start);
        }
        averageTimeBruteForce = totalTimeBruteForce / patternCount;
        System.out.println("Average time (nanoseconds) of search in Brute Force Approach: " + averageTimeBruteForce);
        System.out.println();

        long totalTimeHorspool = 0, averageTimeHorspool;
        for (int i = 0; i < patternCount; i++) {
            
            HashMap<Character, Integer> s= shiftTable(patterns[i],uniqueChars);
            long start = System.nanoTime();

            horspoolMatching(text,s, patterns[i]);
            long end = System.nanoTime();
            totalTimeHorspool += (end - start);
        }
        averageTimeHorspool = totalTimeHorspool / patternCount;
        System.out.println("Average time (nanoseconds) of search in Horspool Approach: " + averageTimeHorspool);
        System.out.println();

        if (averageTimeBruteForce < averageTimeHorspool) {
            System.out.println("For this instance Brute Force approach is better than Horspool approach.");
        } else {
            System.out.println("For this instance Horspool approach is better than Brute Force approach.");
        }
    }

    public static HashSet<Character> getUniqueCharacters(String text) {
        HashSet<Character> uniqueChars = new HashSet<>();
        for (char c : text.toCharArray()) {
            uniqueChars.add(c);
        }
        return uniqueChars;
    }

    public static HashMap<Character, Integer> shiftTable(String pattern, HashSet<Character> uniqueChars) {
        int m = pattern.length();
        HashMap<Character, Integer> table = new HashMap<>();

        for (char c : uniqueChars) {
            table.put(c, m);
        }

        for (int j = 0; j <= m - 2; j++) {
            table.put(pattern.charAt(j), m - 1 - j);
        }

        return table;
    }

    // Print Shift Table For Pattern.
    public static void printShiftTable(HashMap<Character, Integer> table) {
        for (char c : table.keySet()) {
            System.out.print("\t" + c + " ");
        }
        System.out.println();

        for (char c : table.keySet()) {
            System.out.print("\t" + table.get(c) + " ");
        }
        System.out.println();
        System.out.println();
    }

    public static int bruteForceStringMatch(String text, String pattern) {
        int n = text.length(), m = pattern.length();

        for (int i = 0; i <= n - m; i++) {
            int j = 0;

            while (j < m && pattern.charAt(j) == text.charAt(i + j)) {
                j++;
            }

            if (j == m) {
                return i;
            }
        }

        return -1;
    }
    
    public static int horspoolMatching(String text,HashMap<Character, Integer> table,String pattern) {
        int n = text.length(), m = pattern.length();

        int i = m - 1;

        while (i <= n - 1) {
            int k = 0;

            while (k <= m - 1 && pattern.charAt(m - 1 - k) == text.charAt(i - k)) {
                k++;
            }

            if (k == m) {
                return i - m - 1;
            } else {
                //i += table.getOrDefault(text.charAt(i), m);
                    i += table.get(text.charAt(i));
            }
        }

        return -1;
    }
    

}
