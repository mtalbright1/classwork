
public class Country implements Comparable<Country> {		// Comparable is a built in interface with a single compareTo method

	private String name;
	private int population;
	private double area;
	
	public Country(String name, int population, double area) {
		this.name = name;
		this.population = population;
		this.area = area;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public double getArea() {
		return area;
	}
	
	public double getPopulationDensity() {
		return population / area;
	}
	
	@Override
	public String toString() {		// Population density is rounded to two decimal places here. %s for string, %d for int, %f for double
		return String.format("%s: Population = %d, Area = %.2fkm², Density = %.2f people/km²", name, population, area, getPopulationDensity());
	}
	
	@Override
	public int compareTo(Country other) {		// will be used by Collections.sort() in the other class
		return Double.compare(other.getPopulationDensity(), this.getPopulationDensity());		// 'other' before 'this' means higher to lower
	}
}
