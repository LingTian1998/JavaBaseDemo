package main.StringTest;
public class SplitManyBlank {
    public static void main(String[] a){
        String string = "abc bcd   cdf";
        String[] strings = string.split("\\s+");
        for (String s: strings){

            System.out.println(s);
        }
    }
}
