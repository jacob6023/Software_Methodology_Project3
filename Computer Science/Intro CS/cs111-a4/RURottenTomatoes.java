/*************************************************************************
 *  Compilation:  javac RURottenTomatoes.java
 *  Execution:    java RURottenTomatoes
 *
 *  @author:
 *
 * RURottenTomatoes creates a 2 dimensional array of movie ratings 
 * from the command line arguments and displays the index of the movie 
 * that has the highest sum of ratings.
 *
 *  java RURottenTomatoes 3 2 5 2 3 3 4 1
 *  0
 *************************************************************************/

public class RURottenTomatoes {

    public static void main(String[] args) {
		int n;
		int o;
		n = Integer.parseInt(args[0]);
		o = Integer.parseInt(args[1]);
		int c = 2;
		int [] [] table = new int [n] [o];
		for (int i=0; i<n; i++) {
			for (int j=0; j<o; j++){
				table[i][j] = Integer.parseInt(args[c]);
				c++;
			}
		}

		int sumrow = 0;
		int highrow = 0;
		for(int k = 0; k < o ; k++){
			int total = 0;
    		for(int l = 0; l < n ; l++){
				total += table[l][k];
			}
			if (total > sumrow){
				sumrow = total;
				highrow = k ;
			}
			
		}
		System.out.println(highrow);
		




	}
}
