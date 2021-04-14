package ssau.spacegradient.dataprocessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class AlgorithmController {
    private final Algorithm algorithm;

    @Autowired
    public AlgorithmController(Madgwick algorithm) {
        this.algorithm = algorithm;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void start(Consumer<? super ProcessedData> consumer) {
        algorithm.setConsumer(consumer);
        Thread algorithmThread = new Thread(algorithm);
        algorithmThread.setDaemon(true);
        algorithmThread.start();
    }
}
