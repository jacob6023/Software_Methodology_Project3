public class DivideItems {
    public static void main (String[] args){
        double dividend = Double.parseDouble(args[0]);
        double divisor  = Double.parseDouble(args[1]);
        if ( divisor == 0.0 ){
            System.out.println("ERROR");
        }
        else {
            double Quotient = dividend / divisor;
            System.out.println(Quotient);
        }


    }
}
