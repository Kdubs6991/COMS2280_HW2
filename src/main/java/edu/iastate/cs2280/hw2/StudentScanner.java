package edu.iastate.cs2280.hw2;

/**
 * The {@code StudentScanner} orchestrates a two-pass sorting procedure to compute a
 * "median student" for the HW2 assignment and to measure total runtime for a chosen algorithm.
 *
 * <p><b>Two-pass median procedure (per spec):</b>
 * <ol>
 *   <li>Sort by <b>Order 0</b> (GPA descending; tie → credits descending) and take the median GPA.</li>
 *   <li>Sort by <b>Order 1</b> (credits ascending; tie → GPA descending) and take the median credits.</li>
 * </ol>
 * The result is a new {@link Student} whose GPA is the median from pass 1 and whose credits
 * is the median from pass 2.</p>
 *
 * <p>Timing is measured in nanoseconds using {@link System#nanoTime()} and includes both
 * sorting passes executed by the selected algorithm.</p>
 *
 * @see AbstractSorter
 * @see Algorithm
 * @see Student
 *
 * @author Kaleb
 */
public class StudentScanner {

  // Internal working copy of the dataset (deep-copied in the constructor)
  private final Student[] students;

  // Algorithm under test for timing and median computation
  private final Algorithm sortingAlgorithm;

  // Total elapsed time in nanoseconds for the two-pass sort
  protected long scanTime;

  // Median result constructed from pass-1 GPA and pass-2 credits
  private Student medianStudent;

  /**
   * Constructs a scanner for a specific sorting algorithm over a deep copy of {@code students}.
   * The input array is not modified; each element is copied into an internal working array.
   *
   * @param students input dataset; must be non-null, non-empty, and contain no null elements
   * @param algo algorithm to evaluate (SelectionSort, InsertionSort, MergeSort, or QuickSort)
   * @throws IllegalArgumentException if {@code students} is null/empty or contains nulls, or if {@code algo} is null
   */
  public StudentScanner(Student[] students, Algorithm algo) {
    if (students == null || students.length == 0) {
      throw new IllegalArgumentException("Students array must not be null or empty");
    }
    if (algo == null) {
      throw new IllegalArgumentException();
    }

    this.students = new Student[students.length];
    for (int i = 0; i < students.length; i++) {
      if (students[i] == null) {
        throw new IllegalArgumentException();
      }

      // Deep copy the dataset so benchmarking does not mutate client data
      this.students[i] = new Student(students[i]);
    }

    this.sortingAlgorithm = algo;
    this.scanTime = 0L;
    this.medianStudent = null;
  }

  /**
   * Executes the two-pass median workflow and measures total runtime in nanoseconds.
   * <ol>
   *   <li>Instantiate the concrete sorter corresponding to {@link #sortingAlgorithm}.</li>
   *   <li>Set comparator to order 0 and sort; read median GPA.</li>
   *   <li>Set comparator to order 1 and sort; read median credits.</li>
   *   <li>Construct {@link #medianStudent} and record {@link #scanTime}.</li>
   * </ol>
   */
  public void scan() {
    // Create the appropriate sorter instance for this trial
    AbstractSorter sorter;
    switch (sortingAlgorithm) {
      case SelectionSort:
        sorter = new SelectionSorter(students);
        break;
      case InsertionSort:
        sorter = new InsertionSorter(students);
        break;
      case MergeSort:
        sorter = new MergeSorter(students);
        break;
      case QuickSort:
        sorter = new QuickSorter(students);
        break;
      default:
        throw new IllegalArgumentException();
    }
    // Pass 1: GPA desc (tie → credits desc)
    sorter.setComparator(0);
    long start = System.nanoTime();
    sorter.sort();
    Student mid0 = sorter.getMedian();
    double medianGpa = (mid0 == null) ? 0.0 : mid0.getGpa();

    // Pass 2: credits asc (tie → GPA desc)
    sorter.setComparator(1);
    sorter.sort();
    Student mid1 = sorter.getMedian();
    int medianCredits = (mid1 == null) ? 0 : mid1.getCreditsTaken();
    this.medianStudent = new Student(medianGpa, medianCredits);

    // Total elapsed time for both passes
    this.scanTime = System.nanoTime() - start;
  }

  /**
   * Returns a single formatted line for the results table:
   * <pre>{@code
   * algorithm size time (ns)
   * }</pre>
   *
   * @return formatted stats row with algorithm name, data size, and total time in nanoseconds
   */
  public String stats() {
    return String.format("%-15s %-5d %-10d", sortingAlgorithm, students.length, scanTime);
  }

  /**
   * Returns the computed median student after {@link #scan()} completes.
   *
   * @return the median student; may be {@code null} if {@link #scan()} has not been called
   */
  public Student getMedianStudent() {
    return medianStudent;
  }

  /**
   * Returns a human-readable description of the computed median profile.
   *
   * @return a string beginning with {@code "Median Student: "}
   */
  @Override
  public String toString() {
    return (medianStudent == null) ? "Median Student: <not computed>" : "Median Student: " + medianStudent.toString();
  }
}
