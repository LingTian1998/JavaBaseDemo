package main.StringTest;

public class BooleanTest {
    public static void main(String[] args){
        Boolean temp = false;
        String string1 = temp.toString();

        Boolean temp2 = Boolean.valueOf(string1);
        System.out.println(temp2);

        String string2 = "TrUe";
        temp2 = Boolean.valueOf(string2);
        System.out.println(temp2);
    }
}
