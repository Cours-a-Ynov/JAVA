package Triangle;

public class TriangleMain {
    public static void main(String[] args) {
        Triangle t1 = new Triangle(5, 4, 2);
        t1.display();
        Triangle t2 = new Triangle(3, 3, 3);
        t2.display();
        System.out.println("Angles for T1 are " + (t1.anglesAreCorrect() ? "correct." : "incorrect"));
        System.out.println("Angles for T2 are " + (t2.anglesAreCorrect() ? "correct." : "incorrect"));
    }
}
