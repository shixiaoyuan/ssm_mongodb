package test;

import cn.websocket.ServerManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class TestJavaSE {
    public static void main(String[] args) {
//        String a = "name";
//        String b = "name";
//        System.out.println(a == b);
        Thread t1 = new Thread(new ThreadTest());
        t1.start();
        t1.interrupt();
    }

    private void run1(){
        final int[] arr = new int[]{1};
        final int i = 0;
        new Object(){
            public void print(){
                arr[0]++;
                System.out.println(arr[0]);
            }
        }.print();
    }
}

class ThreadTest implements Runnable{
    public void run(){
        try {
            while (true){
                System.out.println("线程执行中");
                Thread.sleep(10);
            }
        }catch (InterruptedException e){
            System.out.println("线程终止");
        }
    }
}

class Person{
    private String name = "zhangsan";
    Person(){}
    Person(String name){
        this.name = name;
    }
    public boolean equals(Person person){
        return this.name.equals(person.name);
    }   //类内部可以直接调用同种类变量成员的私有变量
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class People{
    private String name;
    People(){}
    People(String name){
        this.name = name;
    }
    public static void print(){
        System.out.println();
    }
}
