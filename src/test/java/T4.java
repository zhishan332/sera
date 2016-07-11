public class T4 {
    private int flags;


    private boolean has(int f) {
        return (flags & f) != 0;
    }

    public void doService(int flags){
        this.flags=flags;

        if(has(1)){
            System.out.println("送U盘");
        }
        if(has(2)){
            System.out.println("送手机");
        }
        if(has(4)){
            System.out.println("送电脑");
        }
        if(has(8)){
            System.out.println("送汽车");
        }
    }

    public static void main(String[] args) {
        T4 t4 = new T4();

        t4.doService(1|2|4);
        System.out.println("---------------分割线-----------------");
        t4.doService(2|8);
//打印的结果:
//        送U盘
//        送手机
//        送电脑
//        ---------------分割线-----------------
//        送手机
//        送汽车
    }
}
