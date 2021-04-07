package ssau.spacegradient.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ssau.spacegradient.clientapp.client.ClientController;
import ssau.spacegradient.clientapp.client.converter.DataContainer;

//управление клиентом
//управление обработкой

@Component("controller")
public class Controller {
    private ClientController clientController;
    @Autowired
    public Controller(ClientController clientController) {
        this.clientController =  clientController;

    }

    public void generateClient(String ip, int port) throws IllegalArgumentException {
        clientController.setClient(ip, port);
    }

    public void startClient() throws IllegalArgumentException {
        clientController.start();
    }

    public void stopClient() {
        clientController.stop();
    }

    public DataContainer receive() throws InterruptedException {
        return clientController.receive();
    }
}
