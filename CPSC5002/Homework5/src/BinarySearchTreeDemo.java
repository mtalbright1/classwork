
public class BinarySearchTreeDemo {

	public static void main(String[] args) {
		BinarySearchTree BST = new BinarySearchTree();
		
		BST.insert(5);
		BST.insert(3);
		BST.insert(8);
		BST.insert(7);
		BST.insert(1);
		BST.insert(9);
		BST.insert(6);

		BST.inOrderTraversal();		// prints elements in order
		
		System.out.println("Is there a 3?: " + BST.search(3));		// expected true
		System.out.println("Is there a 2?: " + BST.search(2));		// expected false
		
		BST.delete(6);		// has no children
		BST.delete(3);		// has one child
		BST.delete(8);		// has two children
		
		BST.inOrderTraversal();		// print again after deletion
	}
}
