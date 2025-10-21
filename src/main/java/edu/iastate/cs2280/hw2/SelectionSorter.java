package edu.iastate.cs2280.hw2;

import java.util.Comparator;

/**
 * The {@code SelectionSorter} class extends {@link AbstractSorter}
 * and provides an implementation of the <b>Selection Sort</b> algorithm
 * for sorting an array of {@link Student} objects.
 * <p>
 * This sorter repeatedly selects the minimum element (according to
 * the current {@link Comparator} set in the parent class) from the
 * unsorted portion of the array and moves it to its correct position
 * in the sorted portion.
 * </p>
 *
 * <p>
 * The sorting behavior (by GPA and/or by credits) is determined by
 * the {@link #studentComparator} provided by the {@link AbstractSorter}
 * superclass. The comparator is configured using
 * {@link AbstractSorter#setComparator(int)} before calling {@link #sort()}.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * Student[] students = {
 *     new Student(3.8, 90),
 *     new Student(2.7, 75),
 *     new Student(4.0, 120)
 * };
 *
 * SelectionSorter sorter = new SelectionSorter(students);
 * sorter.setComparator(0); // Sort by GPA (descending)
 * sorter.sort();
 * }</pre>
 *
 * @author Kaleb
 * @see AbstractSorter
 * @see Student
 * @see Comparator
 * @see Algorithm#SelectionSort
 */
public class SelectionSorter extends AbstractSorter {

    /**
     * Constructs a {@code SelectionSorter} that operates on a deep copy
     * of the provided array of {@link Student} objects.
     * <p>
     * The algorithm name is automatically set to {@code "SelectionSort"}.
     * </p>
     *
     * @param students the array of students to be sorted
     * @throws IllegalArgumentException if {@code students} is {@code null}
     */
    public SelectionSorter(Student[] students) {
        super(students);
        this.algorithm = Algorithm.SelectionSort.name();
    }

    /**
     * Sorts the array of students using the <b>Selection Sort</b> algorithm.
     * <p>
     * This method repeatedly selects the minimum element (based on the
     * configured {@link #studentComparator}) from the unsorted part of
     * the array and places it at the beginning of the unsorted section.
     * </p>
     * <p>
     * Runtime Complexity: {@code O(n^2)}
     * </p>
     *
     * <p><b>Note:</b> This method modifies the internal copy of the
     * {@link Student} array stored in the sorter. It does not alter
     * the original array passed to the constructor.</p>
     */
    @Override
    public void sort() {
        int n = students.length;

        // Outer loop selects each position in sequence
        for (int i = 0; i < n-1; i++){
            int minIndex = i;

            // Find index of smallest element in the unsorted portion
            for (int j = i + 1; j < n; j++){
                if (studentComparator.compare(students[j], students[minIndex]) < 0){
                    minIndex = j;
                }
            }

            // swap if a smaller element is found
            if (minIndex != i) {
                swap(i, minIndex);
            }
        }

    }
}
