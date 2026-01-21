
public class Animal {

	String name, species;
	
	Animal (String name, String species) {
		this.name = name;
		this.species = species;
	}
	
	void makeSound() {
		System.out.println("Rawr xD");
	}
	
	void displayInfo() {
		System.out.println("Name: " + this.name);
		System.out.println("Species: " + this.species);
	}

	public static void main (String[] args) {
	
		Animal[] zoo = new Animal[3];
	
		zoo[0] = new Lion("Simba");
		zoo[1] = new Elephant("Dumbo");
		zoo[2] = new Parrot("Iago");
		
		for (Animal i : zoo) {
			i.makeSound();
			i.displayInfo();
			System.out.println();
		}
	}
}
