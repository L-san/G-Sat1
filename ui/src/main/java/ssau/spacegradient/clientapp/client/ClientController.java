package ssau.spacegradient.clientapp.client;

import ssau.spacegradient.clientapp.client.converter.DataContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

@Component
public class ClientController  {
    private Client client;

    @Autowired
    public ClientController(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(String ip, int port) {
        client.setIpAddress(ip);
        client.setPort(port);
    }

    public void start(Consumer<? super DataContainer> consumer) {
        client.setConsumer(consumer);
        Thread clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();
    }
}