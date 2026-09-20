package kz.astanait.daa;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment {

    private static final Random RANDOM = new Random();

    public enum InputType {
        RANDOM, SORTED, REVERSE_SORTED, DUPLICATE_HEAVY
    }

    public void run(int[] sizes, String outputCsvPath) throws IOException {
        try (FileWriter writer = new FileWriter(outputCsvPath)) {
            writer.write("algorithm,inputType,size,timeNanos,maxRecursionDepth,comparisons\n");

            for (int size : sizes) {
                for (InputType type : InputType.values()) {
                    runMergeSort(size, type, writer);
                    runQuickSort(size, type, writer);
                    runDeterministicSelect(size, type, writer);
                }
                runClosestPair(size, writer);
            }
        }
    }

    private void runMergeSort(int size, InputType type, FileWriter writer) throws IOException {
        int[] array = generateArray(size, type);
        MergeSorter sorter = new MergeSorter();

        long start = System.nanoTime();
        sorter.sort(array);
        long elapsed = System.nanoTime() - start;

        writeRow(writer, "MergeSort", type.toString(), size, elapsed,
                sorter.getMaxRecursionDepth(), sorter.getComparisons());
    }

    private void runQuickSort(int size, InputType type, FileWriter writer) throws IOException {
        int[] array = generateArray(size, type);
        QuickSorter sorter = new QuickSorter();

        long start = System.nanoTime();
        sorter.sort(array);
        long elapsed = System.nanoTime() - start;

        writeRow(writer, "QuickSort", type.toString(), size, elapsed,
                sorter.getMaxRecursionDepth(), sorter.getComparisons());
    }

    private void runDeterministicSelect(int size, InputType type, FileWriter writer) throws IOException {
        int[] array = generateArray(size, type);
        DeterministicSelector selector = new DeterministicSelector();
        int k = size / 2;

        long start = System.nanoTime();
        selector.select(array, k);
        long elapsed = System.nanoTime() - start;

        writeRow(writer, "DeterministicSelect", type.toString(), size, elapsed,
                selector.getMaxRecursionDepth(), selector.getComparisons());
    }

    private void runClosestPair(int size, FileWriter writer) throws IOException {
        if (size < 2) {
            return;
        }
        Point[] points = generatePoints(size);
        ClosestPairSolver solver = new ClosestPairSolver();

        long start = System.nanoTime();
        solver.findClosestPair(points);
        long elapsed = System.nanoTime() - start;

        writeRow(writer, "ClosestPair", "RANDOM", size, elapsed,
                solver.getMaxRecursionDepth(), solver.getComparisons());
    }

    private void writeRow(FileWriter writer, String algorithm, String type, int size,
                          long timeNanos, int maxDepth, long comparisons) throws IOException {
        writer.write(String.format("%s,%s,%d,%d,%d,%d%n",
                algorithm, type, size, timeNanos, maxDepth, comparisons));
    }

    private int[] generateArray(int size, InputType type) {
        int[] array = new int[size];
        switch (type) {
            case RANDOM:
                for (int i = 0; i < size; i++) {
                    array[i] = RANDOM.nextInt(1_000_000);
                }
                break;
            case SORTED:
                for (int i = 0; i < size; i++) {
                    array[i] = i;
                }
                break;
            case REVERSE_SORTED:
                for (int i = 0; i < size; i++) {
                    array[i] = size - i;
                }
                break;
            case DUPLICATE_HEAVY:
                for (int i = 0; i < size; i++) {
                    array[i] = RANDOM.nextInt(10);
                }
                break;
        }
        return array;
    }

    private Point[] generatePoints(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(RANDOM.nextDouble() * 1_000_000, RANDOM.nextDouble() * 1_000_000);
        }
        return points;
    }
}