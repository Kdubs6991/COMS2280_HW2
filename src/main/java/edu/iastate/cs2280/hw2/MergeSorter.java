package edu.iastate.cs2280.hw2;

/**
 * The {@code MergeSorter} class extends {@link AbstractSorter} and implements
 * the <b>Merge Sort</b> algorithm for sorting arrays of {@link Student} objects.
 *
 * <p><b>How it works:</b> Merge Sort is a divide-and-conquer algorithm that recursively
 * divides the array into halves, sorts each half, and then merges the two sorted halves
 * back together using the comparator configured via {@link AbstractSorter#setComparator(int)}.
 * </p>
 *
 * <p><b>Comparator behavior:</b> The order of sorting (by GPA or by credits) is determined
 * by the {@link #studentComparator} field in the superclass. The comparator must be set
 * before calling {@link #sort()} so the algorithm can use the correct ordering rule
 * required by the assignment.</p>
 *
 * <p><b>Stability:</b> This implementation is stable. If two students compare equal according
 * to the comparator, their original relative order is preserved during merging.</p>
 *
 * <p><b>Complexity:</b> Average and worst-case runtime {@code O(n log n)}; auxiliary space
 * {@code O(n)}.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * Student[] students = {
 *     new Student(3.5, 100),
 *     new Student(2.9, 80),
 *     new Student(4.0, 120)
 * };
 *
 * MergeSorter sorter = new MergeSorter(students);
 * sorter.setComparator(0); // Sort by GPA descending
 * sorter.sort();
 * }</pre>
 *
 * @author Kaleb
 * @see AbstractSorter
 * @see Algorithm#MergeSort
 */

public class MergeSorter extends AbstractSorter {

    /** Auxiliary array used for temporary storage during the merge process. */
    private Student[] aux;

    /**
     * Constructs a {@code MergeSorter} that operates on a deep copy of the provided
     * {@link Student} array.
     *
     * <p>The algorithm name is set to {@code "MergeSort"} for reporting in the comparison table.</p>
     *
     * @param students the array of students to be sorted
     * @throws IllegalArgumentException if {@code students} is {@code null}
     */
    public MergeSorter (Student[] students) {
        super(students);
        this.algorithm = Algorithm.MergeSort.name();
    }

    /**
     * Sorts the internal array of {@link Student} objects using the <b>Merge Sort</b> algorithm.
     *
     * <p>All element comparisons use the comparator configured through
     * {@link AbstractSorter#setComparator(int)}. The comparator determines whether sorting
     * is by GPA (order 0) or by credits (order 1), following the assignment specification.</p>
     */
    @Override
    public void sort() {
        int n = students.length;
        if (n <= 1) {
            return;
        }

        aux = new Student[n];
        mergeSortRec(0, n - 1);
    }

    /**
     * Recursively sorts the subarray {@code students[left..right]} using Merge Sort.
     *
     * @param left  the leftmost index of the subarray to sort
     * @param right the rightmost index of the subarray to sort
     */
    private void mergeSortRec(int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) /2;
        mergeSortRec(left, mid);
        mergeSortRec(mid + 1, right);
        merge(left, mid, right);
    }

    /**
     * Merges two consecutive sorted subarrays: {@code students[left..mid]} and
     * {@code students[mid+1..right]} into a single sorted range.
     *
     * <p>Elements are merged back into the original array in sorted order according
     * to {@link #studentComparator}. If two elements are equal, the one from the left
     * half is chosen first to preserve stability.</p>
     *
     * @param left  the starting index of the left subarray
     * @param mid   the ending index of the left subarray (midpoint)
     * @param right the ending index of the right subarray
     */
    private void merge(int left, int mid, int right) {
        for (int k = left; k <= right; k++) {
            aux[k] = students[k];
        }

        int i = left;
        int j = mid + 1;
        int dest = left;

        while (i <= mid && j <= right) {
            if (studentComparator.compare(aux[i], aux[j]) <= 0) {
                students[dest++] = aux[i++];
            } else {
                students[dest++] = aux[j++];
            }
        }

        while (i <= mid) {
            students[dest++] = aux[i++];
        }
    }
}
