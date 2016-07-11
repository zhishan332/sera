import java.util.regex.Pattern;

/**
 * Created by wangqing on 16/5/17.
 */
public class T2 {

    public static void main(String[] args) {
//        System.out.println(3&(1|2|3));
//        System.out.println(3&3);
//        System.out.println(3&(1|2|4));
//        System.out.println(3&4);
//        System.out.println(3|(1|2|5));
//        System.out.println(2&(2|3|4));
//        System.out.println(3&(2|3|4));
//        System.out.println(4&(2|3|4));

//        System.out.println((2&(2|3|4))==(2|3|4));
//        System.out.println((3&(2|3|4))==(2|3|4));
//        System.out.println((4&(2|3|4))==(2|3|4));
        Pattern pattern=Pattern.compile("\\w",Pattern.CASE_INSENSITIVE|Pattern.COMMENTS|Pattern.MULTILINE);

        T2 bean = new T2();
        //保养服务为1+2+3
        //喷漆服务为4
        //维修服务为5+6
        System.out.println("顾客A需要保养服务:" + bean.getService1(1)
                + bean.getService1(2)
                + bean.getService1(3));
        System.out.println("顾客B需要喷漆服务:" + bean.getService1(4));
        System.out.println("顾客C需要维修服务:" + bean.getService1(5)+ bean.getService1(6));

        System.out.println("-----------------------分割线---------------------");
        /**改造一下**/
        System.out.println("顾客A需要保养服务:" + bean.getService2(BAOYANG));
        System.out.println("顾客B需要喷漆服务:" + bean.getService2(PENQI));
        System.out.println("顾客C需要维修服务:" + bean.getService2(WEIXIU));
        System.out.println("顾客D需要维修服务:" + bean.getService2(XIHUAN));
    }


    private String getService1(int type) {
        String service = "";
        if (type == 1) {
            service = "洗车";
        } else if (type == 2) {
            service = "换机油";
        } else if (type == 3) {
            service = "换刹车片";
        } else if (type == 4) {
            service = "喷漆";
        } else if (type == 5) {
            service = "钣金";
        } else if (type == 6) {
            service = "换轮胎";
        }
        return service;
    }

    //参数的组合形式可以自由配置
    final static int BAOYANG = 1 | 2 |4;
    final static int PENQI = 8;
    final static int WEIXIU = 16 | 32;
    final static int XIHUAN = 1 | 32;

    private String getService2(int type) {
        String service = "";
        if ((type & BAOYANG) == BAOYANG) {
            service += "洗车";
            service += "换机油";
            service += "换刹车片";
        }else if ((type & PENQI) == PENQI) {
            service += "喷漆";
        }else if ((type & WEIXIU) == WEIXIU) {
            service += "钣金";
            service += "换轮胎";
        }else if ((type & XIHUAN) == XIHUAN) {
            service += "洗车";
            service += "换轮胎";
        }
        return service;
    }

}
