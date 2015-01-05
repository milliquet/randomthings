package scottmilliquet.ca.questions;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author scottmilliquet
 * 
 */
public class UnionIntersection {

	/**
	 * finds the union and intersection of a and b
	 * Big O of N + M solution where a is size N and b is size M 
	 * @param a - non destructive, contents will not be modified
	 * @param b - non destructive, contents will not be modified
	 * @param union - output parameter
	 * @param intersection - output parameter
	 */
	public static void findUnionAndIntersection(ArrayList<Integer> a, ArrayList<Integer> b, final ArrayList<Integer> union, final ArrayList<Integer> intersection) {
	
		if ( a == null || b == null || union == null || intersection == null ) {
			throw new IllegalArgumentException("null input parameter");
		}
		
		HashMap<Integer, Integer> hashMapA = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> hashMapB = new HashMap<Integer, Integer>();
		
		//put all items from A into a hashmap,  this will remove duplicates from A
		Iterator<Integer> iterA = a.iterator();
		while(iterA.hasNext()) {
			Integer next = iterA.next();
			if (!hashMapA.containsKey(next)) { //not a dupe
				hashMapA.put(next, next);
				union.add(next);
			}
		}
		Iterator<Integer> iterB = b.iterator();
		while(iterB.hasNext()){
			Integer next = iterB.next();
			if(!hashMapB.containsKey(next)) { //not a dupe
				hashMapB.put(next,next);
				if( hashMapA.containsKey(next)) { //exists in A so add to intersection
					intersection.add(next);
				} else {
					union.add(next);
				}
			}
		}
	
	}

	
	/**
	 * Non ideal solution -java.util.HashMap does not work with primitives and in order to avoid wrapper object creation 
	 * this code sorts the list instead of putting into a hashmap. 
	 * 
	 * Non destructive find of union and intersection, O(n log n + m log m) solution
	 * much less object creation but wasted array space as well as space and work to copy array
	 * 
	 * @param a
	 *            input data - will not be modified
	 * @param b
	 *            input data - will not be modified
	 * @param union
	 *            must be minimum size (a.length + b.length) to handle worst case
	 * @param intersection
	 *            must be at least as large as max (a.length, b.length)
	 */
	public static void findUnionAndIntersection(int[] a, int[] b, final int[] union, final int[] intersection) {
		
		if ( a == null || b == null || union == null || intersection == null ) {
			throw new IllegalArgumentException("null input parameter");
		}
		if ( a.length + b.length > union.length ) {
			throw new IllegalArgumentException("union too small");
		}
		if ( Math.max(a.length, b.length) > intersection.length ) {
			throw new IllegalArgumentException("intersection too small");
		}
		
		
		//in order to not change the original input arrays we need to copy a, b.
		int[] sortedA = Arrays.copyOf(a, a.length);
		Arrays.sort(sortedA);  //sorts array min to max 
		int[] sortedB = Arrays.copyOf(b, b.length);
		Arrays.sort(sortedB); 
		
		int aPos = 0, bPos = 0; //track position in input arrays
		int intPos = 0, unionPos = 0; // track position in output arrays
		
		while (aPos < sortedA.length && bPos < sortedB.length) {
			
			//check for duplicates
			if (aPos > 0 && sortedA[aPos - 1] == sortedA[aPos]) { 
				aPos++;
				continue;
			}
			if (bPos > 0 && sortedB[bPos - 1] == sortedB[bPos]){
				bPos++;
				continue;
			}
			
			if ( sortedA[aPos] == sortedB[bPos]) { //intersection case add to intersection set and union set
				intersection[intPos++] = sortedA[aPos];
				union[unionPos++] = sortedA[aPos];
				//move both arrays
				aPos++; 
				bPos++;
			} else if (sortedA[aPos] < sortedB[bPos]) { //case of adding A value to union and increment
				union[unionPos++] = sortedA[aPos++];
			} else { 								    //case of adding B value to union and increment
				assert(sortedA[aPos] > sortedB[bPos]); 
				union[unionPos++] = sortedB[bPos++];
			}
		}
		
		//one array has now been exhausted.  append the rest of the remaining array to union output being aware of duplicates
		if (aPos == 0 && sortedA.length > 0){
			union[unionPos++] = sortedA[aPos++];
		}
		while (aPos < sortedA.length ){
			if (sortedA[aPos - 1 ] == sortedA[aPos]){
				aPos++;
			} else {
				union[unionPos++] = sortedA[aPos++];
			}
		}
		
		if (bPos == 0 && sortedB.length > 0){
			union[unionPos++] = sortedB[bPos++];
		}
		while (bPos < sortedB.length) {
			if (sortedB[bPos - 1 ] == sortedB[bPos]){
				bPos++;
			} else {
				union[unionPos++] = sortedB[bPos++];
			}
		}
	}
}

