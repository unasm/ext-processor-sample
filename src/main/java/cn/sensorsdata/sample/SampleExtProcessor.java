package cn.sensorsdata.sample;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sensorsdata.analytics.extractor.processor.ExtProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by fengjiajie on 16/9/28.
 */
public class SampleExtProcessor implements ExtProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SampleExtProcessor.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private static List<Map<String, Object>> DbMeta ;
    //private static String[] resources = {"db", "cache"}

    public SampleExtProcessor() {
        try {
            DBSource db = new DBSource();
            DbMeta = db.executeQuery("select * from property_define where table_type = 0");
            //List<Map<String, Object>> data = db.executeQuery("select * from property_define where table_type = 0");
        } catch (SQLException ex) {
            logger.error("获取数据库数据异常 {}", ex);
        }
    }


    public String process(String record) throws Exception {

        // 传入参数为一条符合 Sensors Analytics 数据格式定义的 Json
        // 数据格式定义 https://www.sensorsdata.cn/manual/data_schema.html
        //logger.info("preProcess_data {} ", record);
        if (record.length() == 0) {
            return "";
        }


        JsonNode recordNode = objectMapper.readTree(record);
        String type = recordNode.get("type").asText().trim();
        //System.out.println(type.indexOf("\"", 1));
        //if (type.indexOf("\"") == 0 && type.indexOf("\"", 1) == (type.length() - 1)) {
        //    type = type.substring(1, type.length() - 1);
        //}
        //System.out.println(type);
        //if (type.substring(0, 1) == "\"") {
        //    System.out.println("data matched");
        //} else {
        //    System.out.println("data not matched");
        //}
        // 查看数据类型
        if (!type.equals("track")) {
            return record;
        }

        JsonNode jsonnode = null;
        ObjectNode properties = (ObjectNode) recordNode.get("properties");
        String changedColumn = "";
        //if (properties.get("transted") != null) {
            //properties.remove("transted");
        //}
        //if (properties.get("transted_s") != null) {
            //properties.remove("transted_s");
        //}
        for (Map<String, Object> row : DbMeta) {
            Integer dataType = (Integer)row.get("data_type");
            String name = (String)row.get("name");
            jsonnode = properties.get(name);
            if (jsonnode == null) {
                continue;
            }
            //System.out.println(name + "\t\t" + jsonnode + "\t\t"+ jsonnode.getClass());
            Class jsonType = jsonnode.getClass();
            if (Const.DB_INT_TYPE == dataType) {
                if (!jsonType.equals(com.fasterxml.jackson.databind.node.IntNode.class)) {
                    // 如果数据的类型，本来不是Int的话，则判断是否能够修改, String 的 正则匹配, 布尔型的看情况
                    if (jsonType.equals(com.fasterxml.jackson.databind.node.TextNode.class)) {
                        String  jsonData = jsonnode.asText("");
                        if (jsonData.matches("^[0-9]+$")) {
                            //System.out.println("aggeing : " + jsonnode + "\t" + Integer.parseInt(jsonData));
                            //properties.put(name, Integer.parseInt(jsonData));
                            properties.put(name, Integer.parseInt(jsonData));
                            changedColumn += "," + name;
                        }
                    }
                }
                // 如果 本来应该的类型 是INT 的话
                //if (jsonnode instanceof Integer) {
                //    System.out.println("it is interger");
                //} else {
                //    System.out.println("it is not interger");
                //}
            }
            if (Const.DB_String_TYPE == dataType) {
                if (!jsonnode.getClass().equals(com.fasterxml.jackson.databind.node.TextNode.class)) {
                    //如果不是
                    if (jsonType.equals(com.fasterxml.jackson.databind.node.BooleanNode.class)) {
                        Boolean value = jsonnode.asBoolean();
                        //System.out.println("checking : " + value);
                        if (value == false) {
                            properties.put(name, "");
                            changedColumn += "," + name;
                        }
                    }
                    //System.out.println("new transted : " + jsonType);
                    if (jsonType.equals(com.fasterxml.jackson.databind.node.IntNode.class)) {
                        // 如果本来是int，但是需要的却是string的话
                        //System.out.println(jsonnode.asText());
                        properties.put(name, jsonnode.asText());
                        changedColumn += "," + name;
                    }
                }
            }
            if (Const.DB_TIME_TYPE == dataType) {
                // 不处理
            }
            if (Const.DB_BOOLEAN_TYPE == dataType) {
                //酌情处理
            }
        }
        //System.out.println(changedColumn);
        //if (changedColumn.length() > 0) {
            //properties.put("transted_s", changedColumn);
        //}
        //logger.info("endProcess_data {} ", recordNode.toString());
        //System.out.println(recordNode.toString());
        return recordNode.toString();

        //JsonNode recordNode = objectMapper.readTree(record);
        //ObjectNode propertiesNode = (ObjectNode) recordNode.get("properties");
        // 例如传入的一条需要处理的数据是:
        //
        // {
        //     "distinct_id":"2b0a6f51a3cd6775",
        //     "time":1434556935000,
        //     "type":"track",
        //     "event":"ViewProduct",
        //     "properties":{
        //         "product_name":"苹果"
        //     }
        // }
        //
        // 如果是“苹果”或“梨”, 那么添加一个字段标记产品为“水果”;
        // 如果是“萝卜”或“白菜”, 那么标记为“蔬菜”;

        //if (propertiesNode.has("product_name")) {
        //    String productName = propertiesNode.get("product_name").asText();
        //    if ("苹果".equals(productName) || "梨".equals(productName)) {
        //        propertiesNode.put("product_classify", "水果");
        //        // 输出日志到 /data/sa_standalone/logs/extractor 下的 extractor.log 中
        //        logger.info("Find a fruit: {}", productName);
        //    } else if ("萝卜".equals(productName) || "白菜".equals(productName)) {
        //        propertiesNode.put("product_classify", "蔬菜");
        //    }
        //}


        //return recordNode.toString();
    }
}