public class MysteryB {
    public static void main(String[] args) {
        int [] a = {1,3,7,5,2,13};
        System.out.println(mysteryB(a,0));
    }
    public static int mysteryB (int[] array, int n){
        if (n == array.length){
            return 0;
        }
        int value = mysteryB(array, n+1);
        if (array[n] % 2 == 0){
            return array[n] + value;
        }else{
            return value;
        }


    }
}
