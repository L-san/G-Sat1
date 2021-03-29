package org.codex.exchangerbeans;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

@Component
public class ArrayBlockingQueueBean extends ArrayBlockingQueue<String> {
    public ArrayBlockingQueueBean() {
        super(1);
    }
}
