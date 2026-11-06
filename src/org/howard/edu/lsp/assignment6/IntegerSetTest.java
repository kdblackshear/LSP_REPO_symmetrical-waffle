package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Assignment 6: JUnit 5 tests for IntegerSet.
 *
 * Complete every TODO. Do not remove, rename, or disable test methods.
 * Each test must contain meaningful assertions and run independently.
 */
public class IntegerSetTest {

    // ===== clear() =====

    @Test
    public void testClearClearsPopulatedSet() {
        IntegerSet set = new IntegerSet();
        set.add(10);
        set.add(20);
        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    @Test
    public void testClearClearsEmptySet() {
        IntegerSet set = new IntegerSet();
        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    // ===== length() =====

    @Test
    public void testLengthCountsUniqueElements() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(5);
        set.add(10);
        set.add(15);
        set.add(15);
        assertEquals(3, set.length());
    }

    @Test
    public void testLengthEmptySetLength() {
        IntegerSet set = new IntegerSet();
        assertEquals(0, set.length());
    }

    // ===== equals() =====

    @Test
    public void testEqualsIgnoresInsertionOrder() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(3);
        set2.add(1);
        set2.add(2);

        assertTrue(set1.equals(set2));
    }

    @Test
    public void testEqualsDetectsDifferentContents() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(1);
        set2.add(99);

