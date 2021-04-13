package ssau.spacegradient;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ssau.spacegradient.clientapp.client.Client;
import ssau.spacegradient.clientapp.Controller;

import java.util.concurrent.ArrayBlockingQueue;

@Component
public class ViewController {
    private final Controller controller;
    private String message;
    @FXML
    public Box box;
    public Label telemetryLabel;
    public TextField ipAddressTextField;
    public TextField portTextField;
    public Label connStatusLabel;

    public ViewController() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                "ssau.spacegradient.clientapp.beans",
                "ssau.spacegradient.clientapp.client",
                "ssau.spacegradient.clientapp");
        this.controller = context.getBean("controller", Controller.class);
        // System.out.println("i'm here");
    }

    @FXML
    public void startConnection(MouseEvent mouseEvent) {
        //todo где депенденси инжекшн емае
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.POWDERBLUE);
        box.setMaterial(phongMaterial);
        /*RotateTransition rotate = new RotateTransition();
        rotate.setByAngle(3.4699 * 180 / Math.PI);
        rotate.setAxis(new Point3D(0.6966, 0.0540, 0.6966));
        rotate.setNode(box);
        rotate.play();*/

        String ip = null;
        int port = 0;
        boolean isOk = true;
        try {
            ip = ipAddressTextField.getText();
            port = Integer.parseInt(portTextField.getText());
            if (ip.equals("")) {
                throw new IllegalArgumentException("Please, enter ip-address");
            }
            System.out.println(ip + " " + port);
            controller.generateClient(ip, port);
            controller.startClient();
        } catch (Exception exception) {
            exception.printStackTrace();
            connStatusLabel.setText("Incorrect host address");
            isOk = false;
        }
        if (isOk) connStatusLabel.setText("Connected to " + ip + ":" + port);
    }

    @FXML
    public void startProcessing(ActionEvent event) throws InterruptedException {
        // updateTelemetry();
        //todo видимо, его придется убивать в stopProcessing
            try {
                Runnable updater = () -> {
                    if (message != null) {
                        try {
                            updateTelemetry();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                while (true) {
                    message = controller.receive().toString();
                    updateTelemetry();
                    Platform.runLater(updater);
                    System.out.println(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void updateTelemetry() throws InterruptedException {
        telemetryLabel.setText(message);
    }

    @FXML
    public void stopConnection(MouseEvent mouseEvent) {
        //controller.stopClient();
    }
}

