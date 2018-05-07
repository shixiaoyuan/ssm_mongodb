package cn.utils;

import cn.pojo.Elec;
import javafx.util.Pair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*****
 *decription:一个HBase的工具包，用于对hdfs://master:9000/hbase这个分布式文件路径的hbase数据库的增删改查
 *
 */
public class HBaseUtils {
    // hbase操作必备
    private static Configuration getConfiguration() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir", "hdfs://master:9000/hbase");
        // 使用eclipse时必须添加这个，否则无法定位
        conf.set("hbase.zookeeper.quorum", "master");
        return conf;
    }

    // 创建一张表
    public static void create(String tableName, String columnFamily)
            throws IOException {
        HBaseAdmin admin = new HBaseAdmin(getConfiguration());
        if (admin.tableExists(tableName)) {
            System.out.println("table exists!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            tableDesc.addFamily(new HColumnDescriptor(columnFamily));
            admin.createTable(tableDesc);
            System.out.println("create table success!");
        }
    }

    // 添加一条记录
    public static void put(String tableName, String row, String columnFamily,
                           String column, String data) throws IOException {
        HTable table = new HTable(getConfiguration(), tableName);
        Put p1 = new Put(Bytes.toBytes(row));
        p1.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes
                .toBytes(data));
        table.put(p1);
        System.out.println("put'" + row + "'," + columnFamily + ":" + column
                + "','" + data + "'");
    }

    //添加多条记录
    public static void putAll(String tableName, String columnFamily, String path) {
        File file = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null){
                sb.append(line + "\r\n");
            }
            putAllData(tableName, columnFamily, sb.toString(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void putAllData(String tableName, String columnFamily, String text, String path){
        try {
            HTable table = new HTable(getConfiguration(), tableName);
            String[] lines = text.split("\r\n");
            String[] columns = lines[0].split(",");
            List<Put> listput = new ArrayList<Put>();
            for(int i = 1; i < lines.length; i++){
                String[] data = lines[i].split(",");
                Put put = new Put(trim(String.valueOf(i)).getBytes());
                for (int j = 0; j < data.length; j++){
                    put.add(columnFamily.getBytes(), trim(columns[j]).getBytes(), trim(data[j]).getBytes());
                }
                listput.add(put);
            }
            table.put(listput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("插入数据成功，路径："+path);
    }

    // 读取一条记录
    public static List<Elec> get(String tableName, String row) throws IOException {
        List<Elec> list = new ArrayList<>();
        HTable table = new HTable(getConfiguration(), tableName);
        Get get = new Get(Bytes.toBytes(row));
        Result result = table.get(get);
        System.out.printf("一共有%s列数据：",result.size());
        for (Cell cell : result.rawCells()){
            Elec elec = new Elec();
            elec.setCf(new String(CellUtil.cloneFamily(cell)));
            elec.setColumn(new String(CellUtil.cloneQualifier(cell)));
            elec.setRow(new String(CellUtil.cloneRow(cell)));
            elec.setValue(new String(CellUtil.cloneValue(cell)));
            elec.setTime(String.valueOf(cell.getTimestamp()));
            list.add(elec);
//            HashMap<String, String> map = new HashMap<>();
//            map.put("row", new String(CellUtil.cloneRow(cell)));
//            map.put("cf", new String(CellUtil.cloneFamily(cell)));
//            map.put("column", new String(CellUtil.cloneQualifier(cell)));
//            map.put("value", new String(CellUtil.cloneValue(cell)));
//            map.put("time", String.valueOf(cell.getTimestamp()));
//            System.out.println("行数："+ new String(CellUtil.cloneRow(cell)));
//            System.out.println("列族："+ new String(CellUtil.cloneFamily(cell)));
//            System.out.println("列："+ new String(CellUtil.cloneQualifier(cell)));
//            System.out.println("值："+ new String(CellUtil.cloneValue(cell)));
//            System.out.println("时间戳："+ cell.getTimestamp());
        }
        return list;
    }

    // 显示所有数据
    public static List<List<Elec>> scan(String tableName) throws IOException {
        List<List<Elec>> lists = new ArrayList<>();
        HTable table = new HTable(getConfiguration(), tableName);
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            List<Elec> list = new ArrayList<>();
            for (Cell cell : result.rawCells()){
                Elec elec = new Elec();
                elec.setCf(new String(CellUtil.cloneFamily(cell)));
                elec.setColumn(new String(CellUtil.cloneQualifier(cell)));
                elec.setRow(new String(CellUtil.cloneRow(cell)));
                elec.setValue(new String(CellUtil.cloneValue(cell)));
                elec.setTime(String.valueOf(cell.getTimestamp()));
                list.add(elec);
            }
            lists.add(list);
        }
        return lists;
    }

    // 删除表
    public static void delete(String tableName) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(getConfiguration());
        if (admin.tableExists(tableName)) {
            try {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Delete " + tableName + " 失败");
            }
        }
        System.out.println("Delete " + tableName + " 成功");
    }

    //去除两边的双引号
    private static String trim(String string){
        if (!string.startsWith("\""))
            return string;
        return string.substring(string.indexOf("\"")+1,string.lastIndexOf("\""));
    }

}
