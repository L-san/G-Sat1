package org.codex.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
@Component("clientController")
public class ClientController {
    private Client client;
    private Thread clientThread;
    private final BlockingQueue<String> rcvQueue;

    ClientController(BlockingQueue<String> rcvQueue){
        this.rcvQueue = rcvQueue;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(String ip, int port) {
        this.client = new Client(rcvQueue);
        client.setIpAddress(ip);
        client.setPort(port);
    }

    public void start(){
        clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();
    }

    public String receive() throws InterruptedException {
        return rcvQueue.take();
    }
}