package edu.iastate.cs2280.hw2;
/**
 * QuickSorter implements the quicksort algorithm to sort an array of Student objects.
 * <p>
 * This class uses the median-of-three pivot selection strategy to choose the pivot element
 * for partitioning the array. The median-of-three method selects the median value among the
 * first, middle, and last elements of the subarray, which helps to avoid worst-case performance
 * scenarios such as already sorted or reverse-sorted inputs.
 * <p>
 * The quicksort algorithm recursively partitions the array around the pivot, sorting elements
 * less than the pivot to its left and elements greater than the pivot to its right. This process
 * continues recursively on the subarrays until the entire array is sorted.
 * <p>
 * The average time complexity of quicksort is O(n log n), where n is the number of elements.
 * The worst-case time complexity is O(n^2), but the median-of-three pivot selection reduces
 * the likelihood of this occurring.
 * <p>
 * This implementation sorts the array in place and does not require additional memory beyond
 * the recursion stack.
 * <p>
 * All element comparisons delegate to the comparator configured via {@link AbstractSorter#setComparator(int)} (order 0 or 1) so this sorter respects the assignment's sorting criteria.
 * </p>
 * @see AbstractSorter
 * @see Algorithm#QuickSort
 * @author Kaleb
 */
public class QuickSorter extends AbstractSorter {

  /**
   * Constructs a QuickSorter with the given array of Student objects.
   * Initializes the sorter and sets the algorithm name to "QuickSort".
   *
   * @param students the array of Student objects to be sorted
   */
  public QuickSorter (Student[] students) {
    super(students);
    this.algorithm = Algorithm.QuickSort.name();
  }

  /**
   * Sorts the array of students using the quicksort algorithm.
   * If the array has zero or one element, no sorting is performed.
   */
  @Override
  public void sort() {
    int n = students.length;
    if (n <= 1) {
      return;
    }
    quickSortRec(0, n - 1);
  }

  /**
   * Recursively sorts the subarray of students between indices first and last (inclusive)
   * using the quicksort algorithm with median-of-three pivot selection.
   *
   * @param first the starting index of the subarray to sort
   * @param last the ending index of the subarray to sort
   */
  private void quickSortRec(int first, int last) {
    if (first >= last) {
      return;
    }
    if (last - first == 1) {
      if (studentComparator.compare(students[last], students[first]) < 0) {
        swap(first, last);
      }
      return;
    }

    medianOfThree(first, last);
    int pivotIndex = partition(first, last);
    quickSortRec(first, pivotIndex - 1);
    quickSortRec(pivotIndex + 1, last);
  }

  /**
   * Selects a pivot using the median-of-three strategy by comparing the first, middle,
   * and last elements of the subarray. It reorders these three elements so that their
   * median is placed at index last - 1. This pivot placement simplifies the partitioning step.
   *
   * @param first the starting index of the subarray
   * @param last the ending index of the subarray
   */
  private void medianOfThree(int first, int last) {
    int mid = first + (last - first) / 2;
    if (studentComparator.compare(students[mid], students[first]) < 0) {
      swap(first, mid);
    }
    if (studentComparator.compare(students[last], students[first]) < 0) {
      swap(first, last);
    }
    if (studentComparator.compare(students[last], students[mid]) < 0) {
      swap(mid, last);
    }
    swap(mid, last - 1);
  }

  /**
   * Partitions the subarray around the pivot element located at index last - 1.
   * Elements less than the pivot are moved to the left, and elements greater or equal
   * to the right. After partitioning, the pivot is placed in its correct sorted position.
   *
   * @param first the starting index of the subarray to partition
   * @param last the ending index of the subarray to partition
   * @return the final index of the pivot element after partitioning
   */
  private int partition(int first, int last) {
    int left = first + 1;
    int right = last - 2;
    Student pivot = students[last - 1];

    while (true) {
      while (left <= right && studentComparator.compare(students[left], pivot) < 0) {
        left++;
      }
      while (left <= right && studentComparator.compare(students[right], pivot) >= 0) {
        right--;
      }
      if (left >= right) {
        break;
      }
      swap(left, right);
      left++;
      right--;
    }
    swap(left, last - 1);
    return left;
  }
}
