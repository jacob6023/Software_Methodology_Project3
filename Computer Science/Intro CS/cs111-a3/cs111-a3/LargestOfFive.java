/*************************************************************************
 *  Compilation:  javac LargestOfFive.java
 *  Execution:    java LargestOfFive 35 10 32 1 8
 *
 *  @author:
 *
 *  Takes five distinct integers as command-line arguments and prints the 
 *  largest value
 *
 *
 *  % java LargestOfFive 35 10 32 1 8
 *  35
 *
 *  Assume the inputs are 5 distinct integers.
 *  Print only the largest value, nothing else.
 *
 *************************************************************************/

public class LargestOfFive {

    public static void main (String[] args) {
        int a;
        int b;
        int c;
        int d;
        int e;

        a = Integer.parseInt(args[0]);
        b = Integer.parseInt(args[1]);
        c = Integer.parseInt(args[2]);
        d = Integer.parseInt(args[3]);
        e = Integer.parseInt(args[4]);

        int n = a;

        if (b > n){
            n = b;
        }

        if (c > n){
            n = c;
        }

        if (d > n) {
            n = d;
        }

        if (e > n) {
            n = e;
        }

        System.out.println(n);
        
    }
}