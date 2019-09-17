package main.JunitEx;

import org.junit.Test;

public class JunitTest {
    @Test
    public void addTest(){
        Calcuate calcuate = new Calcuate();
        System.out.println(calcuate.add(1,2));
    }
}
