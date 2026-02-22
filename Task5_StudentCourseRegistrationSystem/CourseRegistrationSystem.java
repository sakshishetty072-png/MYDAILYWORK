import java.util.*;
// COURSE CLASS 
class Course {
    String code;
    String title;
    String description;
    int capacity;
    String schedule;
    int enrolledStudents = 0;
    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }
    public boolean isAvailable() {
        return enrolledStudents < capacity;
    }
    public void enroll() {
        if (isAvailable()) {
            enrolledStudents++;
        }
    }
    public void drop() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }
    public int slotsLeft() {
        return capacity - enrolledStudents;
    }
}
// STUDENT CLASS 
class Student {
    int id;
    String name;
    ArrayList<Course> registeredCourses = new ArrayList<>();

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public boolean isAlreadyRegistered(Course course) {
        return registeredCourses.contains(course);
    }
}
// MAIN SYSTEM
public class CourseRegistrationSystem {

    static ArrayList<Course> courseList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        // Add courses to database
        courseList.add(new Course("C101", "Java", "Programming in Java", 2, "Mon-Wed"));
        courseList.add(new Course("C102", "Python", "Programming in Python", 3, "Tue-Thu"));
        courseList.add(new Course("C103", "DBMS", "Database Management System", 2, "Fri"));
        // Student Creation
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        Student student = new Student(id, name);
        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println(" STUDENT: " + student.name + " (ID: " + student.id + ")");
            System.out.println("======================================");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. Drop Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    displayCourses();
                    break;
                case 2:
                    registerCourse(student);
                    break;
                case 3:
                    dropCourse(student);
                    break;
                case 4:
                    viewRegistered(student);
                    break;
                case 5:
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println("Invalid Choice! Please try again.");
            }
        } while (choice != 5);
        sc.close();
    }
    //DISPLAY COURSES
    static void displayCourses() {
        System.out.println("\nAVAILABLE COURSES:");
        System.out.println("------------------------------------------------------------");
        for (Course c : courseList) {
            System.out.println("Course Code : " + c.code);
            System.out.println("Title       : " + c.title);
            System.out.println("Description : " + c.description);
            System.out.println("Schedule    : " + c.schedule);
            System.out.println("Slots Left  : " + c.slotsLeft());
            System.out.println("------------------------------------------------------------");
        }
    }
    // REGISTER COURSE 
    static void registerCourse(Student student) {
        System.out.print("Enter Course Code to Register: ");
        String code = sc.next();
        for (Course c : courseList) {
            if (c.code.equalsIgnoreCase(code)) {
                if (student.isAlreadyRegistered(c)) {
                    System.out.println("You are already registered in this course!");
                    return;
                }
                if (c.isAvailable()) {
                    student.registeredCourses.add(c);
                    c.enroll();
                    System.out.println("Course Registered Successfully!");
                } else {
                    System.out.println("Course is Full!");
                }
                return;
            }
        }
        System.out.println("Course Not Found!");
    }
    // DROP COURSE 
    static void dropCourse(Student student) {
        System.out.print("Enter Course Code to Drop: ");
        String code = sc.next();
        Iterator<Course> iterator = student.registeredCourses.iterator();
        while (iterator.hasNext()) {
            Course c = iterator.next();
            if (c.code.equalsIgnoreCase(code)) {
                iterator.remove();
                c.drop();
                System.out.println("Course Dropped Successfully!");
                return;
            }
        }
        System.out.println("You are not registered in this course!");
    }
    // VIEW REGISTERED
    static void viewRegistered(Student student) {
        System.out.println("\nREGISTERED COURSES:");
        System.out.println("----------------------------------");
        if (student.registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            for (Course c : student.registeredCourses) {
                System.out.println(c.code + " - " + c.title + " (" + c.schedule + ")");
            }
        }
    }
}
