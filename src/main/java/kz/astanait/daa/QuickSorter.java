package kz.astanait.daa;

import java.util.Random;
public class QuickSorter {

    private final Random random = new Random();

    private int maxRecursionDepth = 0;
    private long comparisons = 0;
    private long swaps = 0;

    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        maxRecursionDepth = 0;
        comparisons = 0;
        swaps = 0;
        sort(array, 0, array.length - 1, 1);
    }

    private void sort(int[] array, int low, int high, int depth) {
        while (low < high) {
            if (depth > maxRecursionDepth) {
                maxRecursionDepth = depth;
            }

            int pivotIndex = partition(array, low, high);

            int leftSize = pivotIndex - low;
            int rightSize = high - pivotIndex;

            if (leftSize < rightSize) {
                sort(array, low, pivotIndex - 1, depth + 1);
                low = pivotIndex + 1;
            } else {
                sort(array, pivotIndex + 1, high, depth + 1);
                high = pivotIndex - 1;
            }
            depth++;
        }
    }

    private int partition(int[] array, int low, int high) {
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(array, randomIndex, high);

        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            comparisons++;
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private void swap(int[] array, int i, int j) {
        if (i == j) {
            return;
        }
        swaps++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }
}