package ssau.spacegradient.clientapp.beans;

import ssau.spacegradient.clientapp.client.converter.DataContainer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

@Component
public class BlockingQueueBean extends ArrayBlockingQueue<DataContainer> {
    public BlockingQueueBean() {
        super(1);
    }
}
