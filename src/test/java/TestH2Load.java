import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试H2文件加载后内存
 * Created by wangqing on 16/6/16.
 */
@Service
public class TestH2Load {
    private static final Logger logger = LoggerFactory.getLogger(TestH2Load.class);
    private Map<String, Object> indexMap = new ConcurrentHashMap<>();
    private Map<String, Object> dataMap = new ConcurrentHashMap<>();

//    @PostConstruct
    public void init() {
        try {
            readCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (Connection conn = H2Utils.getConnection("/Users/wangqing/Documents/other/h2data/ods_village", "sa", "")) {
//            for (int i = 0; i < Integer.MAX_VALUE; i += 10000) {
//                final int finalI = i;
//                logger.info("链接H2文件:" + finalI);
//                String sql = "select NAME,UNIQUE_NAME,ALIAS,X,Y, COUNTY_CODE,TOWN_CODE FROM village  limit " + finalI + ",10000";
//                logger.info("正在执行h2查询:" + sql);
//                ResultSet rs = null;
//                PreparedStatement stmt = conn.prepareStatement(sql);
//                rs = stmt.executeQuery(sql);
//                while (rs.next()) {
//                    String NAME = rs.get("NAME");
//                    String UNIQUE_NAME = rs.get("UNIQUE_NAME");
//                    String ALIAS = rs.get("ALIAS");
//                    String X = rs.get("X");
//                    String Y = rs.get("Y");
//                    String COUNTY_CODE = rs.get("COUNTY_CODE");
//                    String TOWN_CODE = rs.get("TOWN_CODE");
//                    Map<String, String> obj = new HashMap<>();
//                    obj.put("NAME", NAME);
////                                obj.put("UNIQUE_NAME", UNIQUE_NAME);
////                                obj.put("ALIAS", ALIAS);
//                    obj.put("X", X);
//                    obj.put("Y", Y);
////                                obj.put("COUNTY_CODE", COUNTY_CODE);
////                                obj.put("TOWN_CODE", TOWN_CODE);
//                    String id = UUID.randomUUID().toString();
//                    indexMap.put(TOWN_CODE + "_" + NAME, id);
//                    indexMap.put(TOWN_CODE + "_" + ALIAS, id);
//                    indexMap.put(COUNTY_CODE + "_" + NAME, id);
//                    indexMap.put(COUNTY_CODE + "_" + ALIAS, id);
//                    dataMap.put(id, obj);
//                    logger.info("加载:" + obj);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
//
//        //1次查询1000条,直到查询不到为止
//        for (int i = 0; i < Integer.MAX_VALUE; i += 10000) {
//            final int finalI = i;
//            fixedThreadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    logger.info("链接H2文件:" + finalI);
//                    H2Utils h2Utils = new H2Utils();
//                    try (Connection conn = h2Utils.getConnection("/Users/wangqing/Documents/other/h2data/ods_village", "sa", "")) {
//                        String sql = "select NAME,UNIQUE_NAME,ALIAS,X,Y, COUNTY_CODE,TOWN_CODE FROM village  limit " + finalI + ",10000";
//                        logger.info("正在执行h2查询:" + sql);
//                        ResultSet rs = null;
//                        try {
//                            Statement stmt = conn.createStatement();
//                            rs = stmt.executeQuery(sql);
//                            while (rs.next()) {
//                                String NAME = rs.get("NAME");
//                                String UNIQUE_NAME = rs.get("UNIQUE_NAME");
//                                String ALIAS = rs.get("ALIAS");
//                                String X = rs.get("X");
//                                String Y = rs.get("Y");
//                                String COUNTY_CODE = rs.get("COUNTY_CODE");
//                                String TOWN_CODE = rs.get("TOWN_CODE");
//                                Map<String, String> obj = new HashMap<>();
//                                obj.put("NAME", NAME);
////                                obj.put("UNIQUE_NAME", UNIQUE_NAME);
////                                obj.put("ALIAS", ALIAS);
//                                obj.put("X", X);
//                                obj.put("Y", Y);
////                                obj.put("COUNTY_CODE", COUNTY_CODE);
////                                obj.put("TOWN_CODE", TOWN_CODE);
//                                String id = UUID.randomUUID().toString();
//                                indexMap.put(TOWN_CODE + "_" + NAME, id);
//                                indexMap.put(TOWN_CODE + "_" + ALIAS, id);
//                                indexMap.put(COUNTY_CODE + "_" + NAME, id);
//                                indexMap.put(COUNTY_CODE + "_" + ALIAS, id);
//                                dataMap.put(id, obj);
//                                logger.info("加载:" + obj);
//                            }
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    } catch (Exception e) {
//                        logger.error("TownDivisionSpaStore空间索引加载异常,请重启服务或手动重新加载所有数据!!!", e);
//                    }
//                }
//            });
//
//        }


    }


    private void readCsv() throws IOException {
        String ff=this.getClass().getClassLoader().getResource("data/ods_village.csv").getFile();
        Reader in = new FileReader(ff);
//        Reader in = new FileReader("/Users/wangqing/Documents/code/se/sera-web/src/main/resources/data/ods_village.csv");
//        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("adcode","name","unique_name","alias","x","y","county_code","town_code").parse(in);
        for (CSVRecord rs : records) {
            String name = rs.get("name");
            String alias = rs.get("alias");
            String x = rs.get("x");
            String y = rs.get("y");
            String county_code = rs.get("county_code");
            String town_code = rs.get("town_code");
            Map<String, String> obj = new HashMap<>();
            obj.put("name", name);
            obj.put("x", x);
            obj.put("y", y);
            String id = UUID.randomUUID().toString();
            indexMap.put(town_code + "_" + name, id);
            indexMap.put(town_code + "_" + alias, id);
            indexMap.put(county_code + "_" + name, id);
            indexMap.put(county_code + "_" + alias, id);
            dataMap.put(id, obj);
            logger.info("加载:" + obj);
            
        }

        logger.info("大小:"+indexMap.size()+"数据:"+dataMap.size());
    }
}
