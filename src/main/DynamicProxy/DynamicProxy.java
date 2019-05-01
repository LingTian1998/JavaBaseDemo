import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy implements InvocationHandler {
    private Object object;
    public DynamicProxy(Object object){
        this.object=object;
    }

    public Object invoke(Object proxy, Method method,Object[] args)throws  Throwable{
        System.out.println("Before");
        method.invoke(object,args);
        System.out.println("After");
        return null;
    }
}
