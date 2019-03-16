/**
 * @Author: yd
 * @Date: 2019/3/16 9:27
 * @Version 1.0
 */
public class SortHelper {
    public static int[] generateRandomArray(int n,int rangeL,int rangR){
        int[] array=new int[n];
        for(int i=0;i<n;i++){
            array[i]=(int) (Math.random()*(rangR-rangeL)+rangeL);
        }
        return array;
    }
    public  static void print(int[] array){
        for(int i: array){
            System.out.print(i+" ");
        }
    }
}
