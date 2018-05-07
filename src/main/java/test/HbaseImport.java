package test;

import cn.utils.HBaseUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class HbaseImport {
    public static void main(String[] args) throws IOException {
        System.out.println(HBaseUtils.get("test","1"));
//        HBaseUtils.putAll("test","cf","C:\\Users\\Onlyevan\\Desktop\\2.csv");
//        HBaseUtils.scan("test");
//        writeToCsv();
    }

    private static void writeToCsv() throws IOException {
        byte[] buf = new byte[1024];
        URL url = new URL("http://192.168.1.187:8080/ssmm/csv/hbase.csv");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

//        connection.setDoOutput(true);
//        connection.setDoInput(true);
//        InputStream in = connection.getInputStream();
//        OutputStream out = connection.getOutputStream();
//
//        in.read(buf);
//        System.out.println(buf.toString());
    }
}
