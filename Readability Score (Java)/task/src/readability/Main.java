package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    // --- 1. Metric Calculation and Helper Methods ---

    // Helper method to count syllables based on the provided rules
    private static int countSyllables(String word) {
        word = word.toLowerCase().replaceAll("[.,!?:;]", "");
        if (word.length() == 0) return 0;

        int count = 0;
        boolean isPreviousVowel = false;
        String vowels = "aeiouy";

        // Rule 3: Do not count 'e' at the end of a word
        if (word.length() > 1 && word.endsWith("e")) {
            word = word.substring(0, word.length() - 1);
        }

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            boolean isCurrentVowel = vowels.indexOf(ch) != -1;

            if (isCurrentVowel) {
                // Rule 2: Do not count double-vowels
                if (!isPreviousVowel) {
                    count++;
                }
            }
            isPreviousVowel = isCurrentVowel;
        }

        // Rule 4: If the count is 0, consider it a 1-syllable word
        return count == 0 ? 1 : count;
    }

    // Class to store all calculated metrics
    static class TextMetrics {
        int words;
        int sentences;
        int characters;
        int syllables;
        int polysyllables; // Words with 3 or more syllables

        public TextMetrics(String text) {
            calculateMetrics(text);
        }

        private void calculateMetrics(String text) {
            String trimmed = text.trim();
            this.characters = trimmed.replaceAll("[\\s\\n\\t]", "").length();

            // Word and Sentence Count
            String[] wordList = trimmed.isEmpty() ? new String[0] : trimmed.split("\\s+");
            this.words = wordList.length;

            String[] sentenceParts = text.split("[.!?]");
            this.sentences = 0;
            for (String part : sentenceParts) {
                if (!part.trim().isEmpty()) {
                    this.sentences++;
                }
            }
            // Handle case where text has words but no ending punctuation
            if (this.sentences == 0 && this.words > 0) {
                this.sentences = 1;
            }

            // Calculate Syllables and Polysyllables
            this.syllables = 0;
            this.polysyllables = 0;
            for (String word : wordList) {
                String cleanWord = word.replaceAll("[.,!?:;]", "");
                if (cleanWord.isEmpty()) continue;

                int s = countSyllables(cleanWord);
                this.syllables += s;
                if (s >= 3) {
                    this.polysyllables++;
                }
            }
        }
    }

    // --- 2. Score Calculation Methods ---

    private static double calculateARI(TextMetrics metrics) {
        if (metrics.words == 0 || metrics.sentences == 0) return 0.0;
        return 4.71 * ((double) metrics.characters / metrics.words)
                + 0.5 * ((double) metrics.words / metrics.sentences)
                - 21.43;
    }

    private static double calculateFK(TextMetrics metrics) {
        if (metrics.words == 0 || metrics.sentences == 0) return 0.0;
        return 0.39 * ((double) metrics.words / metrics.sentences)
                + 11.8 * ((double) metrics.syllables / metrics.words)
                - 15.59;
    }

    private static double calculateSMOG(TextMetrics metrics) {
        if (metrics.sentences == 0 || metrics.polysyllables == 0) return 0.0;
        double factor = (double) metrics.polysyllables * (30.0 / metrics.sentences);
        return 1.043 * Math.sqrt(factor) + 3.1291;
    }

    private static double calculateCL(TextMetrics metrics) {
        if (metrics.words == 0) return 0.0;
        double L = ((double) metrics.characters / metrics.words) * 100.0;
        double S = ((double) metrics.sentences / metrics.words) * 100.0;
        return 0.0588 * L - 0.296 * S - 15.8;
    }

    // Helper to map score to the upper bound of the age range
    private static int scoreToAge(double score) {
        int index = (int) Math.round(score);
        if (index < 1) index = 1;
        if (index > 14) index = 14;

        switch (index) {
            case 1:  return 6;
            case 2:  return 7;
            case 3:  return 8;
            case 4:  return 9;
            case 5:  return 10;
            case 6:  return 11;
            case 7:  return 12;
            case 8:  return 13;
            case 9:  return 14;
            case 10: return 15;
            case 11: return 16;
            case 12: return 17;
            case 13: return 18;
            case 14: return 22;
            default: return 0;
        }
    }

    // --- 3. Main Logic ---

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text;

        try {
            if (args.length == 0) {
                System.out.println("Error: Please provide a filename as a command-line argument.");
                return;
            }
            text = new String(Files.readAllBytes(Paths.get(args[0])));
        } catch (IOException e) {
            System.out.println("Error: cannot read file.");
            return;
        }

        // Calculate all metrics
        TextMetrics metrics = new TextMetrics(text);

        // Display basic statistics
        System.out.println("The text is:\n" + text);
        System.out.println("Words: " + metrics.words);
        System.out.println("Sentences: " + metrics.sentences);
        System.out.println("Characters: " + metrics.characters);
        System.out.println("Syllables: " + metrics.syllables);
        System.out.println("Polysyllables: " + metrics.polysyllables);

        // Get user input for score calculation
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String choice = scanner.nextLine().toLowerCase().trim();

        // Calculate all scores
        double scoreARI = calculateARI(metrics);
        double scoreFK = calculateFK(metrics);
        double scoreSMOG = calculateSMOG(metrics);
        double scoreCL = calculateCL(metrics);

        double totalAge = 0.0;
        int scoreCount = 0;

        if (choice.equals("ari") || choice.equals("all")) {
            int age = scoreToAge(scoreARI);
            System.out.printf("Automated Readability Index: %.2f (about %d year olds).%n", scoreARI, age);
            totalAge += age;
            scoreCount++;
        }
        if (choice.equals("fk") || choice.equals("all")) {
            int age = scoreToAge(scoreFK);
            System.out.printf("Flesch-Kincaid readability tests: %.2f (about %d year olds).%n", scoreFK, age);
            totalAge += age;
            scoreCount++;
        }
        if (choice.equals("smog") || choice.equals("all")) {
            int age = scoreToAge(scoreSMOG);
            System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d year olds).%n", scoreSMOG, age);
            totalAge += age;
            scoreCount++;
        }
        if (choice.equals("cl") || choice.equals("all")) {
            int age = scoreToAge(scoreCL);
            System.out.printf("Coleman-Liau index: %.2f (about %d year olds).%n", scoreCL, age);
            totalAge += age;
            scoreCount++;
        }

        // Calculate and print average age if "all" was chosen
        if (choice.equals("all") && scoreCount > 0) {
            double averageAge = totalAge / scoreCount;
            System.out.println();
            System.out.printf("This text should be understood in average by %.2f year olds.%n", averageAge);
        }
    }
}
