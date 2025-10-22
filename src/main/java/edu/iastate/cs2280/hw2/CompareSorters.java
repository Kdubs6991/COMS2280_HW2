
package edu.iastate.cs2280.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
/**
 * The {@code CompareSorters} class is the console driver for HW2.
 * <p>
 * It presents the menu, reads or generates {@link Student} input, runs all four
 * algorithms (Selection, Insertion, Merge, Quick) via {@link StudentScanner},
 * * prints the aligned performance table, shows the median student, and optionally
 * exports the results to CSV. All prompts and error messages match the working
 * example in the homework PDF.
 * </p>
 *
 * <p><b>I/O contract:</b> Input comes either from a randomly generated dataset size
 * or a filename whose lines are of the form "{@code <gpa> <credits>}". For each run,
 * the program prints a table with columns {@code algorithm size time (ns)} followed
 * by the median student profile. On request, results are written to CSV with
 * header {@code algorithm,size,time_ns}.
 * </p>
 *
 * @see StudentScanner
 * @see Student
 * @see Algorithm
 * @author Kaleb
 */
public class CompareSorters {

  /**
   * Program entry point and user interaction loop. Follows the PDF sample interaction:
   * <ul>
   *   <li>Trial prompts and menu selection (1=random, 2=file, 3=exit)</li>
   *   <li>Random/file input path with validation and exact error messages</li>
   *   <li>Timing all four algorithms and reporting aligned stats</li>
   *   <li>Optional CSV export of the results</li>
   * </ul>
   *
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("Sorting Algorithms Performance Analysis using Student Data\n");
    System.out.println("keys: 1 (random student data) 2 (file input) 3 (exit)");

    Scanner scan = new Scanner(System.in);

    // Menu loop state (trial counter) and random generator
    int trial = 1;
    Random rand = new Random();


    // Main menu loop: read choice, dispatch, repeat until exit
    while (true) {
      System.out.print("Trial " + trial + ": ");

      // Parse menu choice; enforce exact error message on bad input
      String input = scan.nextLine();
      int choice;
      try {
        choice = Integer.parseInt(input.trim());
      } catch (NumberFormatException e){
        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        trial++;
        continue;
      }

      switch (choice) {
        case 1: {
          // Random data path: read and validate number of students
          System.out.print("Enter number of random students: ");
          String numInput = scan.nextLine();
          int numStudents;
          try {
            numStudents = Integer.parseInt(numInput.trim());
          } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter an integer.");
            break;
          }

          if (numStudents < 1) {
            System.out.println("Number of students must be at least 1.");
            break;
          }

          // Generate dataset and run all algorithms via StudentScanner
          Student[] students = generateRandomStudents(numStudents, rand);
          StudentScanner[] scanners = new StudentScanner[Algorithm.values().length];

          for (int i = 0; i < Algorithm.values().length; i++) {
            Algorithm algo = Algorithm.values()[i];
            scanners[i] = new StudentScanner(students, algo);
            scanners[i].scan();
          }

          // Print results table and median profile (format must match PDF)
          System.out.println();
          System.out.println("algorithm size time (ns)");
          System.out.println("------------------------------------");

          for (int i = 0; i < scanners.length; i++) {
            System.out.println(scanners[i].stats());
          }

          System.out.println("------------------------------------");

          Student median = scanners[0].getMedianStudent();
          System.out.println("\nMedian Student Profile: (GPA: " + String.format(java.util.Locale.US,"%.2f", median.getGpa()) + ", Credits: " + median.getCreditsTaken() + ")");

          // Offer CSV export prompt as in the working example
          handleExportOption(scan, scanners);
          trial++;
          break;
        }
        case 2: {
          // File input path: read filename and parse "<gpa> <credits>" lines
          System.out.print("File name: ");
          String filename = scan.nextLine();

          // Enforce exact error messages for missing file / bad format
          Student[] students = null;
          try {
            students = readStudentsFromFile(filename);
          } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + filename);
            break;
          } catch (InputMismatchException e) {
            System.out.println("Error: Input file format is incorrect. " + e.getMessage());
            break;
          }

          // Run all algorithms on the parsed dataset
          StudentScanner[] scanners = new StudentScanner[Algorithm.values().length];

          for (int i = 0; i < Algorithm.values().length; i++) {
            Algorithm algo = Algorithm.values()[i];
            scanners[i] = new StudentScanner(students, algo);
            scanners[i].scan();
          }

          // Print results table and median profile (format must match PDF)
          System.out.println();
          System.out.println("algorithm size time (ns)");
          System.out.println("------------------------------------");

          for (int i = 0; i < scanners.length; i++) {
            System.out.println(scanners[i].stats());
          }

          System.out.println("------------------------------------");

          Student median = scanners[0].getMedianStudent();
          System.out.println("\nMedian Student Profile: (GPA: " + String.format(java.util.Locale.US,"%.2f", median.getGpa()) + ", Credits: " + median.getCreditsTaken() + ")");

          // Offer CSV export prompt as in the working example
          handleExportOption(scan, scanners);
          trial++;
          break;
        }
        case 3: {
          System.out.println("Exiting program.");
          // Close scanner and exit program cleanly
          scan.close();
          return;
        }
        default: {
          System.out.println("Invalid choice. Please enter 1, 2, or 3.");
          break;
        }
      }
    }
  }

  /**
   * Prompts for CSV export and writes a file with header {@code algorithm,size,time_ns}.
   * Lines are derived from each {@link StudentScanner#stats()} string.
   * Any file error prints the required error message and returns without throwing.
   *
   * @param scan console scanner
   * @param scanners timing results for all algorithms
   */
  private static void handleExportOption(Scanner scan, StudentScanner[] scanners) {
    // Ask user whether to export the current table to CSV
    System.out.print("Export results to CSV? (y/n): ");
    String yn = scan.nextLine();

    if (yn.length() == 0 || (yn.charAt(0) != 'y' && yn.charAt(0) != 'Y')) {
      return;
    }

    System.out.print("Enter filename for export (e.g., results.csv): ");
    String filename = scan.nextLine();

    try (PrintWriter pw = new PrintWriter(filename)){
      pw.println("algorithm,size,time_ns");

      for (int i = 0; i < scanners.length; i++) {
        String s = scanners[i].stats().trim();
        String[] parts = s.split("\\s+");
        if (parts.length >= 3) {
          pw.println(parts[0] + "," + parts[1] + "," + parts[2]);
        } else {
          pw.println(s);
        }
      }

      System.out.println("Data exported successfully to " + filename);
    } catch (FileNotFoundException e) {
      System.out.println("Error: Could not write to file: " + filename);
    }
  }

