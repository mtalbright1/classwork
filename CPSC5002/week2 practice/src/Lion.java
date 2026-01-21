
public class Lion extends Animal {

	Lion(String name) {
		super(name, "Lion");
	}
	
	@Override
	void makeSound() {
		System.out.println("Roar!");
	}
}
