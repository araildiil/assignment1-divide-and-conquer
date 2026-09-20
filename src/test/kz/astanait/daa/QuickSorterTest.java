package kz.astanait.daa;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
class QuickSorterTest {

    private final Random random = new Random();

    @Test
    void sortsRandomArray() {
        int[] array = generateRandom(1000);
        assertSortedCorrectly(array);
    }

    @Test
    void sortsAlreadySortedArray() {
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertSortedCorrectly(array);
    }

    @Test
    void sortsReverseSortedArray() {
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - i;
        }
        assertSortedCorrectly(array);
    }

    @Test
    void sortsDuplicateHeavyArray() {
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(5);
        }
        assertSortedCorrectly(array);
    }

    @Test
    void sortsEmptyArray() {
        int[] array = new int[0];
        assertSortedCorrectly(array);
    }

    @Test
    void sortsSingleElementArray() {
        int[] array = {42};
        assertSortedCorrectly(array);
    }

    private int[] generateRandom(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1_000_000);
        }
        return array;
    }

    private void assertSortedCorrectly(int[] array) {
        int[] expected = array.clone();
        Arrays.sort(expected);

        int[] actual = array.clone();
        new QuickSorter().sort(actual);

        assertArrayEquals(expected, actual);
    }
}