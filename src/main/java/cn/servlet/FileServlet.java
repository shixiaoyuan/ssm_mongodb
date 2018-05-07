package cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "FileServlet",urlPatterns = "/file")
public class FileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("utf-8");
        String path = this.getServletContext().getRealPath("/csv/hbase.csv");
        File file = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        FileOutputStream fos = new FileOutputStream(file);

        fos.write("hello".getBytes());
        byte[] buf = new byte[1024];
        String line = br.readLine();

        System.out.println(line);
    }
}
