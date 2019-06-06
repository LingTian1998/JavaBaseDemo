package main.StaticProxy;
public class StaticProxyImpl implements StaticProxyInterface{
    public void sayHello(String string){
        System.out.println("Hello,"+string);
    }
}
