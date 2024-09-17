/*************************************************************************
 *  Compilation:  javac OrderCheck.java
 *  Execution:    java OrderCheck 5 10 15 2
 *
 *  @author:
 *
 *  Prints true if the four integer inputs are in strictly ascending
 *  or descending order, false otherwise
 *
 *  % java OrderCheck 5 10 15 2
 *  false
 *
 *  % java OrderCheck 15 11 9 4
 *  true
 *
 *************************************************************************/

public class OrderCheck {

    public static void main(String[] args) {

	// WRITE YOUR CODE HERE
    int w;
    int x;
    int y;
    int z;
    //get user input
    w = Integer.parseInt(args[0]);
    x = Integer.parseInt(args[1]);
    y = Integer.parseInt(args[2]);
    z = Integer.parseInt(args[3]);
    //establish boolean

    boolean o = ( w > x && x > y && y > z )||( w < x && x < y && y < z )? true : false;
    System.out.println(o);
    }
}
