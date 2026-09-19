package kz.astanait.daa;

public class  MergeSorter {

    private static final int CUTOFF = 16;
    private int maxRecursionDepth = 0;
    private long comparisons = 0;

    private int[] buffer;

    public void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        buffer = new int[array.length];
        maxRecursionDepth = 0;
        comparisons = 0;
        sort(array, 0, array.length - 1, 1);
    }

    private void sort(int[] array, int low, int high, int depth) {
        if (depth > maxRecursionDepth) {
            maxRecursionDepth = depth;
        }

        if (high - low + 1 <= CUTOFF) {
            insertionSort(array, low, high);
            return;
        }

        int mid = low + (high - low) / 2;
        sort(array, low, mid, depth + 1);
        sort(array, mid + 1, high, depth + 1);
        merge(array, low, mid, high);
    }

    private void merge(int[] array, int low, int mid, int high) {
        for (int i = low; i <= high; i++) {
            buffer[i] = array[i];
        }

        int left = low;
        int right = mid + 1;
        int current = low;

        while (left <= mid && right <= high) {
            comparisons++;
            if (buffer[left] <= buffer[right]) {
                array[current++] = buffer[left++];
            } else {
                array[current++] = buffer[right++];
            }
        }

        while (left <= mid) {
            array[current++] = buffer[left++];
        }

        while (right <= high) {
            array[current++] = buffer[right++];
        }
    }

    private void insertionSort(int[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= low && array[j] > key) {
                comparisons++;
                array[j + 1] = array[j];
                j--;
            }
            if (j >= low) {
                comparisons++;
            }
            array[j + 1] = key;
        }
    }

    public int getMaxRecursionDepth() {
        return maxRecursionDepth;
    }

    public long getComparisons() {
        return comparisons;
    }
}
