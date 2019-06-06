package main.Reflection;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class ReflectionApplication {
    public static void main(String[] args) throws Exception{
        Scanner scanner =new Scanner(System.in);
        while (scanner.hasNext()){
            getInfo(scanner.nextLine());
        }
    }
    public static void getInfo(String string) throws  Exception{
        Class concurrentHashMapClass = Class.forName(string);
        String classname = concurrentHashMapClass.getName();
        String SuperName = concurrentHashMapClass.getSuperclass().getName();
        String InterfaceName =concurrentHashMapClass.getInterfaces().getClass().getName();
        System.out.println("public class "+classname+" extends "+ SuperName+"{\n");
        System.out.println("//所有属性:");
        Field[] fields =concurrentHashMapClass.getDeclaredFields();
        int k=1;
        for (Field field:fields){
            System.out.println(k+" "+Modifier.toString(field.getModifiers())+" "+field.getType()+" "+field.getName());
            k++;
        }

        System.out.println("\n\n");
        System.out.println("//所有方法:");
        Method[] methods= concurrentHashMapClass.getMethods();
        int j=1;
        for (Method method:methods){
            Class[] parameters = method.getParameterTypes();
            System.out.print(j+" "+Modifier.toString(method.getModifiers())+" "+method.getReturnType()+" "+ method.getName()+"(");
            int i=0;
            for (;i<parameters.length-1;i++){
                System.out.print(parameters[i].getName()+" var"+(i+1)+",");
            }
            if (parameters.length!=0)
                System.out.print(parameters[i].getName()+" var"+(i+1)+")\n");
            else
                System.out.print(")\n");

            j++;
        }
        System.out.println("}");
    }
}
