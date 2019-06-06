package main.Reflection;

import java.lang.reflect.Method;

public class ReflectionInvoke {
    public static void main(String[] args) throws Exception{
        Class testClass = Class.forName("ReflectionApplication");
        System.out.println(testClass.getName());
        Method method= testClass.getDeclaredMethod("getInfo",String.class);
        method.invoke(testClass,"java.lang.reflect.Method");
    }
}
