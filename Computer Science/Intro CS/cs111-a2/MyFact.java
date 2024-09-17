public class MyFact {
    public static void main(String[] args) {
        int n;
        int fact = 1 ;
        n = Integer.parseInt(args[0]);
        int count;
        count = n; 
        while (count > 0) 
        {
            fact = count * fact;
            count = count - 1;
        }
        System.out.println(fact);
    }
}
