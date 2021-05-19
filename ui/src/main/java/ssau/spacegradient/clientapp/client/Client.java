package ssau.spacegradient.clientapp.client;

import org.fusesource.mqtt.client.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ssau.spacegradient.clientapp.client.converter.AbstractConverter;
import ssau.spacegradient.clientapp.client.converter.DataContainer;
import ssau.spacegradient.clientapp.client.converter.JsonConverter;

import java.net.URISyntaxException;
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
        this.ipAddress = "62.77.153.231";
        this.port = 1883;
        //this.topic = "json/realtime";
        this.topic = "Received data";
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
        int i = 1;
        try {
            while (true) {
                Message msg = null;
                System.out.println(i++);
                msg = connection.receive(1000, TimeUnit.MILLISECONDS);
                data = converter.convert(new String(msg.getPayload()));
                data.setMessage(new String(msg.getPayload()));
                receive().subscribe(consumer);
                System.out.println(new String(msg.getPayload()));
                msg.ack();
            }
        } catch (Exception ignored) {
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