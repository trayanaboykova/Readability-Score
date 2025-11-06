import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the three side lengths as integers
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();

        // --- 1. Calculate the Semi-Perimeter (p) ---
        // p = (a + b + c) / 2
        // We use 'double' for p to ensure floating-point division
        double p = (a + b + c) / 2.0;

        // --- 2. Calculate the Area (S) using Heron's Formula ---
        // S = sqrt(p * (p - a) * (p - b) * (p - c))

        // Calculate the value inside the square root
        double areaSquared = p * (p - a) * (p - b) * (p - c);

        // Calculate the area using Math.sqrt()
        // Note: For a valid triangle, areaSquared will be non-negative.
        double area = Math.sqrt(areaSquared);

        // --- 3. Output the result ---
        System.out.println(area);
    }
}