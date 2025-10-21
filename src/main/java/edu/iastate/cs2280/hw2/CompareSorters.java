/**
 * @author
 */
package edu.iastate.cs2280.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class serves as the main driver for the sorting algorithm comparison application.
 * It provides a prompt interface for users to generate or load student data,
 * run four different sorting algorithms (Selection, Insertion, Merge, Quick),
 * and compare their performance based on execution time. The results can be
 * displayed in the console and optionally exported to a CSV file.
 */
public class CompareSorters {

  /**
   * The main entry point of the application. It presents a menu to the user to
   * choose between generating random student data, reading data from a file, or exiting.
   * For each set of data, it runs the sorting algorithms and prints their performance statistics.
   * Should handle InputMismatchExceptions and FileNotFoundExceptions gracefully.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    System.out.println("Sorting Algorithms Performance Analysis using Student Data\n");
    System.out.println("keys:  1 (random student data)  2 (file input)  3 (exit)");
    Scanner scan = new Scanner(System.in);

    // Write your logic here

    System.out.println("Exiting program.");
    scan.close();
  }

  /**
   * Handles the user prompt for exporting sorting performance results to a CSV file.
   * This method catches and handles a {@link FileNotFoundException} if the specified
   * output file cannot be created or written to, printing an error message to the console.
   *
   * @param scan     The Scanner object to read user input.
   * @param scanners An array of StudentScanner objects containing the performance stats.
   */
  private static void handleExportOption(Scanner scan, StudentScanner[] scanners) {
  }

  /**
   * Generates an array of Student objects with random GPA and credit values.
   *
   * @param numStudents The number of random students to generate.
   * @param rand        The Random object to use for generating values.
   * @return An array of randomly generated Student objects.
   * @throws IllegalArgumentException if numStudents is less than 1.
   */
  public static Student[] generateRandomStudents(int numStudents, Random rand) {
  }

  /**
   * Reads student data from a text file. Each line of the file should contain a
   * GPA (double) followed by credits taken (int), separated by whitespace.
   *
   * @param filename The name of the file to read from.
   * @return An array of Student objects created from the file data.
   * @throws FileNotFoundException    if the specified file does not exist.
   * @throws InputMismatchException   if the file content is not in the expected format
   * (e.g., non-numeric data, empty file).
   */
  private static Student[] readStudentsFromFile(String filename) throws FileNotFoundException, InputMismatchException {
  }
}
