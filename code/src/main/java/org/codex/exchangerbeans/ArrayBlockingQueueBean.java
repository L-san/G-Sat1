package org.codex.exchangerbeans;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueBean extends ArrayBlockingQueue<String> {
    public ArrayBlockingQueueBean() {
        super(1);
    }
}
