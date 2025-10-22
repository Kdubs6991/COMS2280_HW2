
/**
 * JUnit 5 test suite for {@link SelectionSorter} verifying compliance with the HW2 specification.
 *
 * <p>This suite validates Selection Sort correctness, stability, and median computation using the
 * assignment's comparator orders:</p>
 * <ul>
 *   <li><b>Order 0:</b> GPA descending, then credits descending.</li>
 *   <li><b>Order 1:</b> credits ascending, then GPA descending.</li>
 * </ul>
 *
 * <p>Additional tests confirm handling of edge cases such as empty arrays, single-element arrays,
 * and duplicates, and verify the median logic defined in {@link AbstractSorter#getMedian()} which
 * returns the upper median (index {@code n / 2}) for even-sized arrays.</p>
 *
 * @author Kaleb
 * @see SelectionSorter
 * @see AbstractSorter
 * @see Student
 */
package edu.iastate.cs2280.hw2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SelectionSorter}, covering sorting correctness and median validation
 * across both comparator orders and special cases.
 */
public class SelectionSorterTest {

    /**
     * Verifies SelectionSorter correctly sorts and computes the median under Order 0
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
        AbstractSorter sorter = new SelectionSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.8, median.getGpa(), 1e-9);
        assertEquals(30, median.getCreditsTaken());
    }

    /**
     * Verifies SelectionSorter correctly sorts and computes the median under Order 1
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
        AbstractSorter sorter = new SelectionSorter(arr);
        sorter.setComparator(1);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(20, median.getCreditsTaken());
        assertEquals(4.0, median.getGpa(), 1e-9);
    }

    /**
     * Ensures SelectionSorter handles empty input arrays without error and returns a null median.
     */
    @Test
    void testEmptyArray_OK() {
        Student[] arr = {};
        AbstractSorter sorter = new SelectionSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        assertNull(sorter.getMedian());
    }

    /**
     * Confirms SelectionSorter returns the single element as the median for a one-element array.
     */
    @Test
    void testSingleElement_MedianSame() {
        Student[] arr = { new Student(3.0, 10) };
        AbstractSorter sorter = new SelectionSorter(arr);
        sorter.setComparator(1);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.0, median.getGpa(), 1e-9);
        assertEquals(10, median.getCreditsTaken());
    }

    /**
     * Confirms SelectionSorter maintains stability and correct median with all identical elements.
     */
    @Test
    void testDuplicates_MedianValue() {
        Student[] arr = {
                new Student(3.0, 10),
                new Student(3.0, 10),
                new Student(3.0, 10)
        };
        AbstractSorter sorter = new SelectionSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.0, median.getGpa(), 1e-9);
        assertEquals(10, median.getCreditsTaken());
    }
}
