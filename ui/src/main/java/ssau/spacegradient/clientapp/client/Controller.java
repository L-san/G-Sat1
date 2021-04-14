package ssau.spacegradient.clientapp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ssau.spacegradient.clientapp.client.ClientController;
import ssau.spacegradient.dataprocessing.AlgorithmController;
import ssau.spacegradient.dataprocessing.ProcessedData;

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
        clientController.start(algorithmController.getAlgorithm());
    }

    public void startAlgorithm(Consumer<? super ProcessedData> consumer){
        algorithmController.start(consumer);
    }
}
