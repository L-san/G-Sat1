package ssau.spacegradient.clientapp.client;

import ssau.spacegradient.clientapp.client.converter.DataContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class ClientController {
    private Client client;
    private Thread clientThread;
    private final BlockingQueue<DataContainer> rcvQueue;

    @Autowired
    public ClientController(Client client) {
        this.client = client;
        this.rcvQueue = client.getRcvQueue();
        System.out.println("i'm here");
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

    public void stop(){
        client.onExit();
    }

    public DataContainer receive() throws InterruptedException {
        return rcvQueue.take();
    }
}