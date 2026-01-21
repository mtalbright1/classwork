
public class Parrot extends Animal {

	Parrot (String name) {
		super(name, "Parrot");
	}
	
	@Override
	void makeSound () {
		System.out.println("parrot noise");
	}
}
