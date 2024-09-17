/*************************************************************************
 *  Compilation:  javac RecursiveAppend.java
 *  Execution:    java RecursiveAppend
 *
 *  @author:
 *
 *************************************************************************/

public class RecursiveAppend {

    // Returns the original string appended to the original string n times 
    public static String appendNTimes (String original, int n) {
	if (n<1){
        return original;
    }else {
        return original+ appendNTimes(original, n-1);
    }
    }

    public static void main (String[] args) {
        String s = "";
        s = appendNTimes("cat", 1);
        System.out.println(s);
	

    }
}
