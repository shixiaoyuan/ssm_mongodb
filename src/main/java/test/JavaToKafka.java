package test;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

public class JavaToKafka {
    public static void main(String[] args){
        KafkaConsumerTest kafkaConsumerTest = new KafkaConsumerTest("sex");
        new Thread(kafkaConsumerTest).start();
    }
    private static void customer() {
        ConsumerRecords<String, String> records = null;
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.5:9092");
        props.put("group.id", "java");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset","earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("sex"));
        while (true) {
            int[] data = new int[]{0,0};
            records = consumer.poll(100);
            consumer.seek(new TopicPartition("sex", 0),1);
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                String sex = record.value();
                if("0".equals(sex)){
                    data[0]++;
                }else if ("1".equals(sex)){
                    data[1]++;
                }else continue;
            }
            if (data[0] != 0){
                System.out.println("girl:" + data[0] + ",boy:" + data[1]);
//                break;
            }
        }
//        while (true) {
//            records = consumer.poll(100);
//            consumer.seekToEnd(Arrays.asList(new TopicPartition("sex", 0)));
//            for (ConsumerRecord<String, String> record : records){
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//            }
//        }
    }
}

class KafkaConsumerTest implements Runnable{
    private KafkaConsumer<String, String> consumer = null;
    private ConsumerRecords<String, String> records = null;
    private String topic = null;
    public KafkaConsumerTest(){}
    public KafkaConsumerTest(String topic){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.5:9092");
        props.put("group.id", "java");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset","earliest");
        this.consumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }
    @Override
    public void run() {
        while(true){
            consumer.subscribe(Arrays.asList(this.topic));
            int[] data = new int[]{0,0};
            records = consumer.poll(100);
            consumer.seek(new TopicPartition("sex", 0),1);
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                String sex = record.value();
                if("0".equals(sex)){
                    data[0]++;
                }else if ("1".equals(sex)){
                    data[1]++;
                }else continue;
            }
            if (data[0] != 0){
                System.out.println("girl:" + data[0] + ",boy:" + data[1]);
//                break;
            }
        }

    }
}
