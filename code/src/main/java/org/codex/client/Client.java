package org.codex.client;

import org.codex.client.converter.AbstractConverter;
import org.codex.client.converter.DataContainer;
import org.codex.client.converter.JsonConverter;
import org.fusesource.mqtt.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component("client")
public class Client extends Thread {
    private String topic;
    private String ipAddress;
    private int port;
    private final BlockingQueue<DataContainer> rcvQueue;
    private AbstractConverter converter;

    @Autowired
    public Client(BlockingQueue<DataContainer> rcvQueue) {
        /*this.ipAddress = "84.201.135.43";
        this.port = 1883;
        this.topic = "test";*/
        this.ipAddress = "0.0.0.0";
        this.port = 1;
        this.topic = "test";
        this.rcvQueue = rcvQueue;
        this.converter = new JsonConverter();
    }

    public BlockingQueue<DataContainer> getRcvQueue() {
        return rcvQueue;
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

    @Override
    public void run() {
        MQTT mqtt = new MQTT();
        try {
            mqtt.setHost(ipAddress, port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.out.println("Can't find host " + ipAddress + ":" + port);
        }
        //mqtt.setUserName("root");
        //mqtt.setPassword("root");

        BlockingConnection connection = mqtt.blockingConnection();
        try {
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't connect to " + ipAddress + ":" + port);
        }

        /*String payload = "hey c:";
        connection.publish("topic", payload.getBytes(), QoS.AT_MOST_ONCE, false);
        System.out.println("Successfully published.");*/

        try {
            connection.subscribe(new Topic[]{new Topic(topic, QoS.EXACTLY_ONCE)});
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failure: topic doesn't exist.");
        }

        Message msg;
        try {
            msg = connection.receive(1200, TimeUnit.MILLISECONDS);
            rcvQueue.put(converter.convert(new String(msg.getPayload())));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failure: receiving has been failed");
        }

        try {
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't disconnect");
        }
    }

}