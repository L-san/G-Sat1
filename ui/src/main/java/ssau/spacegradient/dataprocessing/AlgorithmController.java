package ssau.spacegradient.dataprocessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class AlgorithmController {
    private final Madgwick algorithm;
    private Thread algorithmThread;

    @Autowired
    public AlgorithmController(Madgwick algorithm) {
        this.algorithm = algorithm;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void start(Consumer<? super ProcessedData> consumer, MadgwickSettings set) {
        this.algorithm.setConsumer(consumer);
        this.algorithm.setSettings(set);
        this.algorithmThread = new Thread(algorithm);
        this.algorithmThread.setDaemon(true);
        this.algorithmThread.start();
    }

    public void stop() {
        this.algorithmThread.interrupt();
    }
}
