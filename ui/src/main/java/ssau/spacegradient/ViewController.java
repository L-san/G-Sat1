package ssau.spacegradient;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ssau.spacegradient.dataprocessing.Filter;
import ssau.spacegradient.dataprocessing.KalmanFilter;
import ssau.spacegradient.dataprocessing.MadgwickSettings;
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
    public ToggleButton startProcessingButton;
    public ToggleButton connectionButton;

    public TextField beta;
    public TextField zeta;

    public TextField accelerometerLSB;
    public TextField magnetometerLSB;
    public TextField gyroscopeLSB;
    public TextField time;

    public TextField rCoeff;
    public TextField qCoeff;

    public CheckMenuItem filter;

    public ViewController() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                "ssau.spacegradient.dataprocessing",
                "ssau.spacegradient.mainapp",
                "ssau.spacegradient.clientapp.client");
        this.controller = context.getBean(Controller.class);
        // System.out.println("i'm here");
    }

    @FXML
    public void startConnection(ActionEvent event) {
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.POWDERBLUE);
        box.setMaterial(phongMaterial);

        if (connectionButton.isSelected()) {
            String ip = null;
            int port = 0;
            boolean isOk = true;
            try {
                ip = ipAddressTextField.getText();
                port = Integer.parseInt(portTextField.getText());
                if (!ip.equals("")) {
                    controller.generateClient(ip, port);
                } else {
                    controller.generateClient();
                }
                controller.startClient();
            } catch (Exception exception) {
                exception.printStackTrace();
                connStatusLabel.setText("Incorrect host address");
                isOk = false;
            }
            if (isOk) connStatusLabel.setText("Connected to " + ip + ":" + port);
        } else if (!connectionButton.isSelected()) {
            controller.stopClient();
            controller.stopAlgorithm();
            connStatusLabel.setText("Disconnected");
            startProcessingButton.setSelected(false);
        }

    }

    @FXML
    public void startProcessing(ActionEvent event) {

        if (startProcessingButton.isSelected()) {
            try {
                MadgwickSettings set = new MadgwickSettings(
                        beta.getText(),
                        zeta.getText(),
                        accelerometerLSB.getText(),
                        magnetometerLSB.getText(),
                        gyroscopeLSB.getText(),
                        time.getText());
                controller.startAlgorithm(this, set);
            } catch (Exception exception) {
                connStatusLabel.setText(exception.getMessage());
                startProcessingButton.setSelected(false);
            }
        } else if (!startProcessingButton.isSelected()) {
            controller.stopAlgorithm();
            startProcessingButton.setSelected(false);
        }
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
            System.out.println(angle + " " + q[1] + " " + q[2] + " " + q[3]);
        });
    }

    protected final void rotateBox(double angle, Point3D rotationAxis) {
        RotateTransition rotate = new RotateTransition();
        rotate.setByAngle(angle);
        rotate.setAxis(rotationAxis);
        rotate.setNode(this.box);
        rotate.play();
    }

    public void doFiltering(ActionEvent actionEvent) {
        if (filter.isSelected()) {
            double r = 0;
            double q = 0;
            try {
                r = Double.parseDouble(rCoeff.getText());
                q = Double.parseDouble(qCoeff.getText());
                controller.setFilter(new KalmanFilter(r, q));
            } catch (Exception exception) {
                connStatusLabel.setText("Incorrect filter parameters");
                filter.setSelected(false);
            }
        } else if (!filter.isSelected()) {
            controller.setFilter(new Filter());
        }
    }
}

