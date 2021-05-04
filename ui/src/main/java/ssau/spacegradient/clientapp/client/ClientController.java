package ssau.spacegradient.clientapp.client;

import ssau.spacegradient.clientapp.client.converter.DataContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

@Component
public class ClientController {
    private Client client;
    private Thread clientThread;

    @Autowired
    public ClientController(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(String ip, int port) {
        this.client.setIpAddress(ip);
        this.client.setPort(port);
    }

    public void setClient() {
    }

    public void start(Consumer<? super DataContainer> consumer) {
        this.client.setConsumer(consumer);
        this.clientThread = new Thread(client);
        this.clientThread.setDaemon(true);
        this.clientThread.start();
    }

    public void stop() {
        this.clientThread.interrupt();
    }
}