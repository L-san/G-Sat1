package org.codex.client;

import org.codex.client.converter.DataContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component("clientController")
public class ClientController {
    private Client client;
    private Thread clientThread;
    private final BlockingQueue<DataContainer> rcvQueue;

    @Autowired
    ClientController(Client client) {
        this.client = client;
        this.rcvQueue = client.getRcvQueue();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(String ip, int port) {
        this.client = new Client(rcvQueue);
        client.setIpAddress(ip);
        client.setPort(port);
    }

    public void start() throws IllegalArgumentException {
        clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();
    }

    public DataContainer receive() throws InterruptedException {
        return rcvQueue.take();
    }
}