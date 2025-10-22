

# COMS 2280 – HW2: Sorting Performance Analysis

This project implements and compares four sorting algorithms—**Selection Sort**, **Insertion Sort**, **Merge Sort**, and **Quick Sort**—on an array of `Student` objects.  
Each algorithm measures runtime and reports a median student profile based on GPA and credits.

---

## 📘 Overview

The program sorts data according to two comparator orders defined in the assignment specification:

| Order | Primary Key | Secondary Key |
|-------|--------------|----------------|
| 0 | GPA (descending) | Credits (descending) |
| 1 | Credits (ascending) | GPA (descending) |

Each algorithm is implemented in its own class that extends `AbstractSorter`.  
The `CompareSorters` driver measures performance and prints a formatted runtime table.

---

## 🧩 Project Structure

```
src/
├── main/java/edu/iastate/cs2280/hw2/
│   ├── AbstractSorter.java
│   ├── Algorithm.java
│   ├── CompareSorters.java
│   ├── InsertionSorter.java
│   ├── MergeSorter.java
│   ├── QuickSorter.java
│   ├── SelectionSorter.java
│   ├── Student.java
│   └── StudentScanner.java
└── test/java/edu/iastate/cs2280/hw2/
    ├── SelectionSorterTest.java
    ├── InsertionSorterTest.java
    ├── MergeSorterTest.java
    └── QuickSorterTest.java
```

---

## ⚙️ How to Run

### Option 1: Run the driver (`CompareSorters`)
To compare runtimes and view the median student profile:
```
keys: 1 (random student data) 2 (file input) 3 (exit)
Trial 1: 1
Enter number of random students: 10
```

**Sample Output**
```
algorithm       size       time (ns)
------------------------------------
SelectionSort   10         125000
InsertionSort   10          89000
MergeSort       10          43000
QuickSort       10          20000
------------------------------------
Median Student Profile: (GPA: 3.40, Credits: 75)
Exiting program.
```

### Option 2: Run the test suite
All automated tests are written using **JUnit 5**.  
In IntelliJ, right-click the `test` directory → *Run Tests in edu.iastate.cs2280.hw2*.

---

## 🧠 Key Features
- Implements four classic sorting algorithms.
- Measures performance using `System.nanoTime()`.
- Computes a **median student** using two-pass sorting (Order 0 → GPA, Order 1 → Credits).
- Implements **median-of-three Quick Sort** pivot selection.
- Gracefully handles empty arrays and invalid inputs.
- Fully documented with Javadoc and formatted output matching spec requirements.

---

## 🧪 Testing

Each sorter is tested for:
- Order 0 and Order 1 sorting correctness
- Empty and single-element arrays
- Duplicate stability
- Median calculation

Example (JUnit):
```java
@Test
void testOrder0() {
    Student[] arr = {
        new Student(3.8, 40),
        new Student(4.0, 10),
        new Student(2.5, 90)
    };
    AbstractSorter sorter = new MergeSorter(arr);
    sorter.setComparator(0);
    sorter.sort();
    assertEquals(4.0, sorter.getMedian().getGpa());
}
```

---

## 🧾 Output Format

Each report uses consistent spacing for Gradescope compliance:
```
algorithm       size       time (ns)
------------------------------------
QuickSort       5          7547
------------------------------------
```

---

## 👨‍💻 Author

**Kaleb Wrigley**  
Software Engineering Major  
Iowa State University – COMS 2280 (Fall 2025)

---

## 🏫 Course Information
- **Course:** COMS 2280 – Introduction to Data Structures  
- **Institution:** Iowa State University  
- **Semester:** Fall 2025