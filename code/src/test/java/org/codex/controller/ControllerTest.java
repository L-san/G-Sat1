package org.codex.controller;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ControllerTest {

    @Test
    public void testReceive() throws InterruptedException {
        Controller controller = new Controller(new ClassPathXmlApplicationContext( "applicationContext.xml"));
        controller.generateClient("84.201.135.43",1883);
        System.out.println(controller.receive());
    }
}