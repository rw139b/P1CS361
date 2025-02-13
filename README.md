# Project #1: Deterministic Finite Automata

* Author: Kodey Thompson, Ryan Wensmann
* Class: CS361 Section #002
* Semester: Spring 2025

## Overview

In this project, we implement a Java program to model a Deterministic Finite Automaton (DFA). The program allows users to define a DFA by specifying its states, alphabet, transition function, start state, and final states. The DFA can then be used to check whether a given string is accepted by the automaton based on the defined transitions. The project also includes functionality to manipulate DFAs, such as swapping transition symbols, and verifies the correctness of the implementation using JUnit tests.

## Reflection

This project provided a valuable experience in both understanding the principles behind finite automata and applying object-oriented design principles in Java. One of the most challenging aspects was ensuring that the transitions and state handling in the DFA were correctly implemented, especially given the intricacies of handling state sets and transition maps. However, the use of Java’s Collections API, specifically Set and Map, greatly facilitated the task, as it provided powerful data structures for managing the DFA elements.

One concept that still isn't entirely clear is how to optimize the DFA’s transition function when dealing with large alphabets or many states. While the basic implementation works as expected, handling more complex DFA structures could require further optimization techniques. In terms of debugging, I found that writing and running unit tests early was crucial, as it helped catch errors early in the development process.

If I could go back in time, I would advise myself to spend more time planning the structure of the DFAState and DFA classes before diving into coding. The initial implementation went smoothly, but later modifications were trickier than expected, especially in terms of managing state transitions and ensuring that the toString method outputs exactly as required.

## Compiling and Using

To compile the code, navigate to the project directory and run:
javac -cp .:/usr/share/java/junit.jar ./test/dfa/DFATest.java
To run the tests, use the following command:
java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar org.junit.runner.JUnitCore test.dfa.DFATest
This will execute the unit tests defined in the DFATest.java file.

## Sources used

Java API documentation for the java.util.Set and java.util.Map interfaces.
JUnit documentation for testing principles.