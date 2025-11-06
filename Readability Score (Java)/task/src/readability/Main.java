package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine().trim();

        // Split sentences by ., !, or ?
        String[] sentences = text.split("[.!?]");
        int totalWords = 0;
        int sentenceCount = 0;

        for (String sentence : sentences) {
            sentence = sentence.trim();
            if (sentence.isEmpty()) continue; // skip empty parts (e.g., after last punctuation)

            // Split words by one or more spaces
            String[] words = sentence.split("\\s+");
            totalWords += words.length;
            sentenceCount++;
        }

        // Avoid division by zero
        if (sentenceCount == 0) {
            System.out.println("EASY");
            return;
        }

        double averageWords = (double) totalWords / sentenceCount;

        if (averageWords > 10) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }
    }
}
