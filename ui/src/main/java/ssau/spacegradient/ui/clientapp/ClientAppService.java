package ssau.spacegradient.ui.clientapp;

import org.codex.client.ClientBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
public class ClientAppService {
    private final BlockingQueue<String> queue;
    ClientAppService(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClientBean clientBean = context.getBean("clientBean", ClientBean.class);
        clientBean.setIpAddress("84.201.135.43");
        clientBean.setPort(1883);
        clientBean.setTopic("test");
        this.queue = clientBean.getSend2ControllerQueue();
        Thread clientThread = new Thread(clientBean);
        clientThread.setDaemon(true);
        clientThread.start();
        context.close();
    }
    public String getMessage() throws InterruptedException {
        return queue.take();
    }
}
