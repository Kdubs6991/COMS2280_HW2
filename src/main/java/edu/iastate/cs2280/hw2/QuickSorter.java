/**
 * @author
 */
package edu.iastate.cs2280.hw2;

/**
 * This class implements the quicksort algorithm for an array of Student objects.
 * It uses a median-of-three pivot selection strategy to improve performance and
 * avoid worst-case scenarios with already sorted or reverse-sorted data.
 */
public class QuickSorter extends AbstractSorter {

  /**
   * Selects a pivot using the median-of-three strategy. It considers the first,
   * middle, and last elements of the subarray, sorts them, and uses the median
   * as the pivot. The pivot is swapped to the second-to-last position (last - 1)
   * to simplify the partition step.
   *
   * @param first The starting index of the subarray.
   * @param last  The ending index of the subarray.
   */
  private void medianOfThree(int first, int last) {
  }
}
