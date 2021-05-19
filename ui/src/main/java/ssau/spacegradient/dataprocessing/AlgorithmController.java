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

    public void start(MadgwickSettings set) {
        this.algorithm.setSettings(set);
        this.algorithmThread = new Thread(algorithm);
        this.algorithmThread.setDaemon(true);
        this.algorithmThread.start();
    }

    public void stop() {
        if (algorithmThread != null) {
            this.algorithmThread.interrupt();
        }
    }

    public void setFilter(Filter filter) {
        algorithm.setFilter(filter);
    }
}
