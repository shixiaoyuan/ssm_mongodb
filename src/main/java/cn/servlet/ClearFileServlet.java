package cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "ClearFileServlet",urlPatterns = "/clear")
public class ClearFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = this.getServletContext().getRealPath("/csv/hbase.csv");
        File file = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write("");
        bw.close();
        br.close();
        response.sendRedirect("/ssmm/show/dashboard.html");
    }
}
