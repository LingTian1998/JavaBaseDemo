public class DynamicProxyImpl implements DynamicProxyInterface{
    public void sayHello(String string){
        System.out.println("Hello,"+string);
    }
    public void greet(String string){
        System.out.println("Nice to meet you!");
    }
}
