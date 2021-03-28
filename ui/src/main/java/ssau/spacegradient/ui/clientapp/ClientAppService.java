package ssau.spacegradient.ui.clientapp;

import org.codex.client.ClientBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
public class ClientAppService {
    private BlockingQueue<String> queue;
    ClientAppService(){
    }

    public void connectServer(String ipAddress, int port) throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClientBean clientBean = context.getBean("clientBean", ClientBean.class);
        clientBean.setIpAddress(ipAddress);
        clientBean.setPort(port);
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
