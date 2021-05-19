package ssau.spacegradient.clientapp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ssau.spacegradient.clientapp.client.ClientController;
import ssau.spacegradient.dataprocessing.*;

import java.util.function.Consumer;

//управление клиентом
//управление обработкой

@Component
public class Controller {
    private final ClientController clientController;
    private final AlgorithmController algorithmController;

    @Autowired
    public Controller(ClientController clientController, AlgorithmController algorithmController) {
        this.clientController =  clientController;
        this.algorithmController = algorithmController;
    }

    public void generateClient(String ip, int port) throws IllegalArgumentException {
        clientController.setClient(ip, port);
    }

    public void startClient() {
        clientController.start();
    }

    public void stopClient(){
        clientController.stop();
    }

    public void startAlgorithm(MadgwickSettings set){
        algorithmController.start(set);
    }

    public void stopAlgorithm(){
        algorithmController.stop();
    }

    public void setFilter(Filter filter){algorithmController.setFilter(filter);}
}
