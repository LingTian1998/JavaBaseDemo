package main.Polymorphic;

public class Test {
    public static void main(String[] args){
        Person person = new Student();
        //person.run();  //Student.run
        runTwice(person);
    }
    public static void runTwice(Person person){
        person.run();
        person.run();
    }
}
class Student extends Person{
    @Override
    public void run() {
        System.out.println("Student.run");
    }
}
class Person{
    public void run(){
        System.out.println("Person.run");
    }
}
