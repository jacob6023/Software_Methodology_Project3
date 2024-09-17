/*************************************************************************
 *  Compilation:  javac PolygonTransform.java
    *  Execution:    java PolygonTransform
 *
 *  @author:
 *
 *************************************************************************/

public class PolygonTransform {


    // Returns a new array that is an exact copy of the given array. 
    // The given array is not mutated. 
    public static double[] copy(double[] array) {
    double copy[] = new double[array.length];
    for (int i = 0; i < array.length; i++){
        copy[i]= array[i];
    }
    return copy;
	
    }
    
    // Scales the given polygon by the factor alpha. 
    public static void scale(double[] x, double[] y, double alpha) {


        for (int i=0; i< x.length; i++){
            x[i] = x[i] * alpha;
        }
        for (int i=0; i< y.length; i++){
            y[i] = y[i] * alpha;
        }


	// WRITE YOUR CODE HERE
    }
    
    // Translates the given polygon by (dx, dy). 
    public static void translate(double[] x, double[] y, double dx, double dy) {
      
        for (int i=0; i< x.length; i++){
            x[i] = x[i] +dx;
        }
        for (int i=0; i< y.length; i++){
            y[i] = y[i] + dy;
        }
	// WRITE YOUR CODE HERE
    }
    
    // Rotates the given polygon theta degrees counterclockwise, about the origin. 
    public static void rotate(double[] x, double[] y, double theta) {
        double a[] = new double[x.length];
        for (int i = 0; i < x.length; i++){
            a[i]= x[i];
        }
        double b[] = new double[y.length];
        for (int i = 0; i < y.length; i++){
            b[i]= y[i];
        }
        
        double t = theta * (Math.PI / 180);

        for (int i=0; i< x.length; i++){
            x[i] = a[i] * Math.cos(t) - b[i] * Math.sin(t);
            y[i] = b[i] * Math.cos(t) + a[i] * Math.sin(t);
        }
	// WRITE YOUR CODE HERE
    }

    // Tests each of the API methods by directly calling them. 
    public static void main(String[] args) {
        StdDraw.setScale(-5.0, +5.0); 
        double[] x = { 0, 1, 1, 0 }; 
        double[] y = { 0, 0, 2, 1 }; 
        double theta = -30.0; 
        StdDraw.setPenColor(StdDraw.RED); 
        StdDraw.polygon(x, y); 
        rotate(x, y, theta); 
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.polygon(x, y);
	// WRITE YOUR CODE HERE
    }
}
