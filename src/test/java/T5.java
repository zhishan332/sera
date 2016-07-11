/**
 * Created by wangqing on 16/5/17.
 */
public class T5 {

    public static void main(String[] args) {
        System.out.println(((1|2|4)&(1|2|4))==(1|2|4));
        System.out.println(((1|2|32)&(1|2|32))==(1|2|32));
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