        assertFalse(set1.equals(set2));
    }

    @Test
    public void testEqualsEmptySetsEqual() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();
        assertTrue(set1.equals(set2));
    }

    @Test
    public void testEqualsSameObjectAndDifferentSizes() {
        IntegerSet set1 = new IntegerSet();
        set1.add(10);

        IntegerSet set2 = new IntegerSet();
        set2.add(10);
        set2.add(20);

        assertTrue(set1.equals(set1));
        assertFalse(set1.equals(set2));
        assertFalse(set1.equals(null));
    }

    // ===== contains() =====

    @Test
    public void testContainsPresentAndAbsent() {
        IntegerSet set = new IntegerSet();
        set.add(42);
        assertTrue(set.contains(42));
        assertFalse(set.contains(99));
    }

    @Test
    public void testContainsEmptySetContainsNothing() {
        IntegerSet set = new IntegerSet();
        assertFalse(set.contains(5));
    }

    // ===== largest() =====

    @Test
    public void testLargestNegativeAndPositiveValues() {
        IntegerSet set = new IntegerSet();
        set.add(-10);
        set.add(0);
        set.add(25);
        set.add(-3);
        assertEquals(25, set.largest());
    }

    @Test
    public void testLargestEmptySetThrowsException() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> set.largest());
    }

    // ===== smallest() =====

    @Test
    public void testSmallestNegativeAndPositiveValues() {
        IntegerSet set = new IntegerSet();
        set.add(-50);
        set.add(0);
        set.add(12);
        set.add(-5);
        assertEquals(-50, set.smallest());
    }

    @Test
    public void testSmallestEmptySetThrowsException() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> set.smallest());
    }

    // ===== add() =====

    @Test
    public void testAddIgnoresDuplicates() {
        IntegerSet set = new IntegerSet();
        set.add(7);
        set.add(7);
        set.add(7);
        assertEquals(1, set.length());
        assertEquals("[7]", set.toString());
    }

    @Test
    public void testAddHandlesIntegerBoundaries() {
        IntegerSet set = new IntegerSet();
        set.add(Integer.MIN_VALUE);
        set.add(Integer.MAX_VALUE);
        set.add(0);
        assertEquals(3, set.length());
        assertTrue(set.contains(Integer.MIN_VALUE));
        assertTrue(set.contains(Integer.MAX_VALUE));
        assertTrue(set.contains(0));
    }

    // ===== remove() =====

    @Test
    public void testRemoveRemovesExistingElement() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.remove(1);
        assertFalse(set.contains(1));
        assertTrue(set.contains(2));
        assertEquals(1, set.length());
    }

    @Test
    public void testRemoveIgnoresAbsentElement() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.remove(99);
        assertEquals(1, set.length());
        assertTrue(set.contains(5));
    }

    // ===== union() =====

    @Test
    public void testUnionOverlappingSets() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        IntegerSet unionSet = set1.union(set2);
        assertEquals(3, unionSet.length());
        assertTrue(unionSet.contains(1));
        assertTrue(unionSet.contains(2));
        assertTrue(unionSet.contains(3));
    }

    @Test
    public void testUnionEmptyAndSameOperand() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet empty = new IntegerSet();
        IntegerSet result1 = set1.union(empty);
        assertEquals(1, result1.length());
        assertTrue(result1.contains(1));

        IntegerSet result2 = set1.union(set1);
        assertEquals(1, result2.length());
        assertTrue(result2.contains(1));
    }

    @Test
    public void testUnionDoesNotMutateInputs() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        IntegerSet set2 = new IntegerSet();
        set2.add(2);

        IntegerSet unionSet = set1.union(set2);
        unionSet.add(99);

        assertEquals(1, set1.length());
        assertEquals(1, set2.length());
        assertFalse(set1.contains(99));
        assertFalse(set2.contains(99));
    }

    // ===== intersect() =====

    @Test
    public void testIntersectOverlappingSets() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);
        set2.add(4);

        IntegerSet intersectSet = set1.intersect(set2);
        assertEquals(2, intersectSet.length());
        assertTrue(intersectSet.contains(2));
        assertTrue(intersectSet.contains(3));
        assertFalse(intersectSet.contains(1));
    }

    @Test
    public void testIntersectDisjointAndSameOperand() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);

        IntegerSet disjointResult = set1.intersect(set2);
        assertTrue(disjointResult.isEmpty());

        IntegerSet selfResult = set1.intersect(set1);
        assertEquals(1, selfResult.length());
        assertTrue(selfResult.contains(1));
    }

    @Test
    public void testIntersectIndependentResult() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);

        IntegerSet intersectSet = set1.intersect(set2);
        intersectSet.add(99);

        assertEquals(2, set1.length());
        assertEquals(1, set2.length());
        assertFalse(set1.contains(99));
    }

    // ===== diff() =====

    @Test
    public void testDiffDirectionMatters() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        IntegerSet diff1 = set1.diff(set2);
        assertEquals(1, diff1.length());
        assertTrue(diff1.contains(1));
        assertFalse(diff1.contains(2));

        IntegerSet diff2 = set2.diff(set1);
        assertEquals(1, diff2.length());
        assertTrue(diff2.contains(3));
        assertFalse(diff2.contains(2));
    }

    @Test
    public void testDiffEmptyAndSameOperand() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet empty = new IntegerSet();
        IntegerSet result1 = set1.diff(empty);
        assertEquals(1, result1.length());

        IntegerSet result2 = set1.diff(set1);
        assertTrue(result2.isEmpty());
    }

    @Test
    public void testDiffIndependentResult() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);

        IntegerSet diffSet = set1.diff(set2);
        diffSet.add(99);

        assertEquals(2, set1.length());
        assertFalse(set1.contains(99));
    }

    // ===== complement() =====

    @Test
    public void testComplementReverseDifference() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        // Complement definition: b \ this => {2, 3} \ {1, 2} = {3}
        IntegerSet comp = set1.complement(set2);
        assertEquals(1, comp.length());
        assertTrue(comp.contains(3));
        assertFalse(comp.contains(1));
    }

    @Test
    public void testComplementEmptyOperands() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();
        set2.add(5);

        IntegerSet comp1 = set1.complement(set2);
        assertEquals(1, comp1.length());
        assertTrue(comp1.contains(5));

        IntegerSet comp2 = set2.complement(set1);
        assertTrue(comp2.isEmpty());
    }

    @Test
    public void testComplementIndependentResult() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet set2 = new IntegerSet();
        set2.add(1);
        set2.add(2);

        IntegerSet comp = set1.complement(set2);
        comp.add(99);

        assertEquals(1, set1.length());
        assertFalse(set1.contains(99));
    }

    // ===== isSubsetOf() =====

    @Test
    public void testIsSubsetOfProperSubset() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet set2 = new IntegerSet();
        set2.add(1);
        set2.add(2);

        assertTrue(set1.isSubsetOf(set2));
        assertFalse(set2.isSubsetOf(set1));
    }

    @Test
    public void testIsSubsetOfNotSubset() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(1);
        set2.add(2);

        assertFalse(set1.isSubsetOf(set2));
    }

    @Test
    public void testIsSubsetOfEqualSets() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(1);
        set2.add(2);

        assertTrue(set1.isSubsetOf(set2));
        assertTrue(set2.isSubsetOf(set1));
    }

    @Test
    public void testIsSubsetOfEmptyCases() {
        IntegerSet empty = new IntegerSet();
        IntegerSet populated = new IntegerSet();
        populated.add(1);

        assertTrue(empty.isSubsetOf(populated));
        assertFalse(populated.isSubsetOf(empty));
    }

    // ===== isDisjoint() =====

    @Test
    public void testIsDisjointNoOverlap() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);

        assertTrue(set1.isDisjoint(set2));
    }

    @Test
    public void testIsDisjointOverlap() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        assertFalse(set1.isDisjoint(set2));
    }

    @Test
    public void testIsDisjointEmptyAndSameOperand() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet empty = new IntegerSet();
        assertTrue(set1.isDisjoint(empty));
        assertFalse(set1.isDisjoint(set1));
    }

    // ===== symmetricDiff() =====

    @Test
    public void testSymmetricDiffPartiallyOverlappingSets() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        IntegerSet symDiff = set1.symmetricDiff(set2);
        assertEquals(2, symDiff.length());
        assertTrue(symDiff.contains(1));
        assertTrue(symDiff.contains(3));
        assertFalse(symDiff.contains(2));
    }

    @Test
    public void testSymmetricDiffIdenticalSets() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet symDiff = set1.symmetricDiff(set1);
        assertTrue(symDiff.isEmpty());
    }

    @Test
    public void testSymmetricDiffEmptyAndDisjointSets() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet empty = new IntegerSet();
        IntegerSet symDiffEmpty = set1.symmetricDiff(empty);
        assertEquals(1, symDiffEmpty.length());
        assertTrue(symDiffEmpty.contains(1));

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        IntegerSet symDiffDisjoint = set1.symmetricDiff(set2);
        assertEquals(2, symDiffDisjoint.length());
        assertTrue(symDiffDisjoint.contains(1));
        assertTrue(symDiffDisjoint.contains(2));
    }

    @Test
    public void testSymmetricDiffIndependentResult() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);

        IntegerSet symDiff = set1.symmetricDiff(set2);
        symDiff.add(99);

        assertEquals(1, set1.length());
        assertEquals(1, set2.length());
        assertFalse(set1.contains(99));
    }

    // ===== isEmpty() =====

    @Test
    public void testIsEmptyNewSet() {
        IntegerSet set = new IntegerSet();
        assertTrue(set.isEmpty());
    }

    @Test
    public void testIsEmptyPopulatedSet() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        assertFalse(set.isEmpty());
    }

    @Test
    public void testIsEmptyAfterClear() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.clear();
        assertTrue(set.isEmpty());
    }

    // ===== toString() =====

    @Test
    public void testToStringAscendingOrder() {
        IntegerSet set = new IntegerSet();
        set.add(30);
        set.add(10);
        set.add(20);
        assertEquals("[10, 20, 30]", set.toString());
    }

    @Test
    public void testToStringEmptyFormatting() {
        IntegerSet set = new IntegerSet();
        assertEquals("[]", set.toString());
    }

    @Test
    public void testToStringExactSpacingAndDuplicates() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(5);
        set.add(1);
        assertEquals("[1, 5]", set.toString());
    }
}