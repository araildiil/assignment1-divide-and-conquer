package kz.astanait.daa;

import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
class ClosestPairSolverTest {

    private final Random random = new Random();
    private static final double EPSILON = 1e-9;

    @Test
    void matchesBruteForceOnRandomPoints() {
        for (int trial = 0; trial < 20; trial++) {
            int size = 2 + random.nextInt(500);
            Point[] points = generateRandomPoints(size);

            double expected = bruteForce(points);
            double actual = new ClosestPairSolver().findClosestPair(points);

            assertEquals(expected, actual, EPSILON,
                    "Failed on trial " + trial + " with size=" + size);
        }
    }

    @Test
    void handlesTwoPoints() {
        Point[] points = {new Point(0, 0), new Point(3, 4)};
        double result = new ClosestPairSolver().findClosestPair(points);
        assertEquals(5.0, result, EPSILON);
    }

    @Test
    void handlesDuplicatePoints() {
        Point[] points = {
                new Point(1, 1), new Point(1, 1), new Point(5, 5)
        };
        double result = new ClosestPairSolver().findClosestPair(points);
        assertEquals(0.0, result, EPSILON);
    }

    @Test
    void matchesBruteForceAtLargerSize() {
        Point[] points = generateRandomPoints(2000);
        double expected = bruteForce(points);
        double actual = new ClosestPairSolver().findClosestPair(points);
        assertEquals(expected, actual, EPSILON);
    }

    private Point[] generateRandomPoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }

    private double bruteForce(Point[] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double d = points[i].distanceTo(points[j]);
                if (d < min) {
                    min = d;
                }
            }
        }
        return min;
    }
}
