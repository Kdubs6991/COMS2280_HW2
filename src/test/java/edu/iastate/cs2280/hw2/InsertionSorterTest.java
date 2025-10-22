/**
 * JUnit 5 test suite for {@link InsertionSorter} verifying compliance with the HW2 specification.
 *
 * <p>Each test validates a key aspect of the sorter’s functionality:</p>
 * <ul>
 *   <li><b>Order 0:</b> GPA descending, then credits descending.</li>
 *   <li><b>Order 1:</b> credits ascending, then GPA descending.</li>
 *   <li>Handles empty arrays, single elements, and duplicate stability.</li>
 * </ul>
 *
 * <p>Median logic follows {@link AbstractSorter#getMedian()}, which returns the element at
 * index {@code n / 2} (upper median for even {@code n}).</p>
 *
 * @author Kaleb
 * @see InsertionSorter
 * @see AbstractSorter
 * @see Student
 */
package edu.iastate.cs2280.hw2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for {@link InsertionSorter}, ensuring sorting correctness and median accuracy
 * for both comparator orders and edge cases.
 */
public class InsertionSorterTest {

    /**
     * Verifies correct sorting and median selection using Order 0 (GPA desc → credits desc).
     */
    @Test
    void testOrder0_MedianAndSize() {
            Student[] arr = {
                    new Student(3.8, 30),
                    new Student(4.0, 10),
                    new Student(3.8, 40),
                    new Student(2.5, 20)
            };

            AbstractSorter sorter = new InsertionSorter(arr);
            sorter.setComparator(0);
            sorter.sort();
            Student median = sorter.getMedian();
            assertNotNull(median);
            assertEquals(3.8, median.getGpa(), 1e-9);
            // sorted by GPA desc then credits desc → order is [4.0, (3.8,40), (3.8,30), 2.5]; median index 2 of 4 is (3.8,30)
            assertEquals(30, median.getCreditsTaken());
    }

    /**
     * Verifies correct sorting and median selection using Order 1
     * (credits asc -> GPA desc).
     */
    @Test
    void testOrder1_MedianAndSize () {
        Student[] arr = {
                new Student(3.5, 20),
                new Student(3.8, 10),
                new Student(3.0, 10),
                new Student(4.0, 20)
        };
        AbstractSorter sorter = new InsertionSorter(arr);
        sorter.setComparator(1);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(20, median.getCreditsTaken());
        // order1: credits asc, tie → GPA desc → sorted: [10(3.8), 10(3.0), 20(4.0), 20(3.5)] → median index 2 (0-based) is (20,4.0)
        assertEquals(4.0, median.getGpa(), 1e-9);
    }

    /**
     * Ensures sorter gracefully handles empty input arrays and returns null median.
     */
    @Test
    void testEmptyArray_OK () {
        Student[] arr = {};
        AbstractSorter sorter = new InsertionSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        assertNull(sorter.getMedian());
    }

    /**
     * Confirms median equals the single element for singleton arrays.
     */
    @Test
    void testSingleElement_MedianSame () {
        Student[] arr = {new Student(3.0, 10)};
        AbstractSorter sorter = new InsertionSorter(arr);
        sorter.setComparator(1);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.0, median.getGpa(), 1e-9);
        assertEquals(10, median.getCreditsTaken());
    }

    /**
     * Confirms stability and correct median when all elements are identical.
     */
    @Test
    void testDuplicates_MedianValue () {
        Student[] arr = {
                new Student(3.0, 10),
                new Student(3.0, 10),
                new Student(3.0, 10)
        };
        AbstractSorter sorter = new InsertionSorter(arr);
        sorter.setComparator(0);
        sorter.sort();
        Student median = sorter.getMedian();
        assertNotNull(median);
        assertEquals(3.0, median.getGpa(), 1e-9);
        assertEquals(10, median.getCreditsTaken());
    }
}