import java.io.*;
import java.util.*;

public class CountryPopulationDensity {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		File data = null;
		Map<String, List<Country>> continentMap = new HashMap<>();
		
		while (data == null) {		// repeatedly prompts the user for the correct input file name
			System.out.println("Please enter file name (data.txt): ");
			data = new File(input.nextLine());
			if (data.isFile()) {		// checks if the given file name exists and is a file (not directory)
				System.out.println("File found.");
				input.close();
				break;
			}
			else {
				System.out.println("File not found.");
				data = null;		// resets the file name for data and prompts the user again
			}
		}
		
		try {
			Scanner fileScanner = new Scanner(data);
			
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] tokens = line.split("\t");		// splits the line into a String array based on tabs
				
				String name = tokens[0];
				int population = Integer.parseInt(tokens[1]);
				double area = Double.parseDouble(tokens[2]);
				String continent = tokens[3];
				
				Country country = new Country(name, population, area);
				continentMap.computeIfAbsent(continent, k -> new ArrayList<>()).add(country);		// If continent doesn't yet exist, make it and add the country. Otherwise, add the country.
			}
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Could not read file");
		}
		
		for (List<Country> i : continentMap.values()) {		// continentMap's values are the country lists
			Collections.sort(i);		// uses each Country's compareTo method
		}
		
		for (Map.Entry<String, List<Country>> i : continentMap.entrySet()) {		// for each entry in the map...
			try {
				File output = new File(i.getKey() + ".txt");		// make a text file with the continent for the name
				System.out.println("Created file " + output);
				PrintWriter writer = new PrintWriter(output);
				
				for (Country j : i.getValue()) {		// for each Country in this continent's Country list...
					writer.println(j.toString());		// write its toString result to a new line
				}
				
				writer.close();		// must close to actually write
				
			} catch (Exception e) {
				System.out.println("Could not write file");
			}
		}
		System.out.println("Completed");
	}
}