package scottmilliquet.ca.questions;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


/**
 * @author scottmilliquet
 *
 */
public class BinaryTreePrintTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFullTraversal() {
		ArrayList<Node> list = BinaryTreePrint.getRange(createTree( Trees.FULL ), Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		ArrayList<Node> expectedResult = new ArrayList<Node>();
		for ( int i = 1; i <= TOTAL_ELEMENTS; i++) {
			expectedResult.add(new Node( i ));	
		}
		
		assertEqualArrayListNodes(list, expectedResult);
	}
	
	@Test
	public void testLeftOnlyTree() {
		ArrayList<Node> list = BinaryTreePrint.getRange(createTree( Trees.LEFT_ONLY ), Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		ArrayList<Node> expectedResult = new ArrayList<Node>();
		for ( int i = 1; i <= LEFT_ONLY_ELEMENTS; i++) {
			expectedResult.add(new Node( i ));	
		}

		assertEqualArrayListNodes(list, expectedResult);

	}
	
	@Test
	public void testRightOnlyTree() {
		ArrayList<Node> list = BinaryTreePrint.getRange(createTree( Trees.RIGHT_ONLY ), Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		ArrayList<Node> expectedResult = new ArrayList<Node>();
		int value = 5;
		for ( int i = 1; i <= RIGHT_ONLY_ELEMENTS; i++) {
			expectedResult.add(new Node( value++ ));	
		}

		assertEqualArrayListNodes(list, expectedResult);

	}
	
	@Test
	public void testLeftResultFullTree() {
		ArrayList<Node> list = BinaryTreePrint.getRange(createTree( Trees.FULL ), 1, 4);
		
		ArrayList<Node> expectedResult = new ArrayList<Node>();
		for ( int i = 1; i <= 4; i++) {
			expectedResult.add(new Node( i ));
		}
		
		assertEqualArrayListNodes(list, expectedResult);
	}
	
	@Test
	public void testRightResultFullTree() {
		ArrayList<Node> list = BinaryTreePrint.getRange(createTree( Trees.FULL ), 6, 9);
		
		ArrayList<Node> expectedResult = new ArrayList<Node>();
		for ( int i = 6; i <= 9; i++) {
			expectedResult.add(new Node( i ));
		}
		
		assertEqualArrayListNodes(list, expectedResult);
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testNullTree() {
		exception.expect(IllegalArgumentException.class);
		
		BinaryTreePrint.getRange(null, 0, 1);
	}
	
	@Test
	public void testReversedRange() {
		exception.expect(IllegalArgumentException.class);
		
		BinaryTreePrint.getRange(createTree(Trees.SMALL), 1, 0);
	}
	
	
	//utility function to compare array lists
	private void assertEqualArrayListNodes(ArrayList<Node> list, ArrayList<Node> expectedResult) {
		assert(expectedResult.size() == list.size());
		Iterator<Node> iter = list.iterator(), expIter = expectedResult.iterator();
		while(iter.hasNext() && expIter.hasNext()) {
			assertEquals(iter.next().value,expIter.next().value);
		}
	}

	private enum Trees {
		FULL, 
		SINGLE_NODE,
		LEFT_ONLY,
		RIGHT_ONLY,
		SMALL
	}

	private static final int TOTAL_ELEMENTS = 9;
	private static final int RIGHT_ONLY_ELEMENTS = 5;
	private static final int LEFT_ONLY_ELEMENTS = 5;
	
	private Node createTree( Trees tree ) {
		Node n1 = new Node( 1 );
		Node n2 = new Node( 2 );
		Node n3 = new Node( 3 );
		Node n4 = new Node( 4 );
		Node n5 = new Node( 5 );
		Node n6 = new Node( 6 );
		Node n7 = new Node( 7 );
		Node n8 = new Node( 8 );
		Node n9 = new Node( 9 );

		switch (tree) {
		case FULL: 
			n5.leftChild = n4;
			n4.leftChild = n2;
			n2.leftChild = n1;
			n2.rightChild = n3;
			n5.rightChild = n7;
			n7.leftChild = n6;
			n7.rightChild = n8;
			n8.rightChild = n9;
			break;
		case SINGLE_NODE: //no connections
			break;
		
		case RIGHT_ONLY: // only right children
			n5.rightChild=n6;
			n6.rightChild=n7;
			n7.rightChild=n8;
			n8.rightChild=n9;
			break;
		case LEFT_ONLY: //only left children
			n5.leftChild = n4;
			n4.leftChild = n3;
			n3.leftChild = n2;
			n2.leftChild = n1;
			break;
		case SMALL: 
		default:
			n5.leftChild = n4;
			n5.rightChild = n6;
		}
		
		return n5;
	}
}
