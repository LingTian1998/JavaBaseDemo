import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    public static void main(String[] args){
        DynamicProxyInterface dynamicProxyInterface =new DynamicProxyImpl();
        InvocationHandler invocationHandler = new DynamicProxy(dynamicProxyInterface);
        DynamicProxyInterface dynamicProxy = (DynamicProxyInterface)Proxy.newProxyInstance(DynamicProxyInterface.class.getClassLoader(),new Class[]{DynamicProxyInterface.class},invocationHandler);
        dynamicProxy.greet("chenzhe");
    }
}
