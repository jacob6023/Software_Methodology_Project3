public class OrderCheck {
    public static void main (String[] args)
    {
    //establish 4 integers
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
    boolean o = false;

    //check if true or false for descending order
    while ( w > x && x > y && y > z )
    {
        o = true; 
        System.out.println(o);
        return;
    }
    //check if true or false for ascending order
    while ( w < x && x < y && y < z )
    {
        o = true; 
        System.out.println(o);
        return;
    }

    System.out.println(o);
    }
}