package kz.astanait.daa;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
class DeterministicSelectorTest {

    private final Random random = new Random();

    @Test
    void findsKthSmallestIn100RandomTrials() {
        for (int trial = 0; trial < 100; trial++) {
            int size = 1 + random.nextInt(500);
            int[] array = generateRandom(size);
            int k = random.nextInt(size);

            int[] sorted = array.clone();
            Arrays.sort(sorted);
            int expected = sorted[k];

            int actual = new DeterministicSelector().select(array.clone(), k);

            assertEquals(expected, actual,
                    "Failed on trial " + trial + " with size=" + size + ", k=" + k);
        }
    }

    @Test
    void findsMedianOnDuplicateHeavyArray() {
        int[] array = new int[500];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(5);
        }
        int k = array.length / 2;

        int[] sorted = array.clone();
        Arrays.sort(sorted);
        int expected = sorted[k];

        int actual = new DeterministicSelector().select(array.clone(), k);

        assertEquals(expected, actual);
    }

    @Test
    void findsSingleElementArray() {
        int[] array = {42};
        int actual = new DeterministicSelector().select(array, 0);
        assertEquals(42, actual);
    }

    @Test
    void findsMinAndMax() {
        int[] array = generateRandom(200);
        int[] sorted = array.clone();
        Arrays.sort(sorted);

        int min = new DeterministicSelector().select(array.clone(), 0);
        int max = new DeterministicSelector().select(array.clone(), array.length - 1);

        assertEquals(sorted[0], min);
        assertEquals(sorted[sorted.length - 1], max);
    }

    private int[] generateRandom(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1_000_000);
        }
        return array;
    }
}