
public class BinarySearchTree {

	private TreeNode root;
	
	public BinarySearchTree() {
		root = null;
	}
	
	public void insert(int data) {
		root = insertRecursion(root, data);		// just pass to recursive method along with the root
	}
	
	private TreeNode insertRecursion(TreeNode currentNode, int data) {
		if (currentNode == null) {		// base case
			currentNode = new TreeNode(data);
			return currentNode;
		}
		
		if (data < currentNode.data) {
			currentNode.left = insertRecursion(currentNode.left, data);
		}
		else {
			currentNode.right = insertRecursion(currentNode.right, data);
		}
		
		return currentNode;
	}
	
	public boolean search(int data) {
		return searchRecursion(root, data);
	}
	
	private boolean searchRecursion(TreeNode currentNode, int target) {
		if (currentNode == null) {
			return false;		// base case, failed to find
		}
		
		if (currentNode.data == target) {
			return true;		// other base case, found
		}
		
		if (target < currentNode.data) {
			return searchRecursion(currentNode.left, target);
		}
		else {
			return searchRecursion(currentNode.right, target);
		}
	}
	
	public void delete(int data) {
		root = deleteRecursion(root, data);
	}
	
	private TreeNode deleteRecursion(TreeNode currentNode, int target) {
		if (currentNode == null) {
			return null;		// base case: failed to find target
		}
		
		if (target < currentNode.data) {		// target is lesser, move to the left
			currentNode.left = deleteRecursion(currentNode.left, target);
		}
		else if (target > currentNode.data) {		// target is greater, move to the right
			currentNode.right = deleteRecursion(currentNode.right, target);
		}
		else {			// target is equal, target found
			if (currentNode.left == null) {
				return currentNode.right;		// if without left child, return right child as this node's replacement. Will return null if no right either
			}
			if (currentNode.right == null) {
				return currentNode.left;		// if without right child, return left child as this node's replacement
			}
			
			// two children, node must be replaced with smallest from the right subtree
			
			currentNode.data = findSmallest(currentNode.right);		// currentNode becomes the smallest of its right subtree
			currentNode.right = deleteRecursion(currentNode.right, currentNode.data);		// delete smallest of right subtree node
		}
		return currentNode;
	}
	
	private int findSmallest(TreeNode currentNode) {		// helper to find the smallest child in a subtree
		int smallestChild = currentNode.data;
		while (currentNode.left != null) {
			smallestChild = currentNode.left.data;
			currentNode = currentNode.left;
		}
		return smallestChild;
	}
	
	public void inOrderTraversal() {		// prints elements in ascending order
		inOrderTraversalRecursion(root);
		System.out.println();
	}
	
	private void inOrderTraversalRecursion(TreeNode currentNode) {
		if (currentNode == null) {		// base case, nothing here
			return;
		}
		
		inOrderTraversalRecursion(currentNode.left);		// go to the left and handle that first
		
		System.out.print(currentNode.data + " "); 		// print this node's data
		
		inOrderTraversalRecursion(currentNode.right);		// now go to the right and handle that
	}
	
}
