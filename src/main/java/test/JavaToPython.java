package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class JavaToPython {
    public static void main(String[] args) {
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.execfile("F:\\刘静\\文件\\电力\\实验\\电量预测\\gbt-predict.py");
//        String[] command = new String[]{"F:\\刘静\\文件\\电力\\实验\\电量预测\\py.bat"};
        String[] command = new String[]{"spark-submit2.cmd","F:\\刘静\\文件\\电力\\实验\\电量预测\\read_model.py", "2015-12-01 12:30:00", "2015-12-01 12:40:00"};
        try {
            Process pr = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = br.readLine();
            System.out.println(line);
            br.close();
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
