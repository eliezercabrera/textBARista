package com.eli.textbarista;

import org.junit.Test;
import static org.junit.Assert.*;

public class GreeterTest {
    @Test
    public void greeterGreetsWorldTest() {
        String actual = Greeter.greet();
        String expected = "Hello, World!";
        assertEquals(actual, expected);
    }

}