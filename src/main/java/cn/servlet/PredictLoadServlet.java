package cn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(name = "PredictLoadServlet", urlPatterns = "/predict")
public class PredictLoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String[] command = new String[]{"spark-submit2.cmd","F:\\刘静\\文件\\电力\\实验\\电量预测\\read_model.py", start, end};
//        String[] command = new String[]{"spark-submit2.cmd","F:\\刘静\\文件\\电力\\实验\\电量预测\\read_model.py", "2015-12-01 12:30:00", "2015-12-01 12:40:00"};
        try {
            Process pr = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = br.readLine();
            response.getWriter().print(line);
            System.out.println(line);
            br.close();
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
