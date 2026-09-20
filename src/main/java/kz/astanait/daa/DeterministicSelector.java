package kz.astanait.daa;
public class DeterministicSelector {

    private static final int GROUP_SIZE = 5;

    private int maxRecursionDepth = 0;
    private long comparisons = 0;
    private long swaps = 0;

    public int select(int[] array, int k) {
        if (array == null || k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid array or k");
        }
        maxRecursionDepth = 0;
        comparisons = 0;
        swaps = 0;
        return select(array, 0, array.length - 1, k, 1);
    }

    private int select(int[] array, int low, int high, int k, int depth) {
        if (depth > maxRecursionDepth) {
            maxRecursionDepth = depth;
        }

        if (low == high) {
            return array[low];
        }

        int pivotValue = medianOfMediansValue(array, low, high, depth);
        int lt = low;
        int gt = high;
        int i = low;
        while (i <= gt) {
            comparisons++;
            if (array[i] < pivotValue) {
                swap(array, i, lt);
                lt++;
                i++;
            } else if (array[i] > pivotValue) {
                swap(array, i, gt);
                gt--;
            } else {
                i++;
            }
        }

        if (k < lt) {
            return select(array, low, lt - 1, k, depth + 1);
        } else if (k > gt) {
            return select(array, gt + 1, high, k, depth + 1);
        } else {
            return pivotValue;
        }
    }

    private int medianOfMediansValue(int[] array, int low, int high, int depth) {
        int n = high - low + 1;

        if (n <= GROUP_SIZE) {
            insertionSort(array, low, high);
            return array[low + (n - 1) / 2];
        }

        int numGroups = (n + GROUP_SIZE - 1) / GROUP_SIZE;
        for (int g = 0; g < numGroups; g++) {
            int groupLow = low + g * GROUP_SIZE;
            int groupHigh = Math.min(groupLow + GROUP_SIZE - 1, high);
            insertionSort(array, groupLow, groupHigh);

            int medianIndex = groupLow + (groupHigh - groupLow) / 2;
            swap(array, low + g, medianIndex);
        }

        return select(array, low, low + numGroups - 1, low + (numGroups - 1) / 2, depth + 1);
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
            array[j + 1] = key;
        }
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