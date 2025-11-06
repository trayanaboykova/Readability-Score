# 📚 Readability Score (Java)

A console-based text analysis program that determines how easy or difficult a given text is to read — using scientific readability formulas such as the Automated Readability Index (ARI), Flesch–Kincaid, SMOG, and Coleman–Liau.
It evaluates the text by analyzing characters, words, sentences, syllables, and polysyllables, then estimates the reader’s required age and education level.

Developed as part of my **[JetBrains Academy](https://www.jetbrains.com/academy/)** learning path, this project showcases advanced string processing, file I/O, and algorithmic text scoring in Java.

---

## 🚀 Project Overview

The project evolves through multiple stages, introducing new computational and analytical techniques:

- **File Reading** – Reads text files via command-line arguments using `java.nio.file.Files`.
- **Statistical Analysis** – Counts sentences, words, characters, syllables, and polysyllables.
- **Readability Formulas** – Implements major indices:
  - Automated Readability Index (ARI)
  - Flesch–Kincaid Readability Tests (FK)
  - Simple Measure of Gobbledygook (SMOG)
  - Coleman–Liau Index (CL)
- **Dynamic User Choice** – Lets users select which index to calculate or run all at once.
- **“Age Estimation** – Converts the computed index into a corresponding reader age range.
- **Averaged Output** – Displays the mean reader age across all formulas for better understanding.

---

## 🎯 What I Learned

- 📘 **File Handling:** Reading and processing text from external files safely using Java I/O.
- 🧮 **Text Analysis:** Implementing regex-based sentence splitting and word/syllable detection.
- 🧠 **Algorithm Implementation:**Translating mathematical readability formulas into Java code.
- 🔢 **Data Processing:** Managing multi-step calculations, rounding, and formatted output.
- 🧩 **Modular Thinking:** Structuring code to handle multiple metrics cleanly and efficiently.

---

## 🔧 Features

- ✅ Reads input text from `.txt` files provided as command-line arguments.
- ✅ Counts characters, words, sentences, syllables, and polysyllables accurately.
- ✅ Calculates ARI, FK, SMOG, and CL readability indices.
- ✅ Estimates the reader’s age and education level for each index.
- ✅ Supports both single and “all” mode readability computations.
- ✅ Outputs neatly formatted, human-readable results.

---

## 🛠️ Technologies Used

[![Java](https://skillicons.dev/icons?i=java&theme=light)](https://www.java.com/en/)

---

## 🤔 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/trayanaboykova/Readability-Score.git
2. Open the project in your Java IDE (e.g., IntelliJ IDEA).
3. Compile and run `Main.java` with a text file argument:
   ```bash
   java Main input.txt
5. Example console interaction:
    ```bash
   The text is:
    Readability is the ease with which a reader can understand a written text.
    Words: 108
    Sentences: 6
    Characters: 580
    Syllables: 196
    Polysyllables: 20

    Enter the score you want to calculate (ARI, FK, SMOG, CL, all): all
    Automated Readability Index: 12.86 (about 18-year-olds).
    Flesch–Kincaid readability tests: 12.84 (about 18-year-olds).
    Simple Measure of Gobbledygook: 13.56 (about 22-year-olds).
    Coleman–Liau index: 14.13 (about 22-year-olds).
    This text should be understood in average by 20.00-year-olds.


## 📈 Learning Outcomes
By completing this project, I:

Strengthened my ability to analyze and process large text data efficiently.

Learned how to implement and compare multiple scientific readability metrics.

Improved my understanding of Java’s file I/O, loops, and string operations.

Practiced designing user-interactive, data-driven console applications.

## 🌟 Acknowledgments

Thanks to JetBrains Academy / Hyperskill for creating structured, multi-stage projects that bridge mathematical theory and real-world programming practice — helping me grow my Java text-processing and analytical problem-solving skills.
