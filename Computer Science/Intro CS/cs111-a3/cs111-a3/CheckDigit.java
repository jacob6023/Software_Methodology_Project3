/*************************************************************************
 *  Compilation:  javac CheckDigit.java
 *  Execution:    java CheckDigit 020131452
 *
 *  @author:
 *
 *  Takes a 12 or 13 digit integer as a command line argument, then computes
 *  and displays the check digit
 *
 *  java CheckDigit 048231312622
 *  0
 *
 *  java CheckDigit 9780470458310
 *  0
 * 
 *  java CheckDigit 9780470454310
 *  8
 * 
 *  Print only the check digit character, nothing else.
 *
 *************************************************************************/

public class CheckDigit {

    public static void main (String[] args) {

        // WRITE YOUR CODE HERE
        long n;
        n = Long.parseLong(args[0]);
        long sum1 = 0;
        long sum2 = 0;
        long d;

        for ( int i = 0; i < 13; i++){
            d = n % 10;
            n = n / 10;
            sum1 = sum1 + d;
            d = n % 10;
            n = n / 10;
            sum2 = sum2 + d;
        }
        long m1;
        long m2;
        m1= sum1 % 10;
        m2= sum2 % 10;
        m2 = m2 * 3;
        m2 = m2 % 10;
        long f = m1 + m2;
        f = f % 10;
        System.out.println(f);

    }
}