package org.codex.controller;

import org.codex.client.Client;
import org.codex.client.ClientController;
import org.codex.client.converter.DataContainer;
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

    public void generateClient(String ip, int port)throws IllegalArgumentException{
        this.clientController = context.getBean("clientController",ClientController.class);
        clientController.setClient(ip,port);
    }

    public void startClient() throws IllegalArgumentException{
        clientController.start();
    }

    public DataContainer receive() throws InterruptedException {
       return  clientController.receive();
    }



}
