package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String text;

        // Read whole file passed as the first command-line argument
        try {
            text = new String(Files.readAllBytes(Paths.get(args[0])));
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            // Simple fallback in case of missing/invalid file
            System.out.println("Error: cannot read file.");
            return;
        }

        System.out.println("The text is:");
        System.out.println(text);
        System.out.println();

        // Count characters (any non-whitespace symbol)
        int characters = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (!Character.isWhitespace(ch)) {
                characters++;
            }
        }

        // Count words (split by whitespace)
        String trimmed = text.trim();
        int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;

        // Count sentences (split by ., ! or ?, last one may have no punctuation)
        String[] sentenceParts = text.split("[.!?]");
        int sentences = 0;
        for (String part : sentenceParts) {
            if (!part.trim().isEmpty()) {
                sentences++;
            }
        }

        // ARI score
        double score = 4.71 * (characters * 1.0 / words)
                + 0.5  * (words * 1.0 / sentences)
                - 21.43;

        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.printf("The score is: %.2f%n", score);

        // Round score to integer index (ceil is typical for ARI)
        int index = (int) Math.ceil(score);
        if (index < 1)  index = 1;
        if (index > 14) index = 14;

        String age;
        switch (index) {
            case 1:  age = "5-6";   break;
            case 2:  age = "6-7";   break;
            case 3:  age = "7-8";   break;
            case 4:  age = "8-9";   break;
            case 5:  age = "9-10";  break;
            case 6:  age = "10-11"; break;
            case 7:  age = "11-12"; break;
            case 8:  age = "12-13"; break;
            case 9:  age = "13-14"; break;
            case 10: age = "14-15"; break;
            case 11: age = "15-16"; break;
            case 12: age = "16-17"; break;
            case 13: age = "17-18"; break;
            case 14: age = "18-22"; break;
            default: age = "unknown";
        }

        System.out.printf("This text should be understood by %s year-olds.%n", age);
    }
}
