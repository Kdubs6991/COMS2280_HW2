/**
 * JUnit 5 test suite for {@link MergeSorter} verifying compliance with the HW2 specification.
 *
 * <p>Each test validates key aspects of merge sort correctness and median computation:</p>
 * <ul>
 *   <li><b>Order 0:</b> GPA descending, then credits descending.</li>
 *   <li><b>Order 1:</b> credits ascending, then GPA descending.</li>
 *   <li>Handling of empty arrays, single elements, and duplicate values.</li>
 * </ul>
 *
 * <p>The tests also confirm correct median selection following
 * {@link AbstractSorter#getMedian()}, which returns the upper median (index {@code n / 2})
 * for even-sized arrays.</p>
 *
 * @author Kaleb
 * @see MergeSorter
 * @see AbstractSorter
 * @see Student
 */
package edu.iastate.cs2280.hw2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MergeSorter}, ensuring sorting correctness, stability, and
 * median accuracy across both comparator orders and special cases.
 */
public class MergeSorterTest {

    /**
     * Verifies MergeSorter correctly sorts and identifies the median for Order 0
     * (GPA descending; tie → credits descending).
     */
    @Test
    void testOrder0_MedianAndSize() {
        Student[] arr = {
                new Student(3.8, 30),
                new Student(4.0, 10),
                new Student(3.8, 40),
                new Student(2.5, 20)
        };
        AbstractSorter sorter = new MergeSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.8, median.getGpa(), 1e-9);
        assertEquals(30, median.getCreditsTaken());
    }

    /**
     * Verifies MergeSorter correctly sorts and identifies the median for Order 1
     * (credits ascending; tie → GPA descending).
     */
    @Test
    void testOrder1_MedianAndSize() {
        Student[] arr = {
                new Student(3.5, 20),
                new Student(3.8, 10),
                new Student(3.0, 10),
                new Student(4.0, 20)
        };
        AbstractSorter sorter = new MergeSorter(arr);
        sorter.setComparator(1);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(20, median.getCreditsTaken());
        assertEquals(4.0, median.getGpa(), 1e-9);
    }

    /**
     * Ensures MergeSorter handles an empty array gracefully and returns {@code null} median.
     */
    @Test
    void testEmptyArray_OK() {
        Student[] arr = {};
        AbstractSorter sorter = new MergeSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        assertNull(sorter.getMedian());
    }

    /**
     * Confirms MergeSorter returns the single element as the median for singleton arrays.
     */
    @Test
    void testSingleElement_MedianSame() {
        Student[] arr = { new Student(3.0, 10) };
        AbstractSorter sorter = new MergeSorter(arr);
        sorter.setComparator(1);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.0, median.getGpa(), 1e-9);
        assertEquals(10, median.getCreditsTaken());
    }

    /**
     * Confirms MergeSorter maintains stability and correct median when all elements are identical.
     */
    @Test
    void testDuplicates_MedianValue() {
        Student[] arr = {
                new Student(3.0, 10),
                new Student(3.0, 10),
                new Student(3.0, 10)
        };
        AbstractSorter sorter = new MergeSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.0, median.getGpa(), 1e-9);
        assertEquals(10, median.getCreditsTaken());
    }
}
