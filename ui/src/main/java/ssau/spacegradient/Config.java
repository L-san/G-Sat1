package ssau.spacegradient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ssau.spacegradient.clientapp.client.Client;
import ssau.spacegradient.clientapp.client.ClientController;
import ssau.spacegradient.dataprocessing.AlgorithmController;
import ssau.spacegradient.dataprocessing.Madgwick;
import ssau.spacegradient.clientapp.client.Controller;

@Configuration
public class Config {

    @Bean
    public Client getClient(){
        return new Client();
    }

    @Bean
    public Madgwick getMadgwick(){
        return new Madgwick();
    }

    @Bean
    public ClientController getClientController(Client client){
        return new ClientController(client);
    }

    @Bean
    public AlgorithmController getAlgorithmController(Madgwick algorithm){
        return new AlgorithmController(algorithm);
    }

    @Bean
    public Controller getController(ClientController controller, AlgorithmController algorithmController){
        return new Controller(controller,algorithmController);
    }
}
