public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        int count = 0;
        for ( int i=0; i <= 200000; i++ ){
           if ( i % 17 == 0){
                count++;
                sum = sum + i;
           }
        }
        double average = (double)sum / count;
        System.out.println(average);
    }
    
}
