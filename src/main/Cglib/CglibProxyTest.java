package main.Cglib;
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyTest {
    public static void main(String[] a){
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(CglibProxy.class);
        enhancer.setCallback(new CglibInterceptor());
        CglibProxy cglibProxy = (CglibProxy)enhancer.create();
        cglibProxy.sayHello("CHenzhe");
    }
}
