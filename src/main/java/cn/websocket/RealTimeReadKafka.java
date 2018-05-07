package cn.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Arrays;
import java.util.Properties;

//@WebServlet(name = "RealTimeReadKafka", urlPatterns = "/RealTimeReadKafka", loadOnStartup = 1)//标记为Servlet不是为了其被访问，而是为了便于伴随Tomcat一起启动
public class RealTimeReadKafka extends HttpServlet implements Runnable{
    private KafkaConsumer<String, String> consumer = null;
    private ConsumerRecords<String, String> records = null;
    private String topic = "results";
    public RealTimeReadKafka(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.5:9092");
        props.put("group.id", "java");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset","earliest");
        this.consumer = new KafkaConsumer<>(props);
    }
    public void init(ServletConfig config){
        startup();
    }

    public  void startup(){
        new Thread(this).start();
    }
    @Override
    public void run() {

        while(true){
            consumer.subscribe(Arrays.asList(this.topic));
            String girl = "0";
            String boy = "0";
            records = consumer.poll(100);
//            consumer.seek(new TopicPartition("sex", 0),1);
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                String sex = record.value();
                sex = sex.substring(1, sex.length() - 1);
                for(String s : sex.split(",")){
                    JSONObject json= JSON.parseObject(s);//json字符串转换成jsonobject对象
                    if(json.get("0") != null){
                        girl = json.get("0").toString();
                    }else if(json.get("1") != null){
                        boy = json.get("1").toString();
                    }else continue;
                }
                String result = girl + "," + boy;
                System.out.println(result);
                String messageFormat = "{\"data\":\"%s\"}";
                String message = String.format(messageFormat, result);
                //广播出去
                ServerManager.broadCast(message);
            }
//            if (data[0] != 0){
//                System.out.println("girl:" + data[0] + ",boy:" + data[1]);
//                String messageFormat = "{\"girl\":\"%d\",\"boy\":%d}";
//                String message = String.format(messageFormat, data[0],data[1]);
//                //广播出去
//                ServerManager.broadCast(message);
////                break;
//            }
        }
    }
}
