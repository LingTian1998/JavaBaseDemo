public class StaticProxy implements StaticProxyInterface{
    private StaticProxyInterface staticProxyInterface = new StaticProxyImpl();
    public void sayHello(String string){
        System.out.println("Before");
        staticProxyInterface.sayHello(string);
        System.out.println("After");
    }
}
