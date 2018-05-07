package cn.servlet;

import cn.pojo.Elec;
import cn.utils.HBaseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(name = "HBaseServlet",urlPatterns = "/hbasefile")
public class HBaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("utf-8");
        List<List<Elec>> elecList = HBaseUtils.scan("test");
        String path = this.getServletContext().getRealPath("/csv/hbase.csv");
        File file = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        FileOutputStream fos = new FileOutputStream(file,true);     //可续写

        for(int i = 0; i <elecList.size(); i++){
            List<Elec> list = elecList.get(i);
            if (i == 0){
                for (int j = 0; j < list.size(); j++){
                    Elec elec = list.get(j);
                    if (j == list.size() - 1){
                        fos.write((elec.getColumn() + "\r\n").getBytes());
                        break;
                    }
                    fos.write((elec.getColumn() + ",").getBytes());
                }
            }
            for (int j = 0; j < list.size(); j++){
                Elec elec = list.get(j);
                if (j == list.size() - 1){
                    fos.write((elec.getValue() + "\r\n").getBytes());
                    break;
                }
                fos.write((elec.getValue() + ",").getBytes());
            }
        }

        fos.close();
        br.close();
        response.sendRedirect("/ssmm/show/dashboard.html");
    }
}
