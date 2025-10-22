/**
 * JUnit 5 test suite for {@link QuickSorter} verifying compliance with the HW2 specification.
 *
 * <p>This suite validates Quick Sort correctness with median-of-three pivot selection
 * and the assignment's comparator orders:</p>
 * <ul>
 *   <li><b>Order 0:</b> GPA descending, then credits descending.</li>
 *   <li><b>Order 1:</b> credits ascending, then GPA descending.</li>
 * </ul>
 *
 * <p>It also checks empty arrays, single-element arrays, and duplicates, and confirms
 * median selection follows {@link AbstractSorter#getMedian()} (upper median for even n).</p>
 *
 * @author Kaleb
 * @see QuickSorter
 * @see AbstractSorter
 * @see Student
 */
package edu.iastate.cs2280.hw2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link QuickSorter}, covering both comparator orders and key edge cases.
 */
public class QuickSorterTest {

    /**
     * Verifies QuickSorter correctly sorts and selects the median under Order 0
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
        AbstractSorter sorter = new QuickSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.8, median.getGpa(), 1e-9);
        assertEquals(30, median.getCreditsTaken());
    }

    /**
     * Verifies QuickSorter correctly sorts and selects the median under Order 1
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
        AbstractSorter sorter = new QuickSorter(arr);
        sorter.setComparator(1);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(20, median.getCreditsTaken());
        assertEquals(4.0, median.getGpa(), 1e-9);
    }

    /**
     * Ensures QuickSorter handles an empty input array and returns {@code null} median.
     */
    @Test
    void testEmptyArray_OK() {
        Student[] arr = {};
        AbstractSorter sorter = new QuickSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        assertNull(sorter.getMedian());
    }

    /**
     * Confirms the single element is returned as the median for a singleton array.
     */
    @Test
    void testSingleElement_MedianSame() {
        Student[] arr = { new Student(3.0, 10) };
        AbstractSorter sorter = new QuickSorter(arr);
        sorter.setComparator(1);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.0, median.getGpa(), 1e-9);
        assertEquals(10, median.getCreditsTaken());
    }

    /**
     * Confirms stable handling of duplicate values and correct median selection.
     */
    @Test
    void testDuplicates_MedianValue() {
        Student[] arr = {
                new Student(3.0, 10),
                new Student(3.0, 10),
                new Student(3.0, 10)
        };
        AbstractSorter sorter = new QuickSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.0, median.getGpa(), 1e-9);
        assertEquals(10, median.getCreditsTaken());
    }
}
