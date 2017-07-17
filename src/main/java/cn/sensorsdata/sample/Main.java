package cn.sensorsdata.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by tianyi on 14/07/2017.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        //System.out.println("main started");
        SampleExtProcessor obj = new SampleExtProcessor();
        // final String record = "{\\\"_track_id\\\":809362935,\\\"time\\\":1499235936428,\\\"type\\\":\\\"track\\\",\\\"properties\\\":{\\\"$device_id\\\":\\\"66b37fe757b4d553\\\",\\\"$model\\\":\\\"ZUK Z1\\\",\\\"$os_version\\\":\\\"6.0.1\\\",\\\"$app_version\\\":\\\"2.8.9\\\",\\\"$manufacturer\\\":\\\"ZUK\\\",\\\"$screen_height\\\":1920,\\\"$os\\\":\\\"Android\\\",\\\"$carrier\\\":\\\"中国移动\\\",\\\"$screen_width\\\":1080,\\\"$lib_version\\\":\\\"1.7.10\\\",\\\"$lib\\\":\\\"Android\\\",\\\"$wifi\\\":true,\\\"$network_type\\\":\\\"WIFI\\\",\\\"channel\\\":\\\"jdb\\\",\\\"deviceID\\\":\\\"867695029160115\\\",\\\"network\\\":\\\"5\\\",\\\"fromPage\\\":\\\"jdbclient://user/myTrade/borrow\\\",\\\"toPage\\\":\\\"jdbclient://user/profile/profit\\\", \\\"fromPageTitle\\\":\\\"\\\",\\\"toPageTitle\\\":\\\"\\\",\\\"$is_first_day\\\":true},\\\"distinct_id\\\":\\\"558325089081602776\\\",\\\"lib\\\":{\\\"$lib\\\":\\\"Android\\\",\\\"$lib_version\\\":\\\"1.7.10\\\",\\\"$app_version\\\":\\\"2.8.9\\\",\\\"$lib_method\\\":\\\"code\\\",\\\"$lib_detail\\\":\\\"com.rrh.jdb.business.analytics.sensors.JDBSensorsAgent##a##SourceFile##80\\\"},\\\"event\\\":\\\"AppViewScreen\\\",\\\"_flush_time\\\":1499235936466,\\\"project\\\":\\\"default\\\",\\\"ip\\\":\\\"100.73.49.10\\\"}";
        // Debug result: com.sensorsdata.analytics.extractor.service.DebugService$RecordChecker$CheckResult@52101e5d[returnCode=400,debugString=PROPERTY_WITH_WRONG_TYPE,debugLog=属性 'autoProductSwitch' 数据类型错误，之前已将 'autoProductSwitch' 的类型定为 'STRING'，但本次传值为 '1'.,record=[{"time":1500257274595,"properties":{"device_id":"276F3621-BCF3-4D94-B643-3D398B52445B","transfer":"","periods":"2","$os_version":"8.3","remark":"","delegateAgreementSwitch":false,"$device_id":"4E755ACC-5F6C-4B90-9268-B8BDBBD41CBF","$lib":"iOS","autoProductSwitch":1,"url":"/mybankv21/phptradeui/product/add","$manufacturer":"Apple","network":"5","borrower":"我的全部宝粉(含新增)","$model":"iPhone6,2","$os":"iOS","channel":"appstore","$lib_version":"1.7.8","$screen_width":320,"productType":"0","currentPageTitle":"我要借钱","effectiveTime":"20","$screen_height":568,"repayMode":2,"$network_type":"WIFI","tags":"临时周转","$wifi":true,"friendIdList":"toAll","$app_version":"2.8.9","currentPage":"jdbclient://trade/borrow/index","rate":"24","$is_first_day":false,"udid":"93039cb41cf153fea133832e532bb9f76ab45a57","amount":10,"transted":"amount","$ip":"100.73.38.74","$city":"未知","$province":"未知","$country":"共享地址","transted_s":",transfer"},"type":"track","lib":{"$app_version":"2.8.9","$lib_version":"1.7.8","$lib":"iOS","$lib_method":"code","$lib_detail":"JDBSensorsAnalyticsHelper##logClickEvent:eventName:####"},"event":"myBorrow","distinct_id":"667103842971897792","project":"default","extractor":{"f":null,"o":0,"n":null,"s":86,"c":86,"e":"devdata1.jiedaibao.sa"},"recv_time":1500259504335,"ngx_ip":"127.0.0.1","error_type":"PROPERTY_WITH_WRONG_TYPE"}],debugFlag=3]
        //  {"time":1500258522322,"properties":{"$os":"iOS","$screen_width":320,"amount":10,"friendIdList":"toAll","currentPageTitle":"我要借钱","tags":"临时周转","transfer":false,"$app_version":"2.8.9","$model":"iPhone6,2","$network_type":"WIFI","$device_id":"4E755ACC-5F6C-4B90-9268-B8BDBBD41CBF","currentPage":"jdbclient://trade/borrow/index","channel":"appstore","repayMode":2,"url":"/mybankv21/product/preAddProduct","productType":"0","$wifi":true,"device_id":"276F3621-BCF3-4D94-B643-3D398B52445B","borrower":"我的全部宝粉(含新增)","udid":"93039cb41cf153fea133832e532bb9f76ab45a57","$is_first_day":false,"$screen_height":568,"delegateAgreementSwitch":false,"network":"5","effectiveTime":"20","$lib_version":"1.7.8","$os_version":"8.3","rate":"24","$manufacturer":"Apple","$lib":"iOS","periods":"2","remark":"","transted_s":"amount","$ip":"100.73.38.74","$city":"未知","$province":"未知","$country":"共享地址"},"type":"track","lib":{"$app_version":"2.8.9","$lib_version":"1.7.8","$lib":"iOS","$lib_method":"code","$lib_detail":"JDBSensorsAnalyticsHelper##logClickEvent:eventName:####"},"event":"myBorrow","distinct_id":"667103842971897792","project":"default","extractor":{"f":"(dev=803,ino=393897)","o":132829,"n":"access_log.2017071710","s":3499732,"c":3499732,"e":"devdata1.jiedaibao.sa"},"recv_time":1500258536799,"ngx_ip":"100.73.38.74","error_type":"PROPERTY_WITH_WRONG_TYPE"}
        final String record = readTxt("/Users/tianyi/project/data.json");
        try {
            logger.warn("process data warn");
            logger.info("process data info");
            logger.error("process data err");
            logger.debug("process data debug");
            //System.out.println(record);
            obj.process(record);
            //System.out.println("classpath路径： "+SampleExtProcessor.class.getClassLoader().getResource("").getPath());
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            logger.warn("process except {}", ex);
        }
    }

    public static String readTxt(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader buf = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader reader = new BufferedReader(buf);
                String line = reader.readLine().trim();
                //System.out.println(line);
                return line;
            }
            logger.warn("file not exist {}", file);
            return "";
        } catch (Exception ex) {
            logger.warn("readTxt exception {}", ex);
            System.out.println("read file exception ");
        }
        return "";
    }
}
