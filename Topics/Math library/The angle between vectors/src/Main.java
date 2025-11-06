import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read first vector
        double x1 = scanner.nextDouble();
        double y1 = scanner.nextDouble();

        // Read second vector
        double x2 = scanner.nextDouble();
        double y2 = scanner.nextDouble();

        // Compute dot product
        double dotProduct = x1 * x2 + y1 * y2;

        // Compute magnitudes
        double magnitude1 = Math.sqrt(x1 * x1 + y1 * y1);
        double magnitude2 = Math.sqrt(x2 * x2 + y2 * y2);

        // Compute cosine of angle
        double cosTheta = dotProduct / (magnitude1 * magnitude2);

        // Fix floating-point precision issues
        if (cosTheta > 1) cosTheta = 1;
        if (cosTheta < -1) cosTheta = -1;

        // Compute angle in radians and then convert to degrees
        double angle = Math.acos(cosTheta) * 180 / Math.PI;

        System.out.println(angle);
    }
}
