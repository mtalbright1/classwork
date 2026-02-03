import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CapacitorVoltage {

	public static void main (String[] args) {
		
		Scanner console = new Scanner(System.in);
		System.out.println ("Please enter file name (params.txt):");
		String fileName = console.nextLine();
		File inputFile = new File(fileName);
		
		double[] values = new double[5];
		double B, R, C, start, end;
		
		try (Scanner in = new Scanner(inputFile); PrintWriter out = new PrintWriter("rc.txt")) {
			
			for(int i = 0; i < 5; i++) {
				if(!in.hasNextDouble()) throw new BadDataException("Number Expected!");
				values[i] = in.nextDouble();
			}
			
			if(in.hasNext()) throw new BadDataException("End of Expected!");	
			
			B = values[0];
			R = values[1];
			C = values[2];
			start = values[3];
			end = values[4];
			double e = Math.E;
			
			for (double i = start; i <= end; i += (end - start) / 100) {
				double V = B * (1 - Math.pow(e, -i/(R * C)));
				out.printf("Voltage at %.2f microseconds: %.5f\n", i, V);
			}
		}

		catch (FileNotFoundException exception)
        {
           System.out.println("File not found.");
        }
        catch (BadDataException exception)
        {
           System.out.println("Bad data: " + exception.getMessage());
        }
        catch (IOException exception)
        {
           exception.printStackTrace();
        }
		
		console.close();
		
	}
}