  /**
   * Creates {@code numStudents} random students.
   * <ul>
   *   <li>GPA in [0.00, 4.00] to two decimals</li>
   *   <li>Credits in [0..150]</li>
   * </ul>
   *
   * @param numStudents number of students (must be ≥ 1)
   * @param rand source of randomness
   * @return array of students
   * @throws IllegalArgumentException if {@code numStudents < 1}
   */
  public static Student[] generateRandomStudents(int numStudents, Random rand) {
    if (numStudents < 1) {
      throw new IllegalArgumentException("numStudents must be at least 1.");
    }

    Student[] students = new Student[numStudents];
    for (int i = 0; i < numStudents; i++) {
      // Generate GPA (0.00–4.00) and credits (0–150) per spec
      double gpa = Math.round(rand.nextDouble() * 400) / 100.0; //0.00 to 4.00
      int credits = rand.nextInt(151); //0...150
      students[i] = new Student(gpa, credits);
    }
    return students;
  }

  /**
   * Reads whitespace-separated {@code double} GPA and {@code int} credits per line.
   * Skips blank lines. Throws with PDF-specified messages on format problems or empty data.
   *
   * @param filename input filename
   * @return parsed students (in input order)
   * @throws FileNotFoundException if file is missing
   * @throws InputMismatchException if a line lacks valid {@code double} then {@code int}, or if no valid lines exist
   */
  private static Student[] readStudentsFromFile(String filename) throws FileNotFoundException, InputMismatchException {
    // Parse file according to "<gpa> <credits>" per line; enforce exact error messages
    File file = new File(filename);
    Scanner fs = new Scanner(file);
    ArrayList<Student> results = new ArrayList<>();

    while (fs.hasNextLine()) {
      String line = fs.nextLine().trim();
      if (line.isEmpty()) continue;
      Scanner ls = new Scanner(line);
      if (!ls.hasNext()) {
        ls.close();
        continue;
      }

      double gpa;
      if (!ls.hasNextDouble()) {
        ls.close();
        throw new InputMismatchException("File format error: Invalid GPA format. Expected a double.");
      }

      gpa = ls.nextDouble();
      if (!ls.hasNext()) {
        ls.close();
        throw new InputMismatchException("File format error: Invalid credits format. Expected an integer.");
      }

      int credits;
      if (!ls.hasNextInt()) {
        ls.close();
        throw new InputMismatchException("File format error: Invalid credits format. Expected an integer.");
      }

      credits = ls.nextInt();
      results.add(new Student(gpa, credits));
      ls.close();
    }

    fs.close();
    if (results.isEmpty()) {
      throw new InputMismatchException("File is empty or contains no valid student data.");
    }

    return results.toArray(new Student[0]);
  }
}
