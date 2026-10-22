package org.howard.edu.lsp.assignment5;

import java.util.TreeSet;

/**
 * The IntegerSet class represents a mathematical set of integers.
 * It provides standard set operations such as union, intersection, difference,
 * subset checks, and utility methods like finding the largest and smallest elements.
 * Duplicate values are automatically ignored, and elements are maintained in 
 * ascending numerical order.
 */

public class IntegerSet {
	// Internal data structure using TreeSet to maintain elements in sorted order
	private TreeSet<Integer> set;
	
	/**
     * Default constructor that initializes an empty IntegerSet.
     */
	
	public IntegerSet() {
		this.set = new TreeSet<>();
	}
	
	/**
     * Removes all elements from the set, leaving it empty.
     */
	
	public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set (its cardinality).
     * 
     * @return the number of elements in the set
     */
    public int length() {
        return set.size();
    }

    /**
     * Compares this set with another IntegerSet for equality.
     * Two sets are equal if they contain exactly the same elements,
     * regardless of insertion order.
     * 
     * @param b the IntegerSet to compare against
     * @return true if both sets contain the same elements, false otherwise
     */
    public boolean equals(IntegerSet b) {
        if (b == null) {
            return false;
        }
        return this.set.equals(b.set);
    }

    /**
     * Checks whether the specified value is present in the set.
     * 
     * @param value the integer value to check for
     * @return true if the value is in the set, false otherwise
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest integer in the set.
     * 
     * @return the largest integer
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return set.last();
    }

    /**
     * Returns the smallest integer in the set.
     * 
     * @return the smallest integer
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return set.first();
    }

    /**
     * Adds an item to the set if it is not already present.
     * Duplicate values are ignored.
     * 
     * @param item the integer to add
     */
    public void add(int item) {
        set.add(item);
    }

    /**
     * Removes an item from the set if it is present.
     * Has no effect if the item is absent.
     * 
     * @param item the integer to remove
     */
    public void remove(int item) {
        set.remove(item);
    }

    /**
     * Returns a new IntegerSet containing all elements from this set and set b (Union).
     * Neither input set is modified.
     * 
     * @param b the other IntegerSet
     * @return a new IntegerSet representing the union
     */
    public IntegerSet union(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        result.set.addAll(this.set);
        if (b != null) {
            result.set.addAll(b.set);
        }
        return result;
    }

    /**
     * Returns a new IntegerSet containing only the elements shared by both sets (Intersection).
     * Neither input set is modified.
     * 
     * @param b the other IntegerSet
     * @return a new IntegerSet representing the intersection
     */
    public IntegerSet intersect(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        if (b != null) {
            result.set.addAll(this.set);
            result.set.retainAll(b.set);
        }
        return result;
    }

    /**
     * Returns a new IntegerSet containing elements in this set that are not in set b (Difference).
     * Neither input set is modified.
     * 
     * @param b the other IntegerSet
     * @return a new IntegerSet representing the difference (this \ b)
     */
    public IntegerSet diff(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        result.set.addAll(this.set);
        if (b != null) {
            result.set.removeAll(b.set);
        }
        return result;
    }

    /**
     * Returns a new IntegerSet containing elements in set b that are not in this set 
     * (Assignment-specific definition of complement: b \ this).
     * Neither input set is modified.
     * 
     * @param b the other IntegerSet
     * @return a new IntegerSet representing the complement
     */
    public IntegerSet complement(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        if (b != null) {
            result.set.addAll(b.set);
            result.set.removeAll(this.set);
        }
        return result;
    }

    /**
     * Checks if every element of this set is present in set b.
     * An empty set is considered a subset of every set.
     * 
     * @param b the other IntegerSet
     * @return true if this set is a subset of b, false otherwise
     */
    public boolean isSubsetOf(IntegerSet b) {
        if (b == null) {
            return false;
        }
        return b.set.containsAll(this.set);
    }

    /**
     * Checks if this set and set b share no common elements.
     * Empty sets are disjoint from all sets.
     * 
     * @param b the other IntegerSet
     * @return true if the two sets share no elements, false otherwise
     */
    public boolean isDisjoint(IntegerSet b) {
        if (b == null) {
            return true;
        }
        for (int val : this.set) {
            if (b.contains(val)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a new IntegerSet containing elements that occur in exactly one of the two sets 
     * (Symmetric Difference). Neither input set is modified.
     * 
     * @param b the other IntegerSet
     * @return a new IntegerSet representing the symmetric difference
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        if (b != null) {
            result.set.addAll(this.set);
            result.set.addAll(b.set);
            
            IntegerSet intersection = this.intersect(b);
            result.set.removeAll(intersection.set);
        } else {
            result.set.addAll(this.set);
        }
        return result;
    }

    /**
     * Checks whether the set is empty.
     * 
     * @return true if the set contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a string representation of the set, listing elements in ascending 
     * numerical order separated by a comma and a space, enclosed in square brackets.
     * 
     * @return string representation of the set
     */
    @Override
    public String toString() {
        return set.toString();
    }
}