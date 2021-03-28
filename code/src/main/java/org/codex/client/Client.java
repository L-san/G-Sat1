package org.codex.client;

import org.fusesource.mqtt.client.*;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Client extends Thread {
    private String topic;
    private String ipAddress;
    private int port;

    private final BlockingQueue<String> send2ControllerQueue;

    public Client(BlockingQueue<String> send2ControllerQueue) {
        /*this.ipAddress = "84.201.135.43";
        this.port = 1883;
        this.topic = "test";*/
        this.ipAddress = "0.0.0.0";
        this.port = 1;
        this.topic = "test";
        this.send2ControllerQueue = send2ControllerQueue;
    }

    public BlockingQueue<String> getSend2ControllerQueue() {
        return send2ControllerQueue;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
        System.out.println("Subscribed to topics.");

        Message msg;
        try {
            msg = connection.receive(1200, TimeUnit.MILLISECONDS);
            send2ControllerQueue.put(new String(msg.getPayload()));
            System.out.println(new String(msg.getPayload()));
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