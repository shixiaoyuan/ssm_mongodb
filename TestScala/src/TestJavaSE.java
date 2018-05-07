import java.util.ArrayList;

class Person{
    private String name = "zhangsan";
    Person(){}
    Person(String name){
        this.name = name;
    }
    public boolean equals(Person person){
        return this.name.equals(person.getName());
    }
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

class Student extends People{
    public static void print(){
    }
}