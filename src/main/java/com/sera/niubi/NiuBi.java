package com.sera.niubi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 一些牛逼的代码
 * Created by wangqing on 16/8/12.
 */
public class NiuBi {


    /**
     * 其实，选择一组随机的整数并不是随机的。给定一个seed参数(在这个例子中是-229985452和-147909649), 那么每次随机，同样的seed则会产生同样的输出。
     * <p>
     * Random(-229985452).nextInt(27)产生的前六个数字：8, 5, 12, 12, 15, 0
     * <p>
     * Random(-147909649).nextInt(27)产生的前六个数字：23, 15, 18, 12, 4, 0
     * <p>
     * 这样，最终输出的就是”hello world”。
     **/
    public static String helloWorld(int i) {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int k = ran.nextInt(27);
            if (k == 0)
                break;

            sb.append((char) ('`' + k));
        }
        return sb.toString();
    }

    /**
     * 为什么两个时间戳相减(in 1927)得出一个奇怪的结果？
     * 按说上面的代码最后的结果应该是1，但实际的输出却是353。其实，这是一个时区的问题。
     * 1927年12月31号24:00，上海时间往回调整了5分钟52秒，因此”1927-12-31 23:54:08”发生了两次，Java将后面一次实例化成了本地的这个时间。因此和前一秒的差距成了353。
     * 我们需要指出，如果你试着来运行这段代码，结果并不一定是353。Jon Skeet指出了这一点，在时区数据库项目2014版中，这个改变的时间点改到了1900-12-31，因此成了344秒的差距。
     */
    public static long time() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str3 = "1927-12-31 23:54:07";
        String str4 = "1927-12-31 23:54:08";
        Date sDt3 = sf.parse(str3);
        Date sDt4 = sf.parse(str4);
        long ld3 = sDt3.getTime() / 1000;
        long ld4 = sDt4.getTime() / 1000;
        return ld4 - ld3;
    }


    public static void main(String[] args) throws Exception {
        System.out.println(NiuBi.helloWorld(-229985452) + " " + NiuBi.helloWorld(-147909649));

        System.out.println(NiuBi.time());
    }
}
