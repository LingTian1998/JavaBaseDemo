import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibInterceptor implements MethodInterceptor {

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)throws  Throwable{
        System.out.println("预处理");
        Object object = methodProxy.invokeSuper(o,objects);
        System.out.println("后处理");
        return object;
    }
}
