package edu.iastate.cs2280.hw2;

/**
 * The {@code Student} class models a student record for the HW2 sorting assignment.
 * Each student has a GPA in the range [0.00, 4.00] and a non-negative number of credits taken.
 * <p>
 * This class defines a <em>natural ordering</em> used by the sorting algorithms:
 * <ul>
 *   <li>Primary key: GPA in <b>descending</b> order</li>
 *   <li>Secondary key (tie-breaker): credits taken in <b>descending</b> order</li>
 * </ul>
 * The natural order supports comparisons in {@link #compareTo(Student)} and is used
 * by the sorting framework in {@link edu.iastate.cs2280.hw2.AbstractSorter}.
 * </p>
 *
 * <p>This class is immutable and provides appropriate implementations of
 * {@link #equals(Object)}, {@link #hashCode()}, and {@link #toString()} for comparison,
 * hashing, and readable output.</p>
 *
 * @author Kaleb
 * @see Comparable
 * @see edu.iastate.cs2280.hw2.AbstractSorter
 */
public class Student implements Comparable<Student> {
  /**
   * The student's Grade Point Average.
   */
  private final double gpa;
  /**
   * The total number of credits the student has taken.
   */
  private final int creditsTaken;

  /**
   * Constructs a new Student with a specified GPA and number of credits.
   *
   * @param gpa          The GPA of the student (must be between 0.0 and 4.0).
   * @param creditsTaken The number of credits taken (cannot be negative).
   * @throws IllegalArgumentException if GPA or creditsTaken are out of valid range.
   */
  public Student(double gpa, int creditsTaken) {
    if (gpa < 0.0 || gpa > 4.0){
      throw new IllegalArgumentException();
    }
    if (creditsTaken < 0) {
      throw new IllegalArgumentException();
    }
    this.gpa = gpa;
    this.creditsTaken = creditsTaken;
  }

  /**
   * Copy constructor. Creates a new Student object by copying the data from another.
   *
   * @param other The Student object to copy.
   * @throws IllegalArgumentException if {@code other} is {@code null}
   */
  public Student(Student other) {
    if (other == null) {
      throw new IllegalArgumentException();
    }

    this.gpa = other.gpa;
    this.creditsTaken = other.creditsTaken;
  }

  /**
   * Gets the student's GPA.
   *
   * @return The GPA.
   */
  public double getGpa() {
    return gpa;
  }

  /**
   * Gets the number of credits the student has taken.
   *
   * @return The total credits taken.
   */
  public int getCreditsTaken() {
    return creditsTaken;
  }

  /**
   * Provides a string representation of the Student object.
   *
   * @return A formatted string showing the student's GPA and credits.
   */
  @Override
  public String toString() {
    return String.format("(GPA: %.2f, Credits: %d)", gpa, creditsTaken);
  }

  /**
   * Compares this student with another for natural ordering. The comparison is
   * based first on GPA in descending order, and then by credits taken in
   * descending order as a tie-breaker.
   * Natural ordering: GPA descending, then credits descending.
   *
   * @param other The other student to be compared.
   * @return A negative integer, zero, or a positive integer if this student is
   * greater than, equal to, or less than the specified student, respectively,
   * based on the defined natural order.
   */
  @Override
  public int compareTo(Student other) {
    int cmp = Double.compare(other.gpa, this.gpa);

    if (cmp != 0) {
      return cmp;
    }
    return Integer.compare(other.creditsTaken, this.creditsTaken);
  }

  /**
   * Compares this Student object to another object for equality.
   * The comparison is based on GPA and the number of credits taken.
   *
   * @param other The object to be compared with this Student.
   * @return true if the given object is a Student and has the same GPA
   *         and number of credits taken as this Student, false otherwise.
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Student)) {
      return false;
    }
    Student o = (Student) other;
    return Double.compare(this.gpa, o.gpa) == 0 && this.creditsTaken == o.creditsTaken;
  }


  /**
   * Returns a hash code consistent with {@link #equals(Object)}, derived from GPA and credits taken.
   *
   * @return the hash code of this student
   */
  @Override
  public int hashCode() {
    int result = Double.hashCode(gpa);
    result = 31 * result + Integer.hashCode(creditsTaken);
    return result;
  }
}
