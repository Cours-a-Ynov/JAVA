package Triangle;

import java.lang.Math;

public class Triangle {
    double a = 0;
    double b = 0;
    double c = 0;
    private double alpha = 0;
    private double beta = 0;
    private double gamma = 0;

    public Triangle(double a, double b, double c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getPerimeter(){
        return this.a + this.b + this.c;
    }

    public void computeAngles(){
        double asqrd = this.a * this.a;
        double bsqrd = this.b * this.b;
        double csqrd = this.c * this.c;
        this.alpha = Math.acos((bsqrd + csqrd - asqrd)/(2*b*c) );
        this.beta = Math.acos((asqrd + csqrd - bsqrd)/(2*a*c) );
        this.gamma = Math.acos((asqrd + bsqrd - csqrd)/(2*a*b) );
    }

    public double getArea(){
        double p = (a + b + c)/2.0;
        double area = Math.sqrt(p*(p - a)*(p-b)*(p-c));
        return area;
    }

    public void display() {
        this.computeAngles();
        System.out.println("Propriétés du triangle : ");
        System.out.println("Cotes : a = " + a + ", b = " + b + ", c = " + c);
        System.out.println("Perimetre: " + getPerimeter());
        System.out.println("Angles : alpha = " + alpha + ", beta = " + beta + ", gamma = " + gamma);
        System.out.println("Aire : " + getArea());
    }

    public boolean anglesAreCorrect(){
        return (alpha + beta + gamma - Math.PI < 0.001);
    }
}
