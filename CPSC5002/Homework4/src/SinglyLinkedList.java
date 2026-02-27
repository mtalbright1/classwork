
public class SinglyLinkedList {

	private Node head;		// this first node in the list
	
	public SinglyLinkedList() {
		this.head = null;		// starts out as null
	}
	
	public void insertAtBeginning(int data) {
		Node newNode = new Node(data);
		
		if(head == null) {		// if there isn't yet a head, place the new node as the head
			head = newNode;
			return;
		}
		
		newNode.next = head;
		head = newNode;
	}
	
	public void insertAtEnd(int data) {
		Node newNode = new Node(data);
		
		if(head == null) {		// if there isn't yet a head, place the new node as the head
			head = newNode;
			return;
		}
		
		Node nextNode = head;
		while(nextNode.next != null) {		// as long as this node has a next, change it to its next
			nextNode = nextNode.next;
		}									// now we're at the tail
		
		nextNode.next = newNode;		// make the tail's next be the new node
	}
	
	public void deleteByValue(int data) {
		if(head == null) {
			return;			// can't delete anything if there is no head yet
		}
		
		if(head.data == data) {		// if the head is the target
			head = head.next;
			return;
		}
		
		Node nextNode = head;		// start at the head
		while(nextNode.next != null && nextNode.next.data != data) {		// move to the next while there is a next and the next isn't the target
			nextNode = nextNode.next;
		}															// we're now either at the end or the next is the target
		
		if(nextNode.next == null) {
			return;				// we're at the end, return without deleting
		}
		
		nextNode.next = nextNode.next.next;			// skip over the target
	}
	
	public boolean search(int data) {
		if(head == null) {
			return false;			// there's no head yet, return false
		}
		
		Node nextNode = head;		// start at the head
		while(nextNode.next != null && nextNode.next.data != data) {		// move to the next while there is a next and the next isn't the target
			nextNode = nextNode.next;
		}															// we're now either at the end or the next is the target
		
		if(nextNode.next == null) {
			return false;				// we're at the end and didn't find the target, return false
		}
		
		return true;		// nextNode.next must be the target
	}
	
	public void display() {
		Node nextNode = head;		// start at the head
		while(nextNode.next != null) {
			System.out.print(nextNode.data + " -> ");
			nextNode = nextNode.next;
		}
		
		System.out.print(nextNode.data);		// print the tail
		System.out.println();		// end with a next line
	}
}
