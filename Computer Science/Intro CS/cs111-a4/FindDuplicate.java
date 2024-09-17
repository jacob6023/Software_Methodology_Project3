/*************************************************************************
 *  Compilation:  javac FindDuplicate.java
 *  Execution:    java FindDuplicate
 *
 *  @author:
 *
 * FindDuplicate that reads n integer arguments from the command line 
 * into an integer array of length n, where each value is between is 1 and n, 
 * and displays true if there are any duplicate values, false otherwise.
 *
 *  % java FindDuplicate 10 8 5 4 1 3 6 7 9
 *  false
 *
 *  % java FindDuplicate 4 5 2 1 2
 *  true
 *************************************************************************/

public class FindDuplicate {

    public static void main(String[] args) {
		boolean a;
		a = false;
		int n = args.length;
		int [] nums = new int[n];
		for (int i=0; i<n; i++) nums[i] = Integer.parseInt(args[i]);
		for (int i=0; i< nums.length; i++){
			for (int j = i + 1; j< nums.length; j++){
				if (nums[i] == nums[j]){
					a = true;
				}	
			}
		}
		System.out.println(a);
		
		
	}
}
