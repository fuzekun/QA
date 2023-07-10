package contest;

import java.util.Arrays;

/**
 * @author: Zekun Fu
 * @date: 2022/12/18 14:57
 * @Description: java竞赛区域赛
 */
public class Test {

    public static void main(String[] args) {
        String s = "HH";
        String s2 = new String("HH");
        System.out.println(s == s2);
        System.out.println(s.equals(s2));
        System.out.println(s.compareTo(s2));
        fa(1, 2);
    }

    protected static int fa(int a, double b){
        String ip = "13.32.32";
        System.out.println(ip.indexOf('.'));
        System.out.println(ip.substring(0, ip.indexOf('.')));

        int[][] arr;
        arr = new int[3][];
        arr[0] = new int[]{1,3,3};
        System.out.println(arr.length);
        System.out.println(arr[1].length);
        System.out.println(Arrays.toString(arr[0]));
        short x = 1, y = 2, c = 2;

        return 0;

    }
}
class B extends Test{
//    @Override
    public int fb(int a, double a2) {
        return super.fa(a, a2);
    }
}
