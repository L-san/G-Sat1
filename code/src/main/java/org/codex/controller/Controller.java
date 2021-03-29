package org.codex.controller;

import org.codex.client.Client;
import org.codex.client.ClientController;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

//управление клиентом
//управление обработкой
public class Controller {
    ClassPathXmlApplicationContext context;
    private ClientController clientController;

    public Controller(ClassPathXmlApplicationContext context){
        this.context = context;
    }

    public void generateClient(String ip, int port){
        this.clientController = context.getBean("clientController",ClientController.class);
        clientController.setClient(ip,port);
    }

    public void start(){
        clientController.start();
    }

    public String receive() throws InterruptedException {
       return  clientController.receive();
    }



}
