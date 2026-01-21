
public class Elephant extends Animal {

	Elephant (String name) {
		super(name, "Elephant");
	}
	
	@Override
	void makeSound() {
		System.out.println("elephant noise");
	}
}
