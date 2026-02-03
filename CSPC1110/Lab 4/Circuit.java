import java.util.List;
import java.util.ArrayList;

public class Circuit {

   public double getResistance () {                          // just returns 0 resistance
      double resistance = 0;
      return resistance;
   }
}

class Resistor extends Circuit {

   private double resistance;                               // creates a double called resistance

   public Resistor (double resistance) {                    // takes a double ands sets this instance of resistance to it
      this.resistance = resistance;
   }

   public double getResistance() {                          // overrides and returns a this instance of resistance as a double
      return this.resistance;
   }
}

class Serial extends Circuit {

   private List<Resistor> resistors = new ArrayList<>();    // creates an array list of Resistor objects called resistors
   
   public void add (Resistor resistor) {                    // adds a Resistor called resistor to the resistors list
      resistors.add (resistor);
   }
   
   public double getResistance() {                          // overrides getResistance with the serial formula       
      double totalResistance = 0.0;
      for (Resistor i : resistors) {
         totalResistance += i.getResistance();              // goes through each object in the resistors list and uses that object's getResistance method
      }
      return totalResistance;
   }
}

class Parallel extends Circuit {

   private List<Object> components = new ArrayList<>();    // creates an array list of Resistor objects called resistors
   
   public void add (Object component) {                    // adds a Resistor called resistor to the resistors list
      components.add (component);
   }

   public double getResistance() {                          // overrides
      double totalResistance = 0.0;
      for (Object i : components) {
    	  totalResistance += 1.0 / ((Circuit) i).getResistance();   //for each Resistor i in resistors, uses its get.Resistance method
      }
      return (1.0 / totalResistance);
   }
}
