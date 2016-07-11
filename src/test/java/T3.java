import java.util.regex.Pattern;

/**
 * Created by wangqing on 16/5/17.
 */
public class T3 {
    public static void main(String[] args) {
//        System.out.println((5 | 6));
//        System.out.println((1 | 2 | 3));
//        System.out.println((5 | 6) & (1 | 2 | 3));
//        Pattern.compile("/d",1|2);

//        int flags = 1 | 2 | 5 | 8;
//
//        boolean isHas = (flags & 1) != 0;
//        System.out.println(isHas);
//        boolean isHas1 = (flags & 2) != 0;
//        System.out.println(isHas1);
//        boolean isHas2 = (flags & 5) != 0;
//        System.out.println(isHas2);
//        boolean isHas3 = (flags & 8) != 0;
//        System.out.println(isHas3);
//
//        boolean isHas34 = (flags & 2) != 0;
//        System.out.println(isHas34);

        T3 t3=new T3();
        t3.flags=0x20|0x40|0x80;

        System.out.println(0x20);
        System.out.println(0x40);
        System.out.println(0x80);
        System.out.println(t3.flags);
        System.out.println(t3.has(0x1));
        System.out.println(t3.has(0x10));
        System.out.println(t3.has(0x20));


        System.out.println("--------------");
        t3.flags=0x01|0x02|0x04;

        System.out.println(t3.flags);
        System.out.println(t3.has(1));
        System.out.println(t3.has(2));
        System.out.println(t3.has(4));
        System.out.println(t3.has(8));
        System.out.println(t3.has(9));
        System.out.println("--------------");

        System.out.println((1|2|4|8)&1);
    }

    int flags;
    private boolean has(int f) {
        return (flags & f) != 0;
    }
}
