/**
 * The GradeManagementSystemTest class is a test driver for the grade management system.
 * It initializes data for a student, including courses and grades, and generates a term report
 * for that student, demonstrating the functionality of the system.
 */
public class GradeManagementSystemTest {
	 /**
     * The main method serves as the entry point for the Grade Management System test.
     * It creates instances of Grade, Course, Student, and TermReport to simulate 
     * the process of managing and displaying a student's term report.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
    	 // Create Grade instances for different grades
        Grade gradeA = new Grade("A");
        Grade gradeB = new Grade("B");
        Grade gradeC = new Grade("C");
        Grade gradeD = new Grade("D");
        Grade gradeF = new Grade("F");
       
        // Create Course instances for various subjects with associated grades
        Course cpsc2100 = new Course("CPSC2100", "Software Design and Development", 3, gradeA);
        Course cpsc1110 = new Course("CPSC1110", "Data Structures and Program Design", 4, gradeA);
        Course cpsc2800 = new Course("CPSC2800", "Introduction to Operating Systems", 3, gradeB);
        Course cpsc4101 = new Course("MATH2030", "Discrete Math", 3, gradeC);
       
        // Create a Student instance and add courses to it
        Student student1 = new Student("SYZ321", "John Doe");
        student1.addCourse(cpsc2100);
        student1.addCourse(cpsc1110);
        student1.addCourse(cpsc2800);
        student1.addCourse(cpsc4101);
       
        // Create a TermReport instance for a specific term and add the student
        TermReport report = new TermReport("Fall 2024");
        report.addStudent(student1);
        
        // Generate and display the term report for the student
        report.generateReport();
    }
}