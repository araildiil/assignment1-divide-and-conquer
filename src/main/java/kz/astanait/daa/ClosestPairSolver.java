package kz.astanait.daa;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    private int maxRecursionDepth = 0;
    private long comparisons = 0;

    public double findClosestPair(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }
        maxRecursionDepth = 0;
        comparisons = 0;

        Point[] byX = points.clone();
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));

        Point[] byY = byX.clone();
        Arrays.sort(byY, Comparator.comparingDouble(p -> p.y));

        return closestPair(byX, byY, 0, byX.length - 1, 1);
    }

    private double closestPair(Point[] byX, Point[] byY, int low, int high, int depth) {
        if (depth > maxRecursionDepth) {
            maxRecursionDepth = depth;
        }

        int n = high - low + 1;
        if (n <= 3) {
            return bruteForce(byX, low, high);
        }

        int mid = low + (high - low) / 2;
        double midX = byX[mid].x;

        Point[] leftY = new Point[mid - low + 1];
        Point[] rightY = new Point[high - mid];
        int li = 0, ri = 0;

        java.util.Set<Point> leftSet = new java.util.HashSet<>();
        for (int i = low; i <= mid; i++) {
            leftSet.add(byX[i]);
        }
        for (Point p : byY) {
            if (leftSet.contains(p)) {
                if (li < leftY.length) leftY[li++] = p;
            } else {
                if (ri < rightY.length) rightY[ri++] = p;
            }
        }

        double deltaLeft = closestPair(byX, leftY, low, mid, depth + 1);
        double deltaRight = closestPair(byX, rightY, mid + 1, high, depth + 1);
        double delta = Math.min(deltaLeft, deltaRight);

        Point[] strip = new Point[n];
        int stripSize = 0;
        for (Point p : byY) {
            comparisons++;
            if (Math.abs(p.x - midX) < delta) {
                strip[stripSize++] = p;
            }
        }

        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j].y - strip[i].y) < delta; j++) {
                comparisons++;
                double d = strip[i].distanceTo(strip[j]);
                if (d < delta) {
                    delta = d;
                }
            }
        }

        return delta;
    }

    private double bruteForce(Point[] byX, int low, int high) {
        double min = Double.MAX_VALUE;
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                comparisons++;
                double d = byX[i].distanceTo(byX[j]);
                if (d < min) {
                    min = d;
                }
            }
        }
        return min;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public long getComparisons() {
        return comparisons;
    }
}