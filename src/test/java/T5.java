import java.util.Random;

/**
 * Created by wangqing on 16/5/17.
 */
public class T5 {

    public static void main(String[] args) {
//        System.out.println(((1|2|4)&(1|2|4))==(1|2|4));
//        System.out.println(((1|2|32)&(1|2|32))==(1|2|32));
        long aa=100001L;
        String word=String.valueOf(aa);
        word = word.substring(word.length() - 2) + word.substring(word.length() - 4, word.length() - 2);
        System.out.println("1:"+word);

        long b=aa % 10000;
        System.out.println(b);
        System.out.println(b/100);
        long cc = (b % 100) *100 + b/100;
        System.out.println(cc);
//        System.out.println(aa%10000);

        System.out.println(randomString(-229985452) + " " + randomString(-147909649));
    }

    public static String randomString(int i){
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true)
        {
            int k = ran.nextInt(27);
            if (k == 0)
                break;

            sb.append((char)('`' + k));
        }

        return sb.toString();
    }

    public void getMsg(int[] types){
        for(int a:types){
            if(a==1){
                System.out.println("送U盘");
            }
            if(a==2){
                System.out.println("送手机");
            }
            if(a==3){
                System.out.println("送电脑");
            }
        }
    }
}
