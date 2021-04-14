package ssau.spacegradient.clientapp.client;

import reactor.core.publisher.Flux;
import ssau.spacegradient.clientapp.client.converter.AbstractConverter;
import ssau.spacegradient.clientapp.client.converter.DataContainer;
import ssau.spacegradient.clientapp.client.converter.JsonConverter;
import org.fusesource.mqtt.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
public class Client extends Thread {
    private String topic;
    private String ipAddress;
    private int port;
    private AbstractConverter converter;
    private BlockingConnection connection;
    private Consumer<? super DataContainer> consumer;
    private DataContainer data = new DataContainer();

    public Client() {
        /*this.ipAddress = "84.201.135.43";
        this.port = 1883;
        this.topic = "test";*/
        this.ipAddress = "0.0.0.0";
        this.port = 1;
        this.topic = "json/realtime";
        this.converter = new JsonConverter();
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setConverter(AbstractConverter converter) {
        this.converter = converter;
    }

    public void setConsumer(Consumer<? super DataContainer> consumer) {
        this.consumer = consumer;
    }

    private void onStart() {
        MQTT mqtt = new MQTT();
        try {
            mqtt.setHost(ipAddress, port);
        } catch (URISyntaxException e) {
            System.out.println("Can't find host " + ipAddress + ":" + port);
        }
//mqtt.setUserName("root");
//mqtt.setPassword("root");

        connection = mqtt.blockingConnection();
        try {
            connection.connect();
        } catch (Exception e) {
            System.out.println("Can't connect to " + ipAddress + ":" + port);
        }

        try {
            connection.subscribe(new Topic[]{new Topic(topic, QoS.EXACTLY_ONCE)});
        } catch (Exception e) {
            System.out.println("Failure: topic doesn't exist.");
        }
    }

    @Override
    public void run() {
        onStart();
        while (true) {
            Message msg = null;
            try {
                msg = connection.receive(1200, TimeUnit.MILLISECONDS);
                data = converter.convert(new String(msg.getPayload()));
                receive().subscribe(consumer);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    public Flux<DataContainer> receive() {
        return Flux.fromArray(new DataContainer[]{data});
    }
   /* public void stop() {
        try {
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("Can't disconnect");
        }
    }*/
}