

/**
* A class to run tests on the Circuit class and subclasses
* @author Horstman
* @version 02/06/2014
*
*/
public class CircuitDemo1
{ /**
method that implements tests for Circuit class and sublclasses
@param args - Not Used.
*/
public static void main(String[] args)
{
    System.out.println("Test 1");
    Parallel circuit1 = new Parallel();
    circuit1.add(new Resistor(100));
    Serial circuit2 = new Serial();
    circuit2.add(new Resistor(100));
    circuit2.add(new Resistor(200));
    circuit1.add(circuit2);
    System.out.println("Combined resistance: " + circuit1.getResistance());
    System.out.println("Expected: 75.0");
    
    System.out.println("");
    System.out.println("Test 2");

    Parallel circuit3 = new Parallel();
    circuit3.add(new Resistor(100));
    circuit3.add(new Resistor(100));
    System.out.println("Combined resistance: " + circuit3.getResistance());
    System.out.println("Expected: 50.0");
    
    System.out.println("");
    System.out.println("Test 3");

    Serial circuit4 = new Serial();
    circuit4.add(new Resistor(100));
    circuit4.add(new Resistor(200));
    circuit4.add(new Resistor(300));
    System.out.println("Combined resistance: " + circuit4.getResistance());
    System.out.println("Expected: 600.0");


}
}
