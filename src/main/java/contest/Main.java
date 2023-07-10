package contest;


import java.util.HashSet;
import java.util.Scanner;

/**
 * @author: Zekun Fu
 * @date: 2022/12/18 15:25
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        HashSet<Character> set = new HashSet<>();
        Character chars[] = new Character[]{'@', '!', '#', '%', '$', '*', '~'};
        for (char ch : chars) set.add(ch);
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        HashSet<Integer>ss = new HashSet<>();
        for (char ch : s.toCharArray()) {
            if (set.contains(ch)) ss.add(0);
            else if (Character.isDigit(ch)) ss.add(1);
            else if (Character.isUpperCase(ch)) ss.add(2);
            else if (Character.isLowerCase(ch)) ss.add(3);
        }
//        System.out.println(ss.size());
        if (s.length() < 8 || ss.size() < 2)
            System.out.println("NG");
        else if (ss.size() == 2) System.out.println("MG");
        else if (ss.size() == 3) System.out.println("VG");
        else System.out.println("EG");
    }
}
