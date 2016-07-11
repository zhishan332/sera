/**
 * Created by wangqing on 16/5/17.
 */
public class T1 {

    public static void main(String[] args) {
        T1 bean=new T1();
        System.out.println(bean.getIdByLevel(371326,2));
    }


    /**根据国标ID和行政区划级别获取对应的ID
     *
     * @param id 国标行政区划编号
     * @param level 级别
     * @return 对应的区划ID
     */
    private long getIdByLevel(long id,int level){
        String idStr=String.valueOf(id);
        //国标ID只有六位和9位的情况
        long levelId=0;
        int strLen=idStr.length();
        String param=strLen+"-"+level;
        switch (param){
            case "6-1":
                if(idStr.endsWith("0000")){
                    levelId=id;
                }else{
                    String nStr=idStr.substring(0,2)+"0000";
                    levelId = Long.valueOf(nStr);
                }
                break;
            case "6-2":
                if(idStr.endsWith("00")){
                    levelId=id;
                }else {
                    String nStr=idStr.substring(0,4)+"00";
                    levelId = Long.valueOf(nStr);
                }
                break;
            case "6-3":
                levelId=id;
                break;
            case "9-1"://省
                String proStr=idStr.substring(0,2)+"0000";
                levelId = Long.valueOf(proStr);
                break;
            case "9-2"://市
                String cityStr=idStr.substring(0,4)+"00";
                levelId = Long.valueOf(cityStr);
                break;
            case "9-3"://县
                String disStr=idStr.substring(0,6);
                levelId = Long.valueOf(disStr);
                break;
            case "9-4"://乡
                levelId = id;
                break;
            default:
        }
        return levelId;
    }
}
