package org.codex.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientController {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        ClientBean bean = context.getBean("clientBean", ClientBean.class);
        Thread clientThread = new Thread(bean);
        clientThread.start();
        context.close();
    }
}