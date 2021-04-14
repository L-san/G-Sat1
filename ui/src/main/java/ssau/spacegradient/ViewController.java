package ssau.spacegradient;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ssau.spacegradient.dataprocessing.ProcessedData;
import ssau.spacegradient.clientapp.client.Controller;

import java.util.function.Consumer;

@Component
public class ViewController implements Consumer<ProcessedData> {
    private final Controller controller;
    @FXML
    public Box box;
    public TextField ipAddressTextField;
    public TextField portTextField;
    public Label connStatusLabel;
    public Label telemetryLabel;


    public ViewController() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                "ssau.spacegradient.dataprocessing",
                "ssau.spacegradient.mainapp",
                "ssau.spacegradient.clientapp.client");
        this.controller = context.getBean(Controller.class);
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
    public void startProcessing(ActionEvent event) {
        controller.startAlgorithm(this);
    }


    @FXML
    public void stopConnection(MouseEvent mouseEvent) {
        //controller.stopClient();
    }

    @Override
    public void accept(ProcessedData data) {
        Platform.runLater(() -> {
            telemetryLabel.setText(data.getRawData().toString());
            double[] q = data.getQ();
            double angle;
            angle = 2 * Math.acos(q[0]);
            Point3D rotationAxis = new Point3D(-q[2], q[3], q[1]);
            rotateBox(angle * 180 / Math.PI, rotationAxis);
            System.out.println(angle+" "+q[1]+" "+q[2]+" "+q[3]);
        });
    }

    protected final void rotateBox(double angle, Point3D rotationAxis) {
        RotateTransition rotate = new RotateTransition();
        rotate.setByAngle(angle);
        rotate.setAxis(rotationAxis);
        rotate.setNode(this.box);
        rotate.play();
    }
}

