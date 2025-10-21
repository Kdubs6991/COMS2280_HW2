package edu.iastate.cs2280.hw2;

/**
 * The {@code InsertionSorter} class extends {@link AbstractSorter} and
 * implements the <b>Insertion Sort</b> algorithm for sorting an array of
 * {@link Student} objects.
 *
 * <p><b>How it works:</b> Insertion Sort builds a sorted prefix of the
 * array one element at a time. For each index {@code i}, the element
 * {@code students[i]} is removed and inserted into its correct position
 * among {@code students[0..i-1]} using the comparator configured via
 * {@link AbstractSorter#setComparator(int)}.</p>
 *
 * <p><b>Comparator behavior:</b> All ordering decisions are delegated to
 * the comparator stored in {@link #studentComparator}. That comparator
 * determines whether the sort is by GPA (order 0) or by credits (order 1),
 * using the tie-breaking rules defined by the assignment.</p>
 *
 * <p><b>Stability:</b> This implementation is <i>stable</i> with respect to
 * {@link #studentComparator}; equal elements retain their original relative
 * order.</p>
 *
 * <p><b>Complexity:</b> Worst/average time {@code O(n^2)}; best case
 * (already sorted) {@code O(n)}. Extra space {@code O(1)}.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * Student[] a = {
 *     new Student(3.8, 90),
 *     new Student(2.7, 75),
 *     new Student(4.0, 120)
 * };
 *
 * InsertionSorter sorter = new InsertionSorter(a);
 * sorter.setComparator(0);   // 0 = GPA desc, then credits desc
 * sorter.sort();             // sorts the internal copy held by the sorter
 * }</pre>
 *
 * @author Kaleb
 * @see AbstractSorter
 * @see Algorithm#InsertionSort
 */
public class InsertionSorter extends AbstractSorter {

    /**
     * Constructs an {@code InsertionSorter} that operates on a deep copy
     * of the provided {@link Student} array.
     *
     * <p>The algorithm name is set to {@code "InsertionSort"} to appear in
     * the results table printed by the driver.</p>
     *
     * @param students the array of students to be sorted
     */
    public InsertionSorter(Student[] students) {
        super(students);
        this.algorithm = Algorithm.InsertionSort.name();
    }


    /**
     * Sorts the internal {@link Student} array using the Insertion Sort algorithm.
     *
     * <p>All comparisons use {@link #studentComparator}, which must be set by
     * {@link #setComparator(int)} before calling this method so that both
     * assignment orders (by GPA or by credits) are supported.</p>
     *
     * <p><b>Note:</b> This method rearranges only the <em>internal copy</em> of the
     * students array stored in the sorter; it does not modify the original array
     * reference passed to the constructor.</p>
     */
    @Override
    public void sort() {
        int n = students.length;

        // Grow a sorted prefix [0..i-1], then insert students[i] into that prefix.
        for (int i = 1; i < n; i++) {
            Student key = students[i];
            int j = i - 1;

            // Shift strictly greater elements one position to the right.
            // Using "> 0" (not ">= 0") keeps equals in place → stable sort.
            while (j >= 0 && studentComparator.compare(students[j], key) > 0) {
                students[j + 1] = students[j];
                j--;
            }

            // Insert the saved element at its correct position.
            students[j + 1] = key;
        }
    }
}
