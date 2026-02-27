
public class ListMaker {

	public static void main(String[] args) {
		SinglyLinkedList list = new SinglyLinkedList();
		
		list.insertAtBeginning(2);
		list.insertAtBeginning(1);
		
		list.insertAtEnd(3);
		list.insertAtEnd(4);
		
		System.out.println("Is there a 3?: " + list.search(3));
		System.out.println("Is there a 5?: " + list.search(5));
		
		System.out.println("List before deleting 3:");
		list.display();		// expected: 1 -> 2 -> 3 -> 4
		list.deleteByValue(3);
		System.out.println("List after deleting 3:");
		list.display();		// expected: 1 -> 2 -> 4
	}
}
