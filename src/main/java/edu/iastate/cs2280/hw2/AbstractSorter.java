
package edu.iastate.cs2280.hw2;

import java.util.Comparator;

/**
 * The {@code AbstractSorter} class provides shared state and utilities for all concrete sorting
 * implementations in HW2 (Selection, Insertion, Merge, Quick). It owns a deep-copied array of
 * {@link Student} objects, exposes a configurable {@link java.util.Comparator} that encodes the
 * assignment's two sorting orders, and supplies a {@code swap} helper and a {@code getMedian}
 * accessor.
 *
 * <p><b>Comparator orders (per spec):</b>
 * <ul>
 *   <li>Order 0 (by GPA): GPA <em>descending</em>; ties broken by credits <em>descending</em>.</li>
 *   <li>Order 1 (by Credits): credits <em>ascending</em>; ties broken by GPA <em>descending</em>.</li>
 * </ul>
 * Concrete sorter subclasses must call {@link #setComparator(int)} before invoking {@link #sort()} so
 * comparisons respect the current order.
 *
 * <p>The constructor makes a <em>deep copy</em> of the provided array so that sorting never mutates
 * the caller's data. Subclasses operate only on this internal copy.</p>
 *
 * @see Student
 * @see Algorithm
 * @author Kaleb
 */
public abstract class AbstractSorter {
  // Internal working copy of the dataset to be sorted (deep-copied in the constructor)
  protected Student[] students;

  // Algorithm display name; set by each concrete sorter (e.g., "SelectionSort")
  protected String algorithm = null;

  // Comparator reflecting the current order (set via setComparator(0/1) before sort())
  protected Comparator<Student> studentComparator = null;

  /**
   * Constructs an {@code AbstractSorter} over a deep copy of the given student array.
   * The input reference is never modified; instead each element is copied into an internal array.
   *
   * @param students input array; may be empty but must not be {@code null}
   * @throws IllegalArgumentException if {@code students} is {@code null}
   */
  protected AbstractSorter(Student[] students) throws IllegalArgumentException {
    if (students == null) {
      throw new IllegalArgumentException("students must be non-null");
    }
    // Deep copy each element so client data remains unmodified
    this.students = new Student[students.length];
    for (int i = 0; i < students.length; i++) {
      this.students[i] = new Student(students[i]);
    }
  }

  /**
   * Configures {@link #studentComparator} according to the assignment's two orders:
   * <ul>
   *   <li><b>order == 0</b>: GPA descending; tie → credits descending</li>
   *   <li><b>order == 1</b>: credits ascending; tie → GPA descending</li>
   * </ul>
   *
   * @param order 0 for GPA-first order; 1 for Credits-first order
   * @throws IllegalArgumentException if {@code order} is not 0 or 1
   */
  public void setComparator(int order) throws IllegalArgumentException {
    if (order == 0) {
      // Order 0: GPA desc, then credits desc
      studentComparator = (a, b) -> {
        int c = Double.compare(b.getGpa(), a.getGpa());
        if (c != 0) return c;
        return Integer.compare(b.getCreditsTaken(), a.getCreditsTaken());
      };
    } else if (order == 1) {
      // Order 1: credits asc, then GPA desc
      studentComparator = (a, b) -> {
        int c = Integer.compare(a.getCreditsTaken(), b.getCreditsTaken());
        if (c != 0) return c;
        return Double.compare(b.getGpa(), a.getGpa());
      };
    } else {
      throw new IllegalArgumentException("order must be 0 or 1");
    }
  }

  /**
   * Sorts {@link #students} in-place using the algorithm defined by the concrete subclass.
   * Subclasses must rely exclusively on {@link #studentComparator} for all element comparisons.
   */
  public abstract void sort();

  /**
   * Returns the element at the median index of the current {@link #students} array.
   * If the array is empty, returns {@code null}.
   *
   * <p>Note: the array should already be sorted under the intended order before calling this
   * method, otherwise the returned element is not meaningful as a median.</p>
   *
   * @return the median element, or {@code null} if the array is empty
   */
  public Student getMedian() {
    // Guard: no median exists for an empty dataset
    if (students.length == 0) {
      return null;
    }
    return students[students.length / 2];
  }

  /**
   * Exchanges {@code students[i]} and {@code students[j]} within the internal array.
   *
   * @param i first index
   * @param j second index
   */
  protected void swap(int i, int j) {
    // Standard three-step swap on the internal working array
    Student temp = students[i];
    students[i] = students[j];
    students[j] = temp;
  }
}
