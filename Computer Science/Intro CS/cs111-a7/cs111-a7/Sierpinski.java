/*************************************************************************
 *  Compilation:  javac Sierpinski.java
 *  Execution:    java Sierpinski
 *
 *  @author:
 *
 *************************************************************************/

public class Sierpinski {

    // Height of an equilateral triangle whose sides are of the specified length. 
    public static double height(double length) {
        double height;
        height = (Math.sqrt(3)/2) * length;
        return height;
    }

    // Draws a filled equilateral triangle whose bottom vertex is (x, y) 
    // of the specified side length. 
    public static void filledTriangle(double x, double y, double length) {
        
        double height = height(length);
        double M = length / 2;
        double x1 = x - M ;
        double x2 = x + M;
        double y1 = y + height;
        double y2 = y + height;
        double [] nx = new double [3];
        nx[0] = x;
        nx[1] = x1;
        nx[2] = x2;
        double [] ny = new double [3];
        ny[0] = y;
        ny[1] = y1;
        ny[2] = y2;
        StdDraw.filledPolygon(nx,ny);

    }

    // Draws a Sierpinski triangle of order n, such that the largest filled 
    // triangle has bottom vertex (x, y) and sides of the specified length. 
    public static void sierpinski(int n, double x, double y, double length) {
        if (n==0){
            return;
        }
        else { 
        filledTriangle(x,y,length);

        sierpinski(n-1, x-length/2, y, length/2);
        sierpinski(n-1, x+length/2, y, length/2);
        sierpinski(n-1, x, y+(Math.sqrt(3)/2)*length, length/2);
            
        }

	// WRITE YOUR CODE HERE
    }

    // Takes an integer command-line argument n; 
    // draws the outline of an equilateral triangle (pointed upwards) of length 1; 
    // whose bottom-left vertex is (0, 0) and bottom-right vertex is (1, 0); and 
    // draws a Sierpinski triangle of order n that fits snugly inside the outline. 
    public static void main(String[] args) {
        
        

        double height = height(1);

        double [] xm = new double [3];
        xm[0]= 0;
        xm[1]= 1;
        xm[2]= 0.5;

        double [] ym = new double [3];
        ym[0]= 0;
        ym[1]= 0;
        ym[2]= height;
        
        StdDraw.polygon(xm,ym);

        int n = Integer.parseInt(args[0]);
        
        sierpinski(n,0.5,0.0,0.5);
        
        
    }
}
