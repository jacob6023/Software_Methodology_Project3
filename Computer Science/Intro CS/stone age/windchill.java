public class WindChill {
    public static void main (String[] args)
    {
        //declare variables t for temperature, v for wind
        double t;
        double v;
        //read values
        t = Double.parseDouble(args[0]);
        v = Double.parseDouble(args[1]);
        //compute solution
        //windchill
        double w;
        w = 35.74 + 0.6215 * t + (0.4275 * t - 35.75) * Math.pow(v,0.16) ;
        System.out.println("Wind Chill: " + w);
    }
}
