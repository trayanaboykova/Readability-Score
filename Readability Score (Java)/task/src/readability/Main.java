package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String text;

        try {
            text = new String(Files.readAllBytes(Paths.get(args[0])));
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: cannot read file.");
            return;
        }

        System.out.println("The text is:");
        System.out.println(text);
        System.out.println();

        int characters = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (!Character.isWhitespace(ch)) {
                characters++;
            }
        }

        String trimmed = text.trim();
        String[] wordsArray = trimmed.isEmpty() ? new String[0] : trimmed.split("\\s+");
        int words = wordsArray.length;

        String[] sentenceParts = text.split("[.!?]");
        int sentences = 0;
        for (String part : sentenceParts) {
            if (!part.trim().isEmpty()) {
                sentences++;
            }
        }

        int syllables = 0;
        int polysyllables = 0;
        for (String w : wordsArray) {
            int s = countSyllables(w);
            syllables += s;
            if (s > 2) {
                polysyllables++;
            }
        }

        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String choice = scanner.next().trim();

        // *** IMPORTANT: extra blank line like in the example ***
        System.out.println();

        double ariScore = 4.71 * (characters * 1.0 / words)
                + 0.5  * (words * 1.0 / sentences)
                - 21.43;

        double fkScore = 0.39 * (words * 1.0 / sentences)
                + 11.8 * (syllables * 1.0 / words)
                - 15.59;

        double smogScore = 1.043 * Math.sqrt(polysyllables * 30.0 / sentences)
                + 3.1291;

        double L = characters * 100.0 / words;
        double S = sentences * 100.0 / words;
        double clScore = 0.0588 * L - 0.296 * S - 15.8;

        int ariAge = ageFromScore(ariScore);
        int fkAge = ageFromScore(fkScore);
        int smogAge = ageFromScore(smogScore);
        int clAge = ageFromScore(clScore);

        double ageSum = 0;
        int ageCount = 0;

        if ("ARI".equalsIgnoreCase(choice) || "all".equalsIgnoreCase(choice)) {
            System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).%n", ariScore, ariAge);
            ageSum += ariAge;
            ageCount++;
        }
        if ("FK".equalsIgnoreCase(choice) || "all".equalsIgnoreCase(choice)) {
            System.out.printf("Flesch\u2013Kincaid readability tests: %.2f (about %d-year-olds).%n", fkScore, fkAge);
            ageSum += fkAge;
            ageCount++;
        }
        if ("SMOG".equalsIgnoreCase(choice) || "all".equalsIgnoreCase(choice)) {
            System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).%n", smogScore, smogAge);
            ageSum += smogAge;
            ageCount++;
        }
        if ("CL".equalsIgnoreCase(choice) || "all".equalsIgnoreCase(choice)) {
            System.out.printf("Coleman\u2013Liau index: %.2f (about %d-year-olds).%n", clScore, clAge);
            ageSum += clAge;
            ageCount++;
        }

        if ("all".equalsIgnoreCase(choice) && ageCount > 0) {
            double avgAge = ageSum / ageCount;
            System.out.printf("%nThis text should be understood in average by %.2f-year-olds.%n", avgAge);
        }
    }

    private static int countSyllables(String word) {
        if (word == null || word.isEmpty()) {
            return 0;
        }
        String w = word.toLowerCase().replaceAll("[^a-z]", "");
        if (w.isEmpty()) {
            return 0;
        }

        String vowels = "aeiouy";
        int count = 0;
        boolean prevVowel = false;

        for (int i = 0; i < w.length(); i++) {
            char c = w.charAt(i);
            boolean isVowel = vowels.indexOf(c) >= 0;
            if (isVowel && !prevVowel) {
                count++;
            }
            prevVowel = isVowel;
        }

        if (w.endsWith("e") && count > 1) {
            count--;
        }
        if (count == 0) {
            count = 1;
        }
        return count;
    }

    private static int ageFromScore(double score) {
        int index = (int) Math.ceil(score);
        if (index < 1) index = 1;
        if (index > 14) index = 14;

        int[] ages = {
                6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 22
        };
        return ages[index - 1];
    }
}
