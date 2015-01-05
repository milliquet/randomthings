package scottmilliquet.ca.questions;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 
 */

/**
 * @author scottmilliquet
 * 
 */
public class UnionIntersectionTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNormalArrayList() {
		ArrayList<Integer> a = createBaseArrayList(Lists.A);
		ArrayList<Integer> b = createBaseArrayList(Lists.B);

		ArrayList<Integer> unionOutput = new ArrayList<Integer>();
		ArrayList<Integer> intersectionOutput = new ArrayList<Integer>();

		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);
		
		ArrayList<Integer> expectedUnion = new ArrayList<Integer>();
		ArrayList<Integer> expectedIntersection = new ArrayList<Integer>();
		int[] eU = { 5, 8, 10, 12, 14, 17, 1, 3, 6, 7, 0, -1, -100, 100, 67, 13, 20, 22, 32, 43, 25, 34, 101, -12, 69, 75, 4, 9, 21, 47, 49, 51};
		int[] eI = { 17, 1, 3, 6, 10, -1, 20, 22 }; 

		for (int i = 0; i < eU.length; i++) {
			expectedUnion.add(new Integer(eU[i]));
		}
		for (int i = 0; i < eI.length; i++) {
			expectedIntersection.add(new Integer(eI[i]));
		}
fail();
		
	}
	
	//utilities for arraylist tests

	enum Lists {
		A, B, EMPTY
	}

	private ArrayList<Integer> createBaseArrayList(Lists l) {
		int[] a = { 5, 8, 10, 12, 14, 17, 1, 3, 6, 7, 3, 5, 0, -1, -100, 100, 8, 67, 13, 20, 12, 22, 32, 43, 25 };
		int[] b = { 17, 1, 3, 6, 10, 34, 101, -12, -1, 69, 75, 4, 3, 9, 20, 21, 22, 47, 49, 51 };

		ArrayList<Integer> list = new ArrayList<Integer>();
		if (l == Lists.A) {
			for (int i = 0; i < a.length; i++) {
				list.add(new Integer(a[i]));
			}
		} else if (l == Lists.B) {
			for (int i = 0; i < b.length; i++) {
				list.add(new Integer(b[i]));
			}
		}
		assert (l == Lists.EMPTY);
		return list;
	}

	// Some Primitive Test Cases

	@Test
	public void testPrimitiveNormalCase() {

		int[] a = { 5, 8, 10, 12, 14, 17, 1, 3, 6, 7, 3, 5, 0, -1, -100, 100, 8, 67, 13, 20, 12, 22, 32, 43, 25 };
		int[] b = { 17, 1, 3, 6, 10, 34, 101, -12, -1, 69, 75, 4, 3, 9, 20, 21, 22, 47, 49, 51 };

		int[] unionOutput = new int[a.length + b.length]; // create array with
														  // worst case size
		int[] intersectionOutput = new int[Math.max(a.length, b.length)]; // create
																		  // array
																		  // with
																		  // worst
																		  // case
																		  // size

		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);

		int[] expectedUnion = { -100, -12, -1, 0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 17, 20, 21, 22, 25, 32, 34,
		        43, 47, 49, 51, 67, 69, 75, 100, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] expectedIntersection = { -1, 1, 3, 6, 10, 17, 20, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		utilityCompare(unionOutput, expectedUnion);
		utilityCompare(intersectionOutput, expectedIntersection);

	}

	@Test
	public void testOneElementArrays() {
		int[] a = { 3 };
		int[] b = { 3 };
		int[] unionOutput = new int[2];
		int[] intersectionOutput = new int[1];

		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);

		int[] expectedUnion = { 3 };
		int[] expectedIntersection = { 3 };

		utilityCompare(unionOutput, expectedUnion);
		utilityCompare(intersectionOutput, expectedIntersection);

	}

	@Test
	public void testEmptyArrays() {
		int[] a = new int[0];
		int[] b = new int[0];
		int[] unionOutput = new int[0];
		int[] intersectionOutput = new int[0];

		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);

		int[] expectedUnion = {};
		int[] expectedIntersection = {};

		utilityCompare(unionOutput, expectedUnion);
		utilityCompare(intersectionOutput, expectedIntersection);

	}

	@Test
	public void testPrimitiveDuplicatesAtEnd() {

		int[] a = { 5, 8, 10, 12, 14, 17, 1, 3, 6, 7, 3, 5, 0, -1, -100, 100, 8, 67, 13, 20, 12, 22, 32, 43, 25, 25,
		        25, 25, 25, 25 };
		int[] b = { 17, 1, 3, 6, 10, 34, 101, -12, -1, 69, 75, 4, 3, 9, 20, 21, 22, 47, 49, 51, 51, 51 };

		int[] unionOutput = new int[a.length + b.length]; // create array with
														  // worst case size
		int[] intersectionOutput = new int[Math.max(a.length, b.length)]; // create
																		  // array
																		  // with
																		  // worst
																		  // case
																		  // size

		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);

		int[] expectedUnion = { -100, -12, -1, 0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 17, 20, 21, 22, 25, 32, 34,
		        43, 47, 49, 51, 67, 69, 75, 100, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] expectedIntersection = { -1, 1, 3, 6, 10, 17, 20, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		utilityCompare(unionOutput, expectedUnion);
		utilityCompare(intersectionOutput, expectedIntersection);
	}

	@Test
	public void testPrimitiveDuplicatesAtBeginning() {
		int[] a = { 5, 5, 5, 5, 5, 5, 5, 8, 10, 12, 14, 17, 1, 3, 6, 7, 3, 5, 0, -1, -100, 100, 8, 67, 13, 20, 12, 22,
		        32, 43, 25 };
		int[] b = { 17, 17, 1, 3, 6, 10, 34, 101, -12, -1, 69, 75, 4, 3, 9, 20, 21, 22, 47, 49, 51 };
		int[] unionOutput = new int[a.length + b.length]; // create array with
														  // worst case size
		int[] intersectionOutput = new int[Math.max(a.length, b.length)]; // create
																		  // array
																		  // with
																		  // worst
																		  // case
																		  // size

		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);

		int[] expectedUnion = { -100, -12, -1, 0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 17, 20, 21, 22, 25, 32, 34,
		        43, 47, 49, 51, 67, 69, 75, 100, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] expectedIntersection = { -1, 1, 3, 6, 10, 17, 20, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		utilityCompare(unionOutput, expectedUnion);
		utilityCompare(intersectionOutput, expectedIntersection);
	}

	// used to compare that arrays match and length of arrays match
	private void utilityCompare(int[] a, int[] expectedA) {
		try {
			int i = 0;
			for (; i < expectedA.length; i++) {
				assert (expectedA[i] == a[i]);
			}
			assert (i == a.length);
		} catch (ArrayIndexOutOfBoundsException aie) {
			fail("Array Index Out of Bounds Exception testing union");
		}
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testPrimitiveBadArraySizeUnion() {
		int[] a = new int[10];
		int[] b = new int[10];
		int[] unionOutput = new int[10];
		int[] intersectionOutput = new int[10];

		exception.expect(IllegalArgumentException.class);

		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);
	}

	@Test
	public void testPrimitiveBadArraySizeIntersection() {
		int[] a = new int[10];
		int[] b = new int[10];
		int[] unionOutput = new int[20];
		int[] intersectionOutput = new int[9];

		exception.expect(IllegalArgumentException.class);

		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);
	}

	public void testNullInput() {
		exception.expect(IllegalArgumentException.class);

		int[] a = null;
		int[] b = new int[1];
		int[] unionOutput = new int[10];
		int[] intersectionOutput = new int[10];
		UnionIntersection.findUnionAndIntersection(a, b, unionOutput, intersectionOutput);
	}

}
