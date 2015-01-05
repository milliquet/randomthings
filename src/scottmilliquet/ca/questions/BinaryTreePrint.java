package scottmilliquet.ca.questions;

/**
 * @author scottmilliquet
 * 
 */

import java.util.ArrayList;

public class BinaryTreePrint {


	private BinaryTreePrint() {

	}

	public static ArrayList<Node> getRange(Node node, int min, int max) {

		if ( min > max || node == null) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<Node> list = new ArrayList<Node>();
		
		//visit the left children 
		if ( node.leftChild != null && node.leftChild.value >= min ){ 
			list.addAll(getRange(node.leftChild, min, max));
		}
		
		//visit yourself
		if ( node.value >= min && node.value <= max ){
			list.add(node);
		}
		
	    //visit the right children
		if ( node.rightChild != null && node.rightChild.value <= max) {
			list.addAll(getRange(node.rightChild, min, max));
		}

		return list;
	}

}